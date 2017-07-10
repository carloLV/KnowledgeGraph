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
		//String [] input = new String[]{"42080693,29337915","No Operation"}; //,47948672
		System.out.println("OPERAZIONI ALGEBRICHE TRA KNOWLEDGE GRAPH\n");
		Scanner terminalInput = new Scanner(System.in);
		System.out.println("Inserisci ID degli utenti separti dalla virgola");
		String utenti = terminalInput.nextLine();
		System.out.println("Inserisci l'operazione da eseguire");
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
		//se si passa "all" l'intersezione restituisce id_utente = "all", attributi_utente = "nothing", interessi (dati dall'intersezione degli interessi degli utenti presenti nel file)
		//se si passano due o più utenti l'intersezione restituisce id_utente = "concatenazione degli id degli utenti", attributi_utente = "nothing", interessi che sono un'intersezione degli interessi degli utenti passati
		//id_utente,attributi_utente sono coppie di stringhe e interessi è un array list delle coppie di stringhe
		if (operation.equals("Intersection")){
			Intersection intersection = new Intersection();
			result = intersection.computeIntesection(cf.getRelations(), users, numbersUsers);
			System.out.println("\nINTERSECTION:");//cioè se un interesse ha id = 123 ed è presente per 10 utenti diversi...l'arraylist sarà lungo 10 e tutti questi 10 elementi(stringhe) saranno uguali			
			writer.map2graph(result);
		}
		//se si passa un solo utente la differenza restituisce id_utente, attributi utente, differenza di interessi (dell'utente con tutti gli interessi di altri utenti presenti nel file)
		//se si passano due o più utenti la differenza restituisce id_utente(del primo utente passato), attributi_utente(del primo utente passato), interessi che sono la differenza degli interessi dell'primo utente passato con gli interessi degli altri utenti passati
		if (operation.equals("Difference")){
			Difference difference = new Difference();
			result = difference.computeDifference(cf.getRelations(), users, numbersUsers);
			System.out.println("\nDIFFERENCE");
			writer.map2graph(result);
		}
		
		if (operation.equals("Union")){
			Union union = new Union();
			result = union.computeUnione(cf.getRelations(), users, numbersUsers);
			System.out.println("\nUNION:");
			writer.map2graph(result);
		}
		
		if (operation.equals("No Operation")){
			result = cf.getRelations();
			System.out.println("\nNO Operation:");
			writer.map2graph(result);
		}
		
		//serve per stampare il risultato delle operazioni
		for (String relation : result.keySet()){
			System.out.println(relation);
			for (Entry<GraphEntity, ArrayList<GraphEntity>> entry : result.get(relation).entrySet()) {
				System.out.println(entry.getKey().getId()+","+entry.getKey().getAttr());
				for (GraphEntity v : entry.getValue()){
					System.out.println(v.getId()+","+v.getAttr());
				}
			}
		}
	}
}


//REL:INTERESTED_TO KEY:user,ATTR:None VALUE:info.interests.all.*,ATTR:display
//REL:IS_CHILD KEY:info.interests.all.*,ATTR:parents VALUE:info.valid,ATTR:None
