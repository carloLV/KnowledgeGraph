package DatabaseTest;

import java.io.File;
import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class Neo4jTest {
	
	private static final String n4jPwd="neo";
	private static final String n4jUsr="neo4j";
	
	private static final String jsonPath="file:///home/bum-bum/Desktop/provaUtenti.json";
	private static final String query="CALL apoc.load.json({c_query}) YIELD value"
			+ " UNWIND value.interest AS in"
			+ " RETURN in.interestID";
	private static final String query2 = "MATCH (i:Interest) WHERE i.interestID = {name} " +
	"RETURN i.interestID";
			//+ " CREATE (i:Interest {id: interest.interestID})";
			//+ " SET i.interesse = interest.interesse";
	/*//Path to our db between ""
	public static final File DB_PATH=new File("");

	//Some variables we need
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;

	//Creates the relations
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
	}*/

	public static void main(String[] args){

		Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( n4jUsr, n4jPwd ) );
		Session session = driver.session();

		/*session.run( query, parameters("c_query",jsonPath) );*/

		StatementResult result = session.run( query ,parameters( "c_query",jsonPath ) );
		while ( result.hasNext() )
		{
			Record record = result.next();
			System.out.println( record.get( "interestID" ).asString() + " " + record.get( "interesse" ).asString() );
		}

		session.close();
		driver.close();

	}

}
