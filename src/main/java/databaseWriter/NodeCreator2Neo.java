package databaseWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import jsonManagerModels.*;

public class NodeCreator2Neo {
	
	//private static final File DB_PATH=new File("/Users/davinderkumar/Documents/neo4j-community-3.3.0-alpha02/data/databases/graph.db");
	private static final File DB_PATH=new File("/home/bum-bum/Desktop/neo4j-community-3.2.1/data/databases/graph.db");
	Relationship relationship;
	RelationshipType reltype;

	public GraphDatabaseService graphDb;


	public NodeCreator2Neo(){
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
	}




	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		} );
	}
	
	public void map2graph(Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> graph){
		Node keyEntity=null;
		Node valueEntity=null;
		ArrayList<String> inserted;//remembers the entity already inserted
		//For each realtion we create the nodes, if not present, and link it
		for (String rel : graph.keySet()){
			//now we iterate on graph entities, creating nodes and relations
			for (GraphEntity key : graph.get(rel).keySet()){
				//the creation is contained in a transaction
				try ( Transaction tx = graphDb.beginTx() )
				{
					keyEntity = graphDb.createNode(EntityLabel.ENTITY);
					keyEntity.setProperty("EntityID",key.getId());
					setAllAttributes(keyEntity, key.getAttr());
					tx.success();
				}

				//now for each key, we link that to all his value
				for (GraphEntity value: graph.get(rel).get(key)){
					try ( Transaction tx = graphDb.beginTx() )
					{
						valueEntity = graphDb.createNode(EntityLabel.ENTITY);
						valueEntity.setProperty("EntityID",value.getId());
						setAllAttributes(valueEntity, value.getAttr());
						//Find the relationship and use it
						for (Rels r:Rels.values()){
							if (rel.equalsIgnoreCase(r.name()))
								reltype = r;
						}
						if (keyEntity!=null && valueEntity!=null)
							relationship = keyEntity.createRelationshipTo(valueEntity,reltype);
						tx.success();
						//System.out.println("Seconda trans eseguita con successo");
					}
				}
			}
		}
		graphDb.shutdown();
	}
	
	//This method creates a field for each attribute in the string given in Input
	//Creating separated fileds makes Cypher queries easier and with more possibilities 
	private void setAllAttributes(Node entity, String attribs ){
		//First we parse the attribs string and create an array
		String[] tokens;
		if (attribs.split(";").length>1)
			tokens = attribs.split(";");
		else tokens = new String[] {attribs};
		//Now we create a transaction and set the properties for each node
			for (String a:tokens)
				if (a.split(":")[0]!=null && a.split(":")[1]!=null)
					entity.setProperty(a.split(":")[0],a.split(":")[1]);
	}
}