# KnowledgeGraph
**This readme is not yet completed**

This project is composed in 3 parts.

## 1st: Read Knowledge Graph
KG are extracted using some ontologies. The purpose of this part is to automatically read the JSON, output of the ontologies, and create structures suitable for working.

## 2nd: Perform Operations
In this part we operate on our structures and produce as output the result of the operations. 
Supported operations (for now) are: *Intersection*, *Difference*, *Union*.

## 3rd: Write results on Neo4j
The last part is to write all the graphs computed in a suitable DB, so we choose Neo4j.   
