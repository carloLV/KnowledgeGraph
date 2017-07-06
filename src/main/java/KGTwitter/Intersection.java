package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;


import jsonManagerModels.GraphEntity;

public class Intersection {

	public Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> computeIntesection(Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> relationsMap, String[] users, int numbersUsers) {
		Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> resultIntersection = new LinkedHashMap<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>>();
		SupportToOperations supportToOperations = new SupportToOperations(); 		//contiene i metodi in comune tra le operazioni di intersezione e differenza

		for(String relation : relationsMap.keySet()){
			ArrayList<GraphEntity> valueAttr = new ArrayList<GraphEntity>();
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> mappa = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			if (users[0].equals("all")){	//si fa intersezione di tutti gli users nel file con l'utnete passato
				TreeMap<String, ArrayList<String>> allInterests = supportToOperations.getInterestsMap(relationsMap.get(relation)); //ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
				for (String keyInterest : allInterests.keySet()) {
					if (allInterests.get(keyInterest).size() == relationsMap.get(relation).size()){
						valueAttr.add(new GraphEntity(keyInterest, allInterests.get(keyInterest).get(0)));	//basta prendere il primo elemento perchè gli altri sono uguali
					}
				}
				mappa.put(new GraphEntity("all", "nothing"), valueAttr);
				resultIntersection.put(relation, mappa);
			}
			else {	//si fa l'intersezione solo tra gli utenti passati
				LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
				String utenti = "";
				for (int i = 0; i<numbersUsers; i++){
					utenti = utenti + users[i]+" ";
					GraphEntity userAttr = supportToOperations.getUserID_Attributes(relationsMap.get(relation), users[i]);
					utentiDiInteresse.put(userAttr, relationsMap.get(relation).get(userAttr));
				}
				TreeMap<String, ArrayList<String>> interessiDiInteresse = supportToOperations.getInterestsMap(utentiDiInteresse); 
				for (String keyInterest : interessiDiInteresse.keySet()) {
					if (interessiDiInteresse.get(keyInterest).size() == utentiDiInteresse.size()){
						valueAttr.add(new GraphEntity(keyInterest, interessiDiInteresse.get(keyInterest).get(0))); //basta prendere il primo elemento perchè gli altri sono uguali
					}
				}
				mappa.put(new GraphEntity(utenti, "nothing"), valueAttr);
				resultIntersection.put(relation, mappa);
			}
		}
		return resultIntersection;
	}
}

//interesezione ritorna resultIntersection, che è di size = 1 nel nostro caso. 
//come chiave questa mappa ha una coppia di stringhe composto da id e attributi, che possono essere:
//1) se l'user passato in input è "all"(tutti gli user), allora si ha "all" come id e "nothing" come attributi
//2) se l'user in input sono diversi allora si ha una "concatenazione degli id degli user" come id e "nothing" come attributi 

//come valore invece si ha un  ArrayList che contiene gli interessi che rappresentano l'intersezione degli interessi degli user passati
//o l'intersezione tra gli interessi di tutti gli user presenti nel file di input

//SE SI PASSANO PIU' UTENTI

//INTERSEZIONE:
//42080693 29337915 47948672  nothing
//5457c811d4ac147c2bf5df55 display:Cars 
//5457c811d4ac147c2bf5df59 display:Luxury vehicles 
//5457c85bd4ac147ca1acdd53 display:Extreme sports 
//5457c85bd4ac147ca1acdd54 display:Formula 1 racing 

//SE SI PASSA "all" SI HA:

//INTERSEZIONE:
//all nothing
//5457c811d4ac147c2bf5df55 display:Cars 
//5457c811d4ac147c2bf5df59 display:Luxury vehicles 
//5457c85bd4ac147ca1acdd53 display:Extreme sports 
//5457c85bd4ac147ca1acdd54 display:Formula 1 racing 


//547506cad4ac146fa550128e il primo e l'ultimo utente hanno in comune questo interesse'
//il primo e il secondo hanno 5457c811d4ac147c2bf5df5d