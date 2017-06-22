package KGTwitter;

import java.util.ArrayList;
import java.util.TreeMap;

public class Intersection {

	public void computeIntesection(TreeMap<String, TreeMap<String, String>> userID_fields, int numbersUsers, String[] users) {
		//in questa mappa metto come key=InterestID, value = Stringa del compo di interesse
		//esempio key=5457c811d4ac147c2bf5df55, value = {"score":5,"level":"High","display":"Cars","name":"Cars","category":....
		//quindi a partà di key ho tanto value
		TreeMap<String, ArrayList<String>> intersectionMap = new TreeMap<String, ArrayList<String>>();
		TreeMap<String, String> intID_info = new TreeMap<String, String>();
		if(numbersUsers > 1){
			for(int i = 0; i < numbersUsers; i++){
				intID_info = userID_fields.get(users[i]);
				for (String id : intID_info.keySet()) {
					if(intersectionMap.containsKey(id)){
						intersectionMap.get(id).add(intID_info.get(id));
					}
					else{
						ArrayList<String> list =  new ArrayList<String>();
						list.add(intID_info.get(id));
						intersectionMap.put(id, list);
					}
				}
			}
		}
		else{
			for (String user : userID_fields.keySet()) {
				intID_info = userID_fields.get(user);
				for (String id : intID_info.keySet()) {
					if(intersectionMap.containsKey(id)){
						intersectionMap.get(id).add(intID_info.get(id));
					}
					else{
						ArrayList<String> list =  new ArrayList<String>();
						list.add(intID_info.get(id));
						intersectionMap.put(id, list);
					}
				}
			}
		}
		for (String idInterest : intersectionMap.keySet()) {
			ArrayList<String> fieldsInterests = intersectionMap.get(idInterest);
			if (numbersUsers == 1)
				numbersUsers = userID_fields.size(); //input è di confrontare tutti gli untenti "all"
			//se per lo stesso interesse ho una size pari al numero di user...allora tali utenti hanno in comune questo interesse
			if(fieldsInterests.size() == numbersUsers)
				System.out.println(idInterest+" "+fieldsInterests.get(0)); //basta stampare uno perchè altri sono differenti solo per "score" e "level"
		}
	}
}

