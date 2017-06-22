package resultJSONparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;

public class FinalJSONParser {
	private static String inputFile="/home/bum-bum/Desktop/provaUtenti.json";
	private Scanner scanner; 
	public FinalJSONParser(){
		try {
			this.scanner = new Scanner(new File(inputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*	
	scanner.useDelimiter("\n");
	JSONParser parser = new JSONParser();
	while (scanner.hasNext()) {
		String linea = scanner.next();
	}
*/
}
