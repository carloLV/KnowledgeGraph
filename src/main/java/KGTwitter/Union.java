package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import jsonManagerModels.GraphEntity;

public class Union {

	public Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> computeUnione(Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> relationsMap, String[] users, int numbersUsers) {
		SupportToOperations supportToOperations = new SupportToOperations(); 		//contiene i metodi in comune tra le operazioni di intersezione e differenza
		Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> resultUnion = new LinkedHashMap<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>>();

		for (String relation : relationsMap.keySet()) {
			ArrayList<GraphEntity> valueAttributes = new ArrayList<GraphEntity>();
			LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> mappa = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
			if (users[0].equals("all")){
				for (GraphEntity userAttr : relationsMap.get(relation).keySet()) {
					for (GraphEntity valueAttr : relationsMap.get(relation).get(userAttr)) {
						if(!valueAttributes.contains(valueAttr)){
							valueAttributes.add(valueAttr);
						}
					}
				}				
				mappa.put(new GraphEntity("all", "None:noattr"), valueAttributes);
				resultUnion.put(relation, mappa);
			}
			else{
				String utenti = "";
				LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> utentiDiInteresse = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
				for (int i = 0; i < numbersUsers; i++){
					utenti = utenti + users[i]+" ";
					GraphEntity userAttr = supportToOperations.getUserID_Attributes(relationsMap.get(relation), users[i]);
					utentiDiInteresse.put(userAttr, relationsMap.get(relation).get(userAttr));
				}
				for (GraphEntity userAttr : utentiDiInteresse.keySet()) {
					for (GraphEntity valueAttr : relationsMap.get(relation).get(userAttr)) {
						if(!valueAttributes.contains(valueAttr)){
							valueAttributes.add(valueAttr);
						}
					}
				}
				mappa.put(new GraphEntity(utenti, "None:noattr"), valueAttributes);
				resultUnion.put(relation, mappa);

			}
		}
		return resultUnion;		
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

//54613f71d4ac14770d4e9f8c è resente i tutti gli utenti e non ci sono doppioni nel risultato
//54780ed3d4ac148a337e9851 è presente solo nel primo utente
