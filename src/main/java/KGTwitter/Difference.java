package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import jsonManagerModels.GraphEntity;

public class Difference {

	public void computeDifference(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap, String[] users, int numbersUsers) {

		SupportToOperations supportToOperations =  new SupportToOperations(); //contiene i metodi in comune tra le operazioni di intersezione e differenza
		System.out.println("\nDIFFERENZA");
		GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, users[0]); //ritorna una coppia di stringhe di cui la prima è l'id dello user passato e la seconda i suoi attributi
		ArrayList<GraphEntity> interUserCercato = jsonMap.get(userAttr);	//qui dentro si hanno gli interessi dell'utente userAttr (riga precedente)
		jsonMap.remove(userAttr);	//perchè andrò a fare la differenza tra gli interessi dell'utente scelto sopra e tutti gli interessi degli altri utenti..quindi devo togliere da jsonMap l'utente su cui si vuole fare la differenza
		ArrayList<GraphEntity> difference =  new ArrayList<GraphEntity>();	//conterrà il risultato
		System.out.println(userAttr.getId()+" "+userAttr.getAttr());

		if(numbersUsers == 1){
			TreeMap<String, ArrayList<String>> allInterest = supportToOperations.getInterestsMap(jsonMap);	//ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
			//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali
			for (GraphEntity valueAttr : interUserCercato) {
				if(!allInterest.containsKey(valueAttr.getId()))
					difference.add(valueAttr);		//se un interesse che ha user di differenza scelto non è presente negli altri user allora tale interesse farà parte della differenza
			}
			for(GraphEntity graphEntity : difference){
				System.out.println(graphEntity.getId()+" "+graphEntity.getAttr());
			}
		}
		else{
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			for (int i = 1; i<numbersUsers; i++){
				GraphEntity UAttr = supportToOperations.getUserID_Attributes(jsonMap, users[i]);
				utentiDiInteresse.put(UAttr, jsonMap.get(UAttr));
			}
			TreeMap<String, ArrayList<String>> interessiDiInteresse = supportToOperations.getInterestsMap(utentiDiInteresse); 
			for (GraphEntity valueAttr : interUserCercato) {
				if(!interessiDiInteresse.containsKey(valueAttr.getId()))
					difference.add(valueAttr);		//se un interesse che ha user di differenza scelto non è presente negli altri user allora tale interesse farà parte della differenza
			}
			for(GraphEntity graphEntity : difference){
				System.out.println(graphEntity.getId()+" "+graphEntity.getAttr());
			}
		}
	}
}
