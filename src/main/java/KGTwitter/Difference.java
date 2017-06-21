package KGTwitter;

import java.util.TreeMap;

public class Difference {

	public void computeDifference(TreeMap<String, TreeMap<String, String>> userID_fields, String[] users) {
		TreeMap<String, String> differenceMap = new TreeMap<String, String>();
		TreeMap<String, String> U1 = userID_fields.get(users[0]);
		TreeMap<String, String> U2 = userID_fields.get(users[1]);
		
		//System.out.println(userID_fields);
		for (String interU1 : U1.keySet()) {
			if(!U2.containsKey(interU1)){
				differenceMap.put(interU1, U1.get(interU1));
			}
		}
		for(String key : differenceMap.keySet()){
			System.out.println(key+" "+differenceMap.get(key));
		}
	}
}
