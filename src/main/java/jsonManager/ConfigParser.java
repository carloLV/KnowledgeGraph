package jsonManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import KGTwitter.Difference;
import KGTwitter.Intersection;
import jsonManagerModels.GraphEntity;

/*
 * This class reads a config file and creates the map we need to manipulate.
 * This class saves the relations in a map. Each map contains as value the map representing
 * the entity connected with that relation.
 * 
 * In the config file:
 * " " separates elements;
 * ":" separates the fields for each element
 * "." separates the gerarchy in the json file
 * "-" separates different attributes
 */

public class ConfigParser {

	public Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> relations;

	private static final String confPath="jsonConfig.txt";
	private static final String jsonPath="/Users/davinderkumar/Desktop/interests1148580931.txt";
	LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap=new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();


	public ConfigParser(){
		this.relations = new LinkedHashMap<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> >();
	}

	private void readFields() throws IOException, ParseException{
		FileReader input = new FileReader(confPath);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
			//Array has 3 elements: REL, KEY, VALUE
			String[] array = myLine.split(" ");
			//Rel is the key for the external map
			String rel = array[0];
			//This key and value are for the inner map
			String key = array[1];
			String value = array[2];

			//This is the flag of relation. We will need it for neo4j graph creation
			String relation = rel.split(":")[1];
			
			//This list has in pos 0 the json field that represents the key (could be nested); the other field are the attributes
			ArrayList<String> keyToExtract = new ArrayList<String>();
			keyToExtract.add(key.split(",")[0].split(":")[1]); //This gets field for key
			for (int i=0;i<key.split(",")[1].split(":")[1].split("-").length; i++)
				keyToExtract.add(key.split(",")[1].split(":")[1].split("-")[i]);//This gets field for key attributes
			//Now the first list to extract keys is ready

			//Same type list but for values
			ArrayList<String> valueToExtract = new ArrayList<String>();
			valueToExtract.add(value.split(",")[0].split(":")[1]);
			for (int i=0;i<value.split(",")[1].split(":")[1].split("-").length; i++)
				valueToExtract.add(value.split(",")[1].split(":")[1].split("-")[i]);
			/************************************** fin qui ok: l'output è*/
		
			//printFinalMap(json2map(keyToExtract, valueToExtract));
			this.relations.put(relation, json2map(keyToExtract, valueToExtract));
			System.out.println("Ho inserito la relazione " + relation);
			//System.out.println(this.relations.keySet());
			printFinalMap(this.relations.get(relation));
			
			///****  OK FIN QUI È FUNZIONANTE***///
		}
	}

	//This method is dedicated to the extraction of attributes. They are supposed to be at the same level of nesting of the related keys
	private String getKeyAttribs(String[] attribs, Object object){
		String attr="";
		if (object instanceof Map){
			JSONObject jsonObject = new JSONObject();
			jsonObject.putAll((Map)object);
		if (attribs.length!=0)
			for (String a: attribs){
				attr+=a+":"+jsonObject.get(a).toString()+" ";
			}
		}
		return attr;
	}

	
	/*****************************************************************************************************/
	/***** This method, given a JSON line {...} extracts an array of GraphEntity *************************/
	/***** The this method will be called twice: 1 time for the key extracition; 2 time for values *******/
	/*****************************************************************************************************/

	public ArrayList<GraphEntity> extractEntity(JSONObject obj, ArrayList<String> info){
		//Variables:
		ArrayList<GraphEntity> finalList = new ArrayList<GraphEntity>();
		String[] nest = null;
		JSONParser parser = new JSONParser();
		JSONObject tmp=obj;

		/*******Start parsing json based on array info*******/
		//Split first element of info to get nested values of key
		if (info.get(0).split("\\.").length>1)
			nest = info.get(0).split("\\.");
		else nest = new String[] {info.get(0)};
		for (int i=0; i<nest.length ;i++){
			//If it is the last -> The nesting ends
			if (nest.length-i==1){
				//It can be list (a map)
				if (nest[i].trim().equals("*")){
					Set set = tmp.keySet();
					for (Object e: set){
						String[] newInfo = info.subList(1, info.size()).toArray(new String[info.size()-1]);
						String ats=getKeyAttribs(newInfo, tmp.get(e));
						finalList.add(new GraphEntity(e.toString(), ats));
					}
				}
				//Or a single value
				else {
					String id = tmp.get(nest[i]).toString();
					String[] newInfo = info.subList(1, info.size()).toArray(new String[info.size()-1]);
					String attribs=getKeyAttribs(newInfo, tmp.get(nest[i]));
					finalList.add(new GraphEntity(id, attribs));
				}
			}
			//If it is NOT the last elements we have to enter the nested fields
			else {
				//It could be a map
				if (nest[i].trim().equals("*")){
					//Must copy the remaining nested fields
					String remainingFields =String.join(".", Arrays.copyOfRange(nest, i+1, nest.length));
					ArrayList<String> newFields = new ArrayList<String>(info.subList(1, info.size()));
					newFields.add(0, remainingFields);
					Set<String> set= tmp.keySet();
					for (String e: set){
						//rilanciare algoritmo su tutti el mappa
						runOnMapElement(tmp.get(e), newFields, finalList);
					}
				}
				//Or could be a single value
				else{
					try {
						tmp =(JSONObject) tmp.get(nest[i]);
						String data=tmp.toJSONString();
						tmp = (JSONObject) parser.parse(data);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}

			}
		}

		return finalList;
	}

	//Does the same job of extractEntity, but it performs on maps and is activated recursively in case of more nested map
	public void runOnMapElement(Object object, ArrayList<String> info, ArrayList<GraphEntity> finalList){

		System.out.println("Attivata funzione su mappa");
		JSONObject obj = new JSONObject();
		
		if (object instanceof Map)
			obj.putAll((Map)object);
		
		JSONParser parser = new JSONParser();
		String[] nest = null;
		//Suppose that all attributes are at the same level of id
		if (info.get(0).split("\\.").length>1) // il doppio slash serve a fargli leggere il .
			nest = info.get(0).split("\\.");
		else nest = new String[] {info.get(0)};
		for (int i=0; i<nest.length ;i++){
			//If it is the last -> The nesting ends
			if (nest.length-i==1){
				//It can be a map (tipo interests.all)
				if (nest[i].trim().equals("*")){
					Set set = obj.keySet();
					for (Object e: set){
						String[] newInfo = info.subList(1, info.size()).toArray(new String[info.size()-1]);
						String ats=getKeyAttribs(newInfo, obj.get(e));
						finalList.add(new GraphEntity(e.toString(), ats));
					}
				}
				//Or a single value
				else {
					String identity = obj.get(nest[i]).toString();
					String[] newInfo = info.subList(1, info.size()).toArray(new String[info.size()-1]);
					String attribs=getKeyAttribs(newInfo, obj.get(nest[i]));
					finalList.add(new GraphEntity(identity, attribs));				}
			}
			//It is not the last -> continuous nesting
			else {
				//It could be a map
				if (nest[i].trim().equals("*")){
					//Must copy the remaining nested fields
					String remainingFields =String.join(".", Arrays.copyOfRange(nest, i, nest.length-1));
					ArrayList<String> newFields = new ArrayList<String>(info.subList(1, info.size()));
					newFields.add(0, remainingFields);
					Set<String> set= obj.keySet();
					for (String e: set){
						//rilanciare algoritmo su tutti el mappa
						runOnMapElement(obj.get(e), newFields, finalList);
					}
				}
				//Or could be a single value
				else{
					try {
						obj =(JSONObject) obj.get(nest[i]);
						String data=obj.toJSONString();
						obj = (JSONObject) parser.parse(data);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}

			}
		}

		return;
	}


	//This method reads the JSON and extracts the field for each relation
	private LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> json2map( ArrayList<String> key, ArrayList<String> value) throws FileNotFoundException, ParseException{
		JSONParser parser = new JSONParser();
		//This part because the input is in txt
		Scanner scanner = new Scanner(new File(jsonPath));
		scanner.useDelimiter("\n");
		while (scanner.hasNext()) {
			String linea = scanner.next();
			linea = linea+""+scanner.next();
			if (scanner.hasNext())
				scanner.next();
			linea = "{"+linea+"}";
			//System.out.println(linea);
			if(!linea.equals("{\n}")){
				JSONObject json = (JSONObject) parser.parse(linea);

				//Test metodo extractEntity
				ArrayList<GraphEntity> keyEntity = extractEntity(json, key);
				for (GraphEntity g: keyEntity){
					//System.out.println(g.getId() + " " + g.getAttr());
					ArrayList<GraphEntity> valueEntity = extractEntity(json, value);
					this.jsonMap.put(g, valueEntity);
				}
				/*for (GraphEntity g: valueEntity)
					System.out.println(g.getId() + " " + g.getAttr());*/
			}
		}
		return this.jsonMap;
	}

	private void printAr(String[] ar){
		for (int i=0; i<ar.length; i++)
			System.out.println(ar[i]+" ");
	}

	private void printFinalMap (LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap){
		String k="";
		for (Entry<GraphEntity, ArrayList<GraphEntity>> entry : jsonMap.entrySet())
		{
			k="["+entry.getKey().getId()+","+entry.getKey().getAttr()+"]";
			String inter="[";
			for (GraphEntity v : entry.getValue()){
				inter+="("+v.getId()+","+v.getAttr()+")-";
			}
			System.out.println(k + "/-/" + inter+"]");
		}
	}
	public LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> getJsonMap() {
		return this.jsonMap;
	}

	public static void main(String[] args) throws ParseException{
		String [] input = new String[]{"42080693,29337915","Intersection"}; //,47948672

		ConfigParser cf = new ConfigParser();
		try {
			cf.readFields();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		int numbersUsers = StringUtils.countMatches(input[0], ",") + 1;
//		String[] users = input[0].split(",");
//		//OPERATIONS
		if (input[1].equals("Intersection")){
			Intersection intersection = new Intersection();
			intersection.computeIntesection(cf.getJsonMap());
		}
//		if (input[1].equals("Difference")){
//			Difference difference = new Difference();
//			difference.computeDifference(cf.getJsonMap(), users);
//		}
	}
}
