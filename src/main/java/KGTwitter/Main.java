package KGTwitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.ParseException;

import databaseWriter.NodeCreator2Neo;
import jsonManager.ConfigParser;
import jsonManagerModels.GraphEntity;

public class Main {
	public static void main(String[] args) throws ParseException{
		//1148580931,728283781 --> prova2utenti.json

		System.out.println("OPERAZIONI ALGEBRICHE TRA KNOWLEDGE GRAPH\n");
		Scanner terminalInput = new Scanner(System.in);
		System.out.println("Inserisci ID degli utenti separti dalla virgola");
		String utenti = terminalInput.nextLine();
		System.out.println("Inserisci l'operazione da eseguire(Intersection, Difference, Union, No Operation)");
		String operation = terminalInput.nextLine();

		NodeCreator2Neo writer = new NodeCreator2Neo();		
		ConfigParser cf = new ConfigParser();
		try {
			cf.readFields();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int numbersUsers = StringUtils.countMatches(utenti, ",") + 1;
		String[] users = utenti.split(",");
		Map<String, LinkedHashMap<GraphEntity, ArrayList<GraphEntity>>> result = null;

		//OPERATIONS
		if (operation.equals("Intersection")){
			Intersection intersection = new Intersection();
			result = intersection.computeIntesection(cf.getRelations(), users, numbersUsers);
			System.out.print("\nINTERSECTION:");		
			writer.map2graph(result);
			System.out.print("\nOperation terminated\n");

		}

		if (operation.equals("Difference")){
			Difference difference = new Difference();
			result = difference.computeDifference(cf.getRelations(), users, numbersUsers);
			System.out.print("\nDIFFERENCE");
			writer.map2graph(result);
			System.out.print("\nOperation terminated\n");

		}

		if (operation.equals("Union")){
			Union union = new Union();
			result = union.computeUnione(cf.getRelations(), users, numbersUsers);
			System.out.print("\nUNION:");
			writer.map2graph(result);
			System.out.print("\nOperation terminated\n");

		}
		//56350fc892cffb17e4c2841d

		if (operation.equals("no")){
			result = cf.getRelations();
			System.out.println("\nNo Operation:");
			writer.map2graph(result);
		}

		for (String relation : result.keySet()){
			System.out.println(relation);
			for (Entry<GraphEntity, ArrayList<GraphEntity>> entry : result.get(relation).entrySet()) {
				System.out.print(entry.getKey().getId()+","+entry.getKey().getAttr()+" ");
				for (GraphEntity v : entry.getValue()){
					System.out.print(v.getId()+","+v.getAttr()+" ");
				}
				System.out.println();
			}
		}
	}
}


//REL:INTERESTED_TO KEY:user,ATTR:None VALUE:info.interests.all.*,ATTR:display
//REL:IS_CHILD KEY:info.interests.all.*,ATTR:parents VALUE:info.valid,ATTR:None
