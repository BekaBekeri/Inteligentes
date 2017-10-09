package Persistence;
import java.util.*;
import java.io.*;

/************************************************************************************************
 * Class Name: Broker
 * Class Description: Class whose responsibility of reading and writing from/to a file.
 * 
 ************************************************************************************************/
public class Broker {
	
	/********************************************************************************************
	 * Method Name: readFile
	 * Method Description: method whose responsibility is reading the initial state of the problem from a file.
	 * @param filename: name of the file to read
	 * @param infoarray: array that will contain the the rest of the information
	 * @return An array containing the initial state of the field
	 * @throws FileNotFoundException
	 ********************************************************************************************/
	public static int[][] readFile(String filename, int[] infoarray)throws FileNotFoundException{
		int intsplit=0;
		int totalcells=0;
		int totalsand=0;
		int rows=0, columns=0;
		File fieldfile = new File(filename);
		Scanner reader;
		reader=new Scanner(new FileReader(fieldfile));
		String data;
		data = reader.nextLine();
		data = data.replaceAll("\\s+", " ");
		String[] splitted = data.split(" ");
		for (int i=0; i<6; i++) {
			try {
				intsplit = Integer.parseInt(splitted[i]);
			}catch (Exception e){
				//throw wrongFormat exception
			}
			infoarray[i] = intsplit;
		}
		if (checkSense(infoarray)) {
			//throw nonSense exception
		}
		rows = infoarray[5];
		columns = infoarray[4];
		int[][] fieldarray = new int[infoarray[5]][infoarray[4]];
		for (int i=0; i<rows; i ++) {
			data = reader.nextLine();
			data = data.replaceAll("\\s+", " ");
			splitted = data.split(" ");
			if (splitted[0]!= "") {
				//throw wrongFormat exception
			}else {
				for (int j=0; j<columns; j++) {
					try {
						intsplit = Integer.parseInt(splitted[j+1]);
					}catch (Exception e) {
						//throw wrongFormat Exception
					}
					fieldarray [i][j] = intsplit;
					totalsand+=intsplit;
					totalcells++;
				}
			}
		}
		if (totalsand/totalcells!=infoarray[2]) {
			System.out.println("Mean of sand not equal to K. The problem won't have any solution. Please, introduce different information on the file");
			//throw nonSense exception
		}
		reader.close();
		return fieldarray;
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
		int rows=infoarray[5];
		int columns = infoarray[4];
		String data[] = new String[lines];
		for (int i=0; i<infoarray.length; i++) {
			data[0]+=infoarray[i]+" ";
		}
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				data[i+1]+=" "+fieldarray[i][j];
			}
		}
		PrintWriter writer = new PrintWriter(newfilename, "UTF-8");
		for (int i=0; i<data.length;i++){
			writer.println(data[i]);
		}
		writer.close();
	}
	
	/**********************************************************************************************************************************
	 * Method Name: checkSense
	 * Method Description: Method whose responsibility is checking whether the information read from the file has sense or not
	 * @param infoarray: an array containing the information we wish to check
	 * @return: true if information is correct, false in other case
	 **********************************************************************************************************************************/
	private static boolean checkSense(int[] infoarray) {
		if (infoarray[0]>=infoarray[4]) {
			System.out.println("Position x of truck cannot be equal or bigger than number of columns. Introduced: "+infoarray[0]+" and "+infoarray[4]);
			return false;
		}
		if (infoarray[1]>=infoarray[5]) {
			System.out.println("Position y of truck cannot be equal or bigger than number of rows. Introduced: "+infoarray[1]+" and "+infoarray[5]);
			return false;
		}
		if (infoarray[2]>infoarray[3]) {
			System.out.println("Objective amount of sand K cannot be greater than the MAX amount of sand. Introduced: "+infoarray[2]+ " and "+infoarray[3]);
			return false;
		}
		return true;	
	}
	
}