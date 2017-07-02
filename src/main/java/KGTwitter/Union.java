package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import jsonManagerModels.GraphEntity;

public class Union {

	public LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> computeUnione(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap, String[] users, int numbersUsers) {
		SupportToOperations supportToOperations = new SupportToOperations(); 		//contiene i metodi in comune tra le operazioni di intersezione e differenza
		LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> resultIntersection = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
		ArrayList<GraphEntity> valueAttributes = new ArrayList<GraphEntity>();
		
		System.out.println("\nUNION:");	
		if (users[0].equals("all")){
			for (GraphEntity userAttr : jsonMap.keySet()) {
				for (GraphEntity valueAttr : jsonMap.get(userAttr)) {
					if(!valueAttributes.contains(valueAttr)){
						valueAttributes.add(valueAttr);
					}
				}
			}
			resultIntersection.put(new GraphEntity("all", "nothing"), valueAttributes);
		}
		else{
			String utenti = "";
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			for (int i = 0; i < numbersUsers; i++){
				utenti = utenti + users[i]+" ";
				GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, users[i]);
				utentiDiInteresse.put(userAttr, jsonMap.get(userAttr));
			}
			for (GraphEntity userAttr : utentiDiInteresse.keySet()) {
				for (GraphEntity valueAttr : jsonMap.get(userAttr)) {
					if(!valueAttributes.contains(valueAttr)){
						valueAttributes.add(valueAttr);
					}
				}
			}
			resultIntersection.put(new GraphEntity(utenti, "nothing"), valueAttributes);
		}
		
		for (GraphEntity userAttr : resultIntersection.keySet()) {
			System.out.println(userAttr.getId() +" "+userAttr.getAttr());
			for (GraphEntity valueAttr : resultIntersection.get(userAttr)) {
				System.out.println(valueAttr.getId()+" "+valueAttr.getAttr());
			}
			
		}
		return resultIntersection;		
	}
}



//in caso si passano più utenti si ha questo output:

//UNION:
//42080693 29337915 47948672  nothing
//54613f71d4ac14770d4e9f8c display:Television 
//54780ed3d4ac148a337e9851 display:History 
//55f1f6a192cffb2b365a74bb display:Premier League soccer 
//57c9c32692cffb3b2a085fd2 display:Bundesliga league soccer 

//SE SI PASSA "all" SI HA:

//UNION:
//all nothing
//54613f71d4ac14770d4e9f8c display:Television 
//54780ed3d4ac148a337e9851 display:History 
//55f1f6a192cffb2b365a74bb display:Premier League soccer 
//57c9c32692cffb3b2a085fd2 display:Bundesliga league soccer 
