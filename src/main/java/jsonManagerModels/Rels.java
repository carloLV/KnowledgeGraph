package jsonManagerModels;

import org.neo4j.graphdb.RelationshipType;

/**************************************************************************************************/
/*** In newer version of Neo4J, the use of Dynamic Relationship is deprecated,                  ***/
/*** so we create this enum. The user should place here his relationship; same that he used     ***/
/*** in the configuration file. 														        ***/
/**************************************************************************************************/

public enum Rels implements RelationshipType {
	INTERESTED_TO,
	IS_CHILD
	//More relationships goes here, separated by comma
}
