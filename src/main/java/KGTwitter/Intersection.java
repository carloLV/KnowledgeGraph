package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;


import jsonManagerModels.GraphEntity;

public class Intersection {

	public LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> computeIntesection(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> jsonMap, String[] users, int numbersUsers) {
		LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> resultIntersection = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
		ArrayList<GraphEntity> valueAttr = new ArrayList<GraphEntity>();

		SupportToOperations supportToOperations = new SupportToOperations(); 		//contiene i metodi in comune tra le operazioni di intersezione e differenza
		System.out.println("\nINTERSEZIONE:");										//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali			

		if (users[0].equals("all")){	//si fa intersezione di tutti gli users nel file con l'utnete passato
			TreeMap<String, ArrayList<String>> allInterests = supportToOperations.getInterestsMap(jsonMap); //ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
			//GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, users[0]);	//ritorna una coppia di stringhe di cui la prima è l'id dello user passato e la seconda i suoi attributi
			//System.out.println(userAttr.getId()+" "+userAttr.getAttr());
			System.out.println("all"+" nothing");
			for (String keyInterest : allInterests.keySet()) {
				if (allInterests.get(keyInterest).size() == jsonMap.size()){
					valueAttr.add(new GraphEntity(keyInterest, allInterests.get(keyInterest).get(0)));
					System.out.println(keyInterest+" "+allInterests.get(keyInterest).get(0)); //basta prendere il primo elemento perchè gli altri sono uguali
				}
			}
			resultIntersection.put(new GraphEntity("all", "nothing"), valueAttr);
		}
		else {	//si fa l'intersezione solo tra gli utenti passati
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			String utenti = "";
			for (int i =0; i<numbersUsers; i++){
				utenti = utenti + users[i]+" ";
				GraphEntity userAttr = supportToOperations.getUserID_Attributes(jsonMap, users[i]);
				utentiDiInteresse.put(userAttr, jsonMap.get(userAttr));
			}
			System.out.println(utenti+" nothing");
			TreeMap<String, ArrayList<String>> interessiDiInteresse = supportToOperations.getInterestsMap(utentiDiInteresse); 
			for (String keyInterest : interessiDiInteresse.keySet()) {
				if (interessiDiInteresse.get(keyInterest).size() == utentiDiInteresse.size()){
					valueAttr.add(new GraphEntity(keyInterest, interessiDiInteresse.get(keyInterest).get(0)));
					System.out.println(keyInterest+" "+interessiDiInteresse.get(keyInterest).get(0)); //basta prendere il primo elemento perchè gli altri sono uguali
				}
			}
			resultIntersection.put(new GraphEntity(utenti, "nothing"), valueAttr);
		}
		return resultIntersection;
	}
}

//interesezione ritorna resultIntersection, che è di size = 1. 
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