package KGTwitter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AlgebraKnowLedgeGraph 
{
	public static void main( String[] args ) throws IOException, Exception{
		
//		//questi saranno i parametri di input dati dal terminale
//		//"userID1,userID2,.....","algoritmo"
//		String [] input = new String[]{"42080693,29337915","Difference"}; //,47948672
//		
//		//questa mappa ha come chiave userID e come valore un altra mappa che ha come chiave interestID 
//		//e come valore la stringa formata dai corrispondenti campi (es: {"score":5,"level":"High","display":"Cars","name":"Cars",...}) 
//		TreeMap<String, TreeMap<String, String>> userID_fields =  new TreeMap<String,  TreeMap<String, String>>();
//		Scanner scanner = new Scanner(new File("/Users/davinderkumar/Desktop/docs.txt"));
//		scanner.useDelimiter("\n");
//		JSONParser parser = new JSONParser();
//		while (scanner.hasNext()) {
//			String linea = scanner.next();
//			linea = linea+""+scanner.next();
//			scanner.next();
//			linea = "{"+linea+"}";
//			if(!linea.equals("{\n}")){
//				JSONObject json = (JSONObject) parser.parse(linea);
//				JSONObject info = (JSONObject)json.get("info");
//
//				JSONObject interests = (JSONObject)info.get("interests");
//				JSONObject all = (JSONObject)interests.get("all");
//				List<String> interestIDS = new ArrayList<String>(all.keySet());
//
//				TreeMap<String, String> interestID_info = new TreeMap<String, String>();
//				for (String id : interestIDS){
//					JSONObject intID = (JSONObject)all.get(id);
//					String line = intID.toString();
//					interestID_info.put(id, line);
//				}
//				userID_fields.put(json.get("user").toString(), interestID_info);
//			}
//		}
//		int numbersUsers = StringUtils.countMatches(input[0], ",") + 1;
//		String[] users = input[0].split(",");

//		//OPERATIONS
//		if (input[1].equals("Intersection")){
//			Intersection intersection = new Intersection();
//			intersection.computeIntesection(userID_fields, numbersUsers, users);
//		}
//		if (input[1].equals("Difference")){
//			Difference difference = new Difference();
//			difference.computeDifference(userID_fields, users);
//		}
	}
}
		



//package KGTwitter;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.TreeMap;
//
//import jsonManagerModels.GraphEntity;
//
//public class Intersection {
//
//	public void computeIntesection(TreeMap<String, TreeMap<String, String>> userID_fields, int numbersUsers, String[] users) {
//		//in questa mappa metto come key=InterestID, value = Stringa del compo di interesse
//		//esempio key=5457c811d4ac147c2bf5df55, value = {"score":5,"level":"High","display":"Cars","name":"Cars","category":....
//		//quindi a partà di key ho tanto value
//		TreeMap<String, ArrayList<String>> intersectionMap = new TreeMap<String, ArrayList<String>>();
//		TreeMap<String, String> intID_info = new TreeMap<String, String>();
//		if(numbersUsers > 1){
//			for(int i = 0; i < numbersUsers; i++){
//				intID_info = linkedHashMap.get(users[i]);
//				for (String id : intID_info.keySet()) {
//					if(intersectionMap.containsKey(id)){
//						intersectionMap.get(id).add(intID_info.get(id));
//					}
//					else{
//						ArrayList<String> list =  new ArrayList<String>();
//						list.add(intID_info.get(id));
//						intersectionMap.put(id, list);
//					}
//				}
//			}
//		}
//		else{
//			for (String user : linkedHashMap.keySet()) {
//				intID_info = linkedHashMap.get(user);
//				for (String id : intID_info.keySet()) {
//					if(intersectionMap.containsKey(id)){
//						intersectionMap.get(id).add(intID_info.get(id));
//					}
//					else{
//						ArrayList<String> list =  new ArrayList<String>();
//						list.add(intID_info.get(id));
//						intersectionMap.put(id, list);
//					}
//				}
//			}
//		}
//		for (String idInterest : intersectionMap.keySet()) {
//			ArrayList<String> fieldsInterests = intersectionMap.get(idInterest);
//			if (numbersUsers == 1)
//				numbersUsers = linkedHashMap.size(); //input è di confrontare tutti gli untenti "all"
//			//se per lo stesso interesse ho una size pari al numero di user...allora tali utenti hanno in comune questo interesse
//			if(fieldsInterests.size() == numbersUsers)
//				System.out.println(idInterest+" "+fieldsInterests.get(0)); //basta stampare uno perchè altri sono differenti solo per "score" e "level"
//		}
//	}
//}
//



//package KGTwitter;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.TreeMap;
//
//import jsonManagerModels.GraphEntity;
//
//public class Difference {
//
//	public void computeDifference(TreeMap<String, TreeMap<String, String>> userID_fields, String[] users) {
//		TreeMap<String, String> differenceMap = new TreeMap<String, String>();
//		TreeMap<String, String> U1 = linkedHashMap.get(users[0]);
//		TreeMap<String, String> U2 = linkedHashMap.get(users[1]);
//		
//		//System.out.println(userID_fields);
//		for (String interU1 : U1.keySet()) {
//			if(!U2.containsKey(interU1)){
//				differenceMap.put(interU1, U1.get(interU1));
//			}
//		}
//		for(String key : differenceMap.keySet()){
//			System.out.println(key+" "+differenceMap.get(key));
//		}
//	}
//}


