package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;


import jsonManagerModels.GraphEntity;

public class Intersection {

	public void computeIntesection(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap, String[] users, int numbersUsers) {
		
		SupportToOperations supportToOperations = new SupportToOperations(); 		//contiene i metodi in comune tra le operazioni di intersezione e differenza
		System.out.println("\nINTERSEZIONE:");															//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali			
		if (numbersUsers == 1){
			TreeMap<String, ArrayList<String>> allInterests = supportToOperations.getInterestsMap(jsonMap); //ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
			GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, users[0]);	//ritorna una coppia di stringhe di cui la prima è l'id dello user passato e la seconda i suoi attributi
			System.out.println(userAttr.getId()+" "+userAttr.getAttr());
			for (String keyInterest : allInterests.keySet()) {
				if (allInterests.get(keyInterest).size() == jsonMap.size())
					System.out.println(keyInterest+" "+allInterests.get(keyInterest).get(0)); //basta prendere il primo elemento perchè gli altri sono uguali
			}
		}
		else{
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			for (int i =0; i<numbersUsers; i++){
				GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, users[i]);
				utentiDiInteresse.put(userAttr, jsonMap.get(userAttr));
			}
			TreeMap<String, ArrayList<String>> interessiDiInteresse = supportToOperations.getInterestsMap(utentiDiInteresse); 
			for (String keyInterest : interessiDiInteresse.keySet()) {
				if (interessiDiInteresse.get(keyInterest).size() == utentiDiInteresse.size())
					System.out.println(keyInterest+" "+interessiDiInteresse.get(keyInterest).get(0)); //basta prendere il primo elemento perchè gli altri sono uguali
			}
		}
	}
}
