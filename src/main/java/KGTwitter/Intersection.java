package KGTwitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.TreeSet;


import jsonManagerModels.GraphEntity;

public class Intersection {

	public void computeIntesection(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap) {
		
		TreeSet<String> allUsers = new TreeSet<String>();	//conterrà tutti gli utenti
		TreeMap<String, ArrayList<String>> allInterest = new TreeMap<String, ArrayList<String>>();	//la chiave è id del interesse e come valore la sua descrizioni
																									//nella lista interna gli elementi saranno tutti uguali
		boolean label = true;
		Iterator<GraphEntity> iter =  jsonMap.keySet().iterator();
		GraphEntity userAttr1 = iter.next();	
		allUsers.add((userAttr1.getId()));	//raccolta di id_utente da userAtt1
		while (iter.hasNext() && label){	//qui vado a verificare se gli attributi di userAttr1 sono uguali agli attributi di tutti gli altri utenti
			GraphEntity userAttr2 = iter.next();	
			if(!userAttr1.getAttr().equals(userAttr2.getAttr())){
				label = false;
			}
			allUsers.add((userAttr2.getId()));	//raccolta di id utente da userAtt2
		}
		if(label){	//se entro nel if..tutti gli utenti hanno gli stessi attributi...adesso bisogna raccogliere tutti gli interessi di tutti gli utenti
			for (GraphEntity userAttr : jsonMap.keySet()) {
				ArrayList<GraphEntity> userAttr_interests = jsonMap.get(userAttr);
				for (GraphEntity valueAttr : userAttr_interests) {
					if(allInterest.containsKey(valueAttr.getId())){
						allInterest.get(valueAttr.getId()).add(valueAttr.getAttr());
					}
					else{
						ArrayList<String> attributes =  new ArrayList<String>();
						attributes.add(valueAttr.getAttr());
						allInterest.put(valueAttr.getId(), attributes);
					}
				}
			}
			if (label){	//se entro in questo if significa che tutti gli utenti hanno gli stessi attributi
				System.out.println("\nINTERSEZIONE:");
				for (String keyInterest : allInterest.keySet()) {
					if (allInterest.get(keyInterest).size() == jsonMap.size()){	
						System.out.println(allUsers+" "+keyInterest+" "+allInterest.get(keyInterest).get(0));
					}
				}
			}
		}
	}
}

//gli attributi degli utenti sono stati confrontati mentre quelli di interessi ho considerato che a parità di ID_INTERESSE gli attributi siano identici
