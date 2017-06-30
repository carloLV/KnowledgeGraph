package KGTwitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import jsonManagerModels.GraphEntity;

public class SupportToOperations {

	//ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
	//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali
	public TreeMap<String, ArrayList<String>> getInterestsMap(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap) {
		TreeMap<String, ArrayList<String>> allInterest = new TreeMap<String, ArrayList<String>>();	//la chiave è id del interesse e come valore la sua descrizioni
		for (GraphEntity userAttr : jsonMap.keySet()) {
			ArrayList<GraphEntity> userAttr_interests = jsonMap.get(userAttr);
			for (GraphEntity valueAttr : userAttr_interests) {
				if(allInterest.containsKey(valueAttr.getId()))
					allInterest.get(valueAttr.getId()).add(valueAttr.getAttr());
				else{
					ArrayList<String> attributes =  new ArrayList<String>();
					attributes.add(valueAttr.getAttr());
					allInterest.put(valueAttr.getId(), attributes);
				}
			}
		}
		return allInterest;	 
	}

	//ritorna una coppia di stringhe di cui la prima è l'id dello user passato e la seconda i suoi attributi
	public GraphEntity getUserID_Attributes(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap, String user) {
		boolean label = true;
		Iterator<GraphEntity> iter =  jsonMap.keySet().iterator();
		GraphEntity userAttr = null;
		while(iter.hasNext() && label){
			userAttr = iter.next();
			if(userAttr.getId().equals(user))
				label = false;
		}
		return userAttr;
	}

}
