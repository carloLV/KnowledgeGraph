package KGTwitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import jsonManagerModels.GraphEntity;

public class Intersection {

	public void computeIntesection(LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> linkedHashMap) {
		LinkedHashMap<GraphEntity, ArrayList<GraphEntity>> intersectionMap = new LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>();
		for (GraphEntity user1 : linkedHashMap.keySet()) {
			for (GraphEntity user2 : linkedHashMap.keySet()) {
				if(!user1.getId().equals(user2.getId())){
					int numbersKeyAttributesU1 = StringUtils.countMatches(user1.getAttr(), ",") + 1;
					int numbersKeyAttributesU2 = StringUtils.countMatches(user2.getAttr(), ",") + 1;
					String [] keyAttributesU1 = user1.getAttr().split(",");
					String [] keyAttributesU2 = user2.getAttr().split(",");
					if (numbersKeyAttributesU1 == numbersKeyAttributesU2){	
						boolean label = true;
						for (int i = 0; i< numbersKeyAttributesU1 && label; i++){
							if(!keyAttributesU1[i].equals(keyAttributesU2[i])){
								label = false;
							}
						}
						if(label){	//se entro in questo if significa che si hanno due utenti diversi con gli stessi atrributesKey
							for (GraphEntity interestU1 : linkedHashMap.get(user1)) {	//itero sugli interessi degli user
								String interertID_U1 = interestU1.getId();
								boolean k = true;
								ArrayList<GraphEntity> interestsU2 = linkedHashMap.get(user2);
								for (int i = 0; i<interestsU2.size() && k; i++) {
									if(interertID_U1.equals(interestsU2.get(i).getId())){	//stesso interest ID se entro nel if
										k = false;
										int numbersValueAttributesU1 = StringUtils.countMatches(interestU1.getAttr(), ",") + 1;
										int numbersValueAttributesU2 = StringUtils.countMatches(interestsU2.get(i).getAttr(), ",") + 1;
										String [] valueAttributesU1 = user1.getAttr().split(",");
										String [] valueAttributesU2 = user2.getAttr().split(",");
										if(numbersValueAttributesU1 == numbersValueAttributesU2){
											label = true;
											for (int j = 0; j< numbersValueAttributesU1 && label; j++){
												if(!valueAttributesU1[j].equals(valueAttributesU2[j])){
													label = false;
												}
											}
											if(label){	//due utenti diversi hanno stessi keyAttributes ed oltre ad avere lo stesso id_Interesse in comune hanno anche valueAttributes uguali 
												String keyU1U2 = user1.getId()+","+user2.getId();
												GraphEntity U1U2_key_attributes = new GraphEntity(keyU1U2, user1.getAttr());
												GraphEntity U1U2_value_attributes = new GraphEntity(interertID_U1, user1.getAttr());
												if (intersectionMap.keySet().contains(U1U2_key_attributes)){
													intersectionMap.get(U1U2_key_attributes).add(U1U2_value_attributes);
												}
												else{
													ArrayList<GraphEntity> interestsCommon =  new ArrayList<GraphEntity>();
													interestsCommon.add(U1U2_value_attributes);
													intersectionMap.put(U1U2_key_attributes, interestsCommon);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(linkedHashMap.size() ==intersectionMap.size()){
			//stampare la roba dentro intersectionMap
			String k="";
			for (Entry<GraphEntity, ArrayList<GraphEntity>> entry : intersectionMap.entrySet()){
				k="["+entry.getKey().getId()+","+entry.getKey().getAttr()+"]";
				String inter="[";
				for (GraphEntity v : entry.getValue()){
					inter+="("+v.getId()+","+v.getAttr()+")-";
				}
				System.out.println(k + "/-/" + inter+"]");
			}
		}
	}
}

