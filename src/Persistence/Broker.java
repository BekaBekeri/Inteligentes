package Persistence;
import java.util.*;
import java.io.*;

/************************************************************************************************
 * Class Name: Broker
 * Class Description: Class whose responsibility of reading and writing from/to a file.
 * 
 ************************************************************************************************/
public class Broker {
	
	private static String oldfilename;
	
	/**************************************************************************************************************
	 * Method Name: readFile
	 * Method Description: method whose responsibility is reading the initial state of the problem from a file.
	 * @param filename: name of the file to read
	 * @param fieldarray: array that will contain the state of the field after reading it from the file
	 * @param infoarray: array that will contain the the rest of the information
	 * @throws FileNotFoundException
	 *************************************************************************************************************/
	public static void readFile(String filename, int[][] fieldarray,  int[] infoarray)throws FileNotFoundException{
		int lines=0;
		File fieldfile = new File(filename);
		Scanner reader;
		reader=new Scanner(new FileReader(fieldfile));
		while(reader.hasNext()) {    
		  lines++;
		  reader.nextLine();
		}
		reader.close();
		reader=new Scanner(new FileReader(fieldfile));
		String data []= new String[lines];
		data[0] = reader.nextLine();
		data[0] = data[0].replaceAll("\\s+", " ");
		int[] splitted = Arrays.stream(data[0].split(" ")).mapToInt(Integer::parseInt).toArray();
		for (int i=0; i<6; i++) {
			infoarray[i] = splitted[i];
		}
		for (int i=1; i<lines; i ++) {
			data[i] = reader.nextLine();
			data[i] = data[i].replaceAll("\\s+", " ");
			splitted = Arrays.stream(data[i].split(" ")).mapToInt(Integer::parseInt).toArray();
			for (int j=0; j<lines; j++) {
				fieldarray [i-1][j] = splitted[1];
			}
		}
		oldfilename = filename;
		reader.close();
	}
	
	/**************************************************************************************************************
	 * Method Name: writeFile
	 * Method Description: method whose responsibility of writing into a file the final state of the problem
	 * @param newfilename: name of the file in which the information is going to be stored
	 * @param fieldarray: array containing the final state of the field
	 * @param infoarray: array containing the final state of the rest of the information
	 * @throws IOException
	 ***************************************************************************************************************/
	public static void writeFile(String newfilename, int[][] fieldarray, int[] infoarray) throws IOException{
		int lines=0;
		File fieldfile = new File(oldfilename);
		Scanner reader;
		reader=new Scanner(new FileReader(fieldfile));
		while(reader.hasNext()) {    
			  lines++;
			  reader.nextLine();
		}
		reader.close();
		String data[] = new String[lines];
		for (int i=0; i<infoarray.length; i++) {
			data[0]+=infoarray[i]+" ";
		}
		for (int i=0; i<fieldarray.length; i++) {
			for (int j=0; j<fieldarray[i].length; j++) {
				data[i]+=fieldarray[i][j]+" ";
			}
		}
		PrintWriter writer = new PrintWriter(newfilename, "UTF-8");
		for (int i=0; i<data.length;i++){
			writer.println(data[i]);
		}
		writer.close();
	}
	
}