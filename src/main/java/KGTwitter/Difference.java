package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import jsonManagerModels.GraphEntity;

public class Difference {

	public Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> computeDifference(Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> relationsMap, String[] users, int numbersUsers) {
		Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> resultDifference = new LinkedHashMap<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>>();
		SupportToOperations supportToOperations =  new SupportToOperations(); //contiene i metodi in comune tra le operazioni di intersezione e differenza
		
		for (String relation : relationsMap.keySet()) {
			GraphEntity userAttr = supportToOperations.getUserID_Attributes(relationsMap.get(relation), users[0]); //ritorna una coppia di stringhe di cui la prima è l'id dello user passato e la seconda i suoi attributi
			ArrayList<GraphEntity> interUserCercato = relationsMap.get(relation).get(userAttr);	//qui dentro si hanno gli interessi dell'utente userAttr (riga precedente)
			relationsMap.get(relation).remove(userAttr);	//perchè andrò a fare la differenza tra gli interessi dell'utente scelto sopra e tutti gli interessi degli altri utenti..quindi devo togliere da jsonMap l'utente su cui si vuole fare la differenza
			ArrayList<GraphEntity> difference =  new ArrayList<GraphEntity>();	//conterrà il risultato
			
			if(numbersUsers == 1){
				TreeMap<String, ArrayList<String>> allInterest = supportToOperations.getInterestsMap(relationsMap.get(relation));	//ritorna una mappa che ha come chiave TUTTI gli ID_interesse e come valore un arraylist in cui ogni elemento sono gli "attributi" di interesse..cioè la descrizione dell'interesse
				//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali
				for (GraphEntity valueAttr : interUserCercato) {
					if(!allInterest.containsKey(valueAttr.getId()))
						difference.add(valueAttr);		//se un interesse che ha user di differenza scelto non è presente negli altri user allora tale interesse farà parte della differenza
				}
			}
			else{
				LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
				for (int i = 1; i<numbersUsers; i++){
					GraphEntity UAttr = supportToOperations.getUserID_Attributes(relationsMap.get(relation), users[i]);
					utentiDiInteresse.put(UAttr, relationsMap.get(relation).get(UAttr));
				}
				TreeMap<String, ArrayList<String>> interessiDiInteresse = supportToOperations.getInterestsMap(utentiDiInteresse); 
				for (GraphEntity valueAttr : interUserCercato) {
					if(!interessiDiInteresse.containsKey(valueAttr.getId()))
						difference.add(valueAttr);		//se un interesse che ha user di differenza scelto non è presente negli altri user allora tale interesse farà parte della differenza
				}
			}
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> mappa = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			System.out.println(userAttr.getId());
			System.out.println("attributi: "+userAttr.getAttr().length());
			String attrUser = null;
			if (userAttr.getAttr().length() == 0){
				attrUser = "None:noattr";
			}
			else{
				//TODO da definire, nel nostro caso non ci sono attributi
			}
			
			mappa.put(new GraphEntity(userAttr.getId(), attrUser), difference);
			resultDifference.put(relation, mappa);
		}
		return resultDifference;
	}
}

//la differenza ritorna resultDifference, che è di size = 1 nel nostro caso, e ha come chiave una coppia di stringhe che sono id_utente e attributi_utente
//quando si passa un unico utnete id_utente e attributi_utente fanno riferimento a questo utente
//quando si passano una serie di utenit id_utente e attributi_utente fanno riferimento al primo utente della serie
//Come valore si ha un ArrayList che contiene gli interessi che rappresentano la differenza degli interessi dell'unico user passato con gli altri presenti nel file
//o del primo user passato con gli altri passati in input


//SE SI PASSANO PIU' UTENTI (42080693,29337915) SI HA: 

//DIFFERENZA
//42080693 
//54780ed3d4ac148a337e9851 display:History 
//55f1f6a192cffb2b365a74bb display:Premier League soccer 
//57c9c32692cffb3b2a085fd2 display:Bundesliga league soccer 
//55afc38792cffb786d83f900 display:Hobbies and interests 
//55afc38492cffb786d83f826 display:Porsche (brand) 
//5457c85bd4ac147ca1acdd76 display:Soccer 
//56a6605592cffb338344088c display:Chris Pratt (celebrity) 
//5457c85bd4ac147ca1acdd6d display:Motorcycle racing 
//55afc38892cffb786d83f944 display:Mercedes-Benz (brand) 
//55afc38792cffb786d83f912 display:MotoGP 


//SE SI PASSA UN SOLO UTENTE SI HA:

//DIFFERENZA
//42080693 
//54780ed3d4ac148a337e9851 display:History 
//55f1f6a192cffb2b365a74bb display:Premier League soccer 
//57c9c32692cffb3b2a085fd2 display:Bundesliga league soccer 
//55afc38792cffb786d83f900 display:Hobbies and interests 
//55afc38492cffb786d83f826 display:Porsche (brand) 
//5457c85bd4ac147ca1acdd76 display:Soccer 
//56a6605592cffb338344088c display:Chris Pratt (celebrity) 
//5457c85bd4ac147ca1acdd6d display:Motorcycle racing 
//55afc38792cffb786d83f912 display:MotoGP 

//la penultima riga non c'è più perchè nel secondo caso si fa la differenza anche con user 47948672 che ha Mercedes-Benz
//mentre nel primo caso il secondo utente 29337915 non ha Mercedez-Benz
