# KnowledgeGraph
This project is composed in 3 parts.

## 1st: Read Knowledge Graph
KG are extracted using some ontologies. The purpose of this part is to automatically read the JSON, output of the ontologies, and create structures suitable for working. In order to perform this step, you have to configure some elements:
### Configuration file
Create a configuration file in *txt* format. The file we used is **jsonconfig.txt**:
![alt](https://github.com/carloLV/KnowledgeGraph/blob/master/images/jsonconfig1.png)
![alt](https://github.com/carloLV/KnowledgeGraph/blob/master/images/jsonconfig2.png)
Each line is composed by 3 fields:
1. REL: is the name of relation between nodes.
2. KEY: the ID of the main node, followed by its attributes.
3. VALUE: the ID of the nodes linked with the KEY ID.

**Note**
The idea behind this conf file is that a JSON file is always composed as a set of nested fields, so we created a *configuration logic* based on this points:

**-** Nested fields separated by **.**

**-** In case of a map in a nested level (the case of interests in our file), you have to set this with ** * **.

**-** Attributes are always at least at 1 level of nesting more than the value used as key; multiple attributes are extracted separating them with **-**.

### Configure relations
Put your relations (based on your domain and ontology) on **jsonManagerModels/Rels**.
### Set path
Go to **jsonconfig/ConfigParser** and set your paths for configuration file and for the JSON file returned by the ontology you used. Now you can extract the fields in the way you prefer. 
**Be careful: ** In case of too large dataset, DO NOT use a map for the Key value (the case in the second image).
It is best to extract data in a computational efficient way, then modify the values extracted using a UDF. Follow the example proposed in **userDefinedFunction/UDF**. We used this UDF for the relation *IS_CHILD*.
When you use a UDF the UI will ask you on wich relation it will be applied.

## 2nd: Perform Operations
In this part we operate on our structures and produce as output the result of the operations. 
Supported operations (for now) are: *Intersection*, *Difference*, *Union*. I you want just to visualize the graph extracted in Neo4J, you can write 'no' in the UI. For a better understanding of operations go to https://en.wikipedia.org/wiki/Graph_operations.

## 3rd: Write results on Neo4j
The last part is to write all the graphs computed in a suitable DB, so we choose Neo4j.
Note: DB has to be **Shut Down** when running the operations.
### Versions 
For this project we used Neo4J 3.2.1 and the related *apoc plugin*. We reccomend the binary download.
### Set libraries and path on Eclipse
In Eclipse go to *configure build path* and add all the external jars present in the folder lib of Neo4J.
Then go to **databaseWriter/NodeCreator2Neo** and set path to your **/GraphDB/** folder. If you want to delete all data in the DB just turn off Neo, delete this folder and restart Neo to recreate it empty.
Ensure your DB is off when you run the program, to avoid driver errors.
