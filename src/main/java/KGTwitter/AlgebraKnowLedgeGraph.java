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
		
		//questi saranno i parametri di input dati dal terminale
		//"userID1,userID2,.....","algoritmo"
		String [] input = new String[]{"42080693,29337915","Difference"}; //,47948672
		
		//questa mappa ha come chiave userID e come valore un altra mappa che ha come chiave interestID 
		//e come valore la stringa formata dai corrispondenti campi (es: {"score":5,"level":"High","display":"Cars","name":"Cars",...}) 
		TreeMap<String, TreeMap<String, String>> userID_fields =  new TreeMap<String,  TreeMap<String, String>>();
		Scanner scanner = new Scanner(new File("/Users/davinderkumar/Desktop/docs.txt"));
		scanner.useDelimiter("\n");
		JSONParser parser = new JSONParser();
		while (scanner.hasNext()) {
			String linea = scanner.next();
			linea = linea+""+scanner.next();
			scanner.next();
			linea = "{"+linea+"}";
			if(!linea.equals("{\n}")){
				JSONObject json = (JSONObject) parser.parse(linea);
				JSONObject info = (JSONObject)json.get("info");

				JSONObject interests = (JSONObject)info.get("interests");
				JSONObject all = (JSONObject)interests.get("all");
				List<String> interestIDS = new ArrayList<String>(all.keySet());

				TreeMap<String, String> interestID_info = new TreeMap<String, String>();
				for (String id : interestIDS){
					JSONObject intID = (JSONObject)all.get(id);
					String line = intID.toString();
					interestID_info.put(id, line);
				}
				userID_fields.put(json.get("user").toString(), interestID_info);
			}
		}
		int numbersUsers = StringUtils.countMatches(input[0], ",") + 1;
		String[] users = input[0].split(",");

		//OPERATIONS
		if (input[1].equals("Intersection")){
			Intersection intersection = new Intersection();
			intersection.computeIntesection(userID_fields, numbersUsers, users);
		}
		if (input[1].equals("Difference")){
			Difference difference = new Difference();
			difference.computeDifference(userID_fields, users);
		}
	}
}
		

