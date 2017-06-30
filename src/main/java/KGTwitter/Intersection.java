package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;


import jsonManagerModels.GraphEntity;

public class Intersection {

	public void computeIntesection(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap, String user) {
		
		SupportToOperations supportToOperations = new SupportToOperations(); 		//contiene i metodi in comune tra le operazioni di intersezione e differenza
		GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, user);	//ritorna una coppia di stringhe di cui la prima è l'id dello user passato e la seconda i suoi attributi
		
		TreeMap<String, ArrayList<String>> allInterest = supportToOperations.getInterestsMap(jsonMap); //ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
		System.out.println("\nINTERSEZIONE:");															//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali			
		System.out.println(userAttr.getId()+" "+userAttr.getAttr());
		for (String keyInterest : allInterest.keySet()) {
			if (allInterest.get(keyInterest).size() == jsonMap.size())
				System.out.println(keyInterest+" "+allInterest.get(keyInterest).get(0)); //basta prendere il primo elemento perchè gli altri sono uguali
		}
	}
}
