package databaseWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import jsonManagerModels.GraphEntity;

public class NodeCreator2Neo {
	
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
	@SuppressWarnings("deprecation")
	public void map2graph(Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> graph){
		Node keyEntity;
		Node valueEntity;
		//For each realtion we create the nodes, if not present, and link it
		for (String rel : graph.keySet()){
			//now we iterate on graph entities, creating nodes and relations
			for (GraphEntity key : graph.get(rel).keySet()){
				//the creation is contained in a transaction
				try ( Transaction tx = graphDb.beginTx() )
				{
					keyEntity = graphDb.createNode();
					keyEntity.setProperty("EntityID",key.getId());
					keyEntity.setProperty("Attributes",key.getAttr());
				    tx.success();
				    System.out.println("Prima trans eseguita con successo");
				}
				
				//now for each key, we link that to all his value
				for (GraphEntity value: graph.get(rel).get(key)){
					try ( Transaction tx = graphDb.beginTx() )
					{
						valueEntity = graphDb.createNode();
						valueEntity.setProperty("EntityID",value.getId());
						valueEntity.setProperty("Attributes",value.getAttr());
						reltype = DynamicRelationshipType.withName(rel);
						relationship = keyEntity.createRelationshipTo(valueEntity,reltype);
					    tx.success();
					    System.out.println("Seconda trans eseguita con successo");
					}
				}
			}
		}
		graphDb.shutdown();
	}
}
