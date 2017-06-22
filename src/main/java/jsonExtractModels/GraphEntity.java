package jsonExtractModels;

/* Questa classe modella le Entità che formano i nodi del grafo. Ogni entità è formata
 * da un ID e dall'insieme degli attributi.
 */

public class GraphEntity {

	private String id, attr;
	
	public GraphEntity(String id, String attr){
		this.id=id;
		this.attr=attr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	/*
	 * Le mappe che rappresentano le relazioni saranno formate da:
	 * Chiave: L'entità e la sua lista di attributi (GraphEntity(user.id,user.attr))
	 * Valore: Una lista delle entità a cui è collegato [GraphEntity(interest1.id,interest1.attr),..,GraphEntity(interestn.id,interestn.attr)]
	 */
	
}
