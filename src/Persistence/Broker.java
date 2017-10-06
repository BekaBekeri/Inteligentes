package Persistence;
import java.util.*;
import java.io.*;

/************************************************************************************************
 * Class Name: Broker
 * Class Description: Class whose responsibility of reading and writing from/to a file.
 * 
 ************************************************************************************************/
public class Broker {
	
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
		int intsplit;
		File fieldfile = new File(filename);
		Scanner reader;
		reader=new Scanner(new FileReader(fieldfile));
		while(reader.hasNext()) {    
		  lines++;
		  reader.nextLine();
		}
		reader.close();
		reader=new Scanner(new FileReader(fieldfile));
		String data;
		data = reader.nextLine();
		data = data.replaceAll("\\s+", " ");
		String[] splitted = data.split(" ");
		for (int i=0; i<6; i++) {
			intsplit = Integer.parseInt(splitted[i]);
			infoarray[i] = intsplit;
		}
		for (int i=0; i<lines; i ++) {
			data = reader.nextLine();
			data = data.replaceAll("\\s+", " ");
			splitted = data.split(" ");
			if (splitted[0]!= "") {
				//throw exception. Format: <_a_b_c>
			}else {
				for (int j=0; j<splitted.length; j++) {
					intsplit = Integer.parseInt(splitted[j+1]);
					fieldarray [i][j] = intsplit;
				}
			}
		}
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
		int lines=1+fieldarray.length;
		String data[] = new String[lines];
		for (int i=0; i<infoarray.length; i++) {
			data[0]+=infoarray[i]+" ";
		}
		for (int i=0; i<fieldarray.length; i++) {
			for (int j=0; j<fieldarray[i].length; j++) {
				data[i+1]+=" "+fieldarray[i][j];
			}
		}
		PrintWriter writer = new PrintWriter(newfilename, "UTF-8");
		for (int i=0; i<data.length;i++){
			writer.println(data[i]);
		}
		writer.close();
	}
	
}