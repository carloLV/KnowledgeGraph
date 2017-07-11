package userDefinedFunction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import jsonManagerModels.GraphEntity;

/**************************************************************************************************/
/*** The purpose of this package is to let every user to define his functions,                  ***/
/*** to extract some fields from the JSON. It is an extension of the package jsonManager.       ***/
/*** The difference is that that package just extracts maps or single values from the JSON,     ***/
/*** while this one permits to the user to modify the output of jsonManager and write it in the ***/
/*** same structure, but in a more suitable format. Follows an example, adapted for our purpose.***/
/**************************************************************************************************/


public class UDF {

	
	//This is the method that operates the adjustment
	public LinkedHashMap<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> adjustmentMethod(LinkedHashMap<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> relations, String workOn){
		if (workOn.equals("None"))//In case no UDF are required we just pass None instead of relation
			return relations;
		LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> map2transform = relations.get(workOn);
		LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> transformedMap = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
		//Now we have map and start working on it.
		/*** MODIFY THIS PART TO CHANGE BEHAVIOUR***/
		
		//Sample output for our case: [55afc38492cffb786d83f822,parents:["55afc38492cffb786d83f811"] ]/-/[(true,)-]
		//Desired format: [55afc38492cffb786d83f822]/-/[55afc38492cffb786d83f811]
		for (GraphEntity g: map2transform.keySet()){
			//In this case all information is contained in key, so we iterate on those
			GraphEntity newEntity = new GraphEntity(g.getId(), "None:noattr");
			ArrayList<GraphEntity> values=new ArrayList<GraphEntity>();
			//Now we parse attribs and get the values we need to reconstruct map
			String attribs = g.getAttr().split(":")[1];//this gets only id of parents;
			attribs = attribs.substring(1, attribs.length()-2);//remove [] from edges
			
			String[] tokens = attribs.split(",");//get all parents
			//Now we can reconstruct the map
			for (String parent : tokens)
				if (parent != null && parent.length()>2){
					values.add(new GraphEntity(parent.substring(1,parent.length()-1), "None:noattr"));
				}
			transformedMap.put(newEntity, values);
		}
		relations.put(workOn, transformedMap);
		return relations;
	}

}
