package Persistence;
import java.util.*;

import Domain.NodeState;
import Exceptions.*;

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
	 * @throws FileNotFoundException: when the file required cannot be found
	 * @throws wrongDataException: when the information in the file is incorrect 
	 ********************************************************************************************/
	public static byte[][] readFile(String filename, byte[] infoarray)throws FileNotFoundException, wrongDataException, InputMismatchException{
		byte intsplit=0;
		int max;
		double totalcells=0;
		double totalsand=0;
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
				intsplit = Byte.parseByte(splitted[i]);
			}catch (Exception e){
					throw new wrongDataException(100);
			}
			infoarray[i] = intsplit;
		}
		hasSense(infoarray);
		rows = infoarray[5];
		columns = infoarray[4];
		max = infoarray[3];
		byte[][] fieldarray = new byte[rows][columns];
		for (int i=0; i<rows; i ++) {
			data = reader.nextLine();
			data = data.replaceAll("\\s+", " ");
			splitted = data.split(" ");
			if (!(splitted[0].isEmpty())) {
				throw new wrongDataException(101);
			}else{
				for (int j=0; j<columns; j++) {
					try {
						intsplit = Byte.parseByte(splitted[j+1]);
					}catch (Exception e) {
							throw new wrongDataException(100);
					}
					if (intsplit<0 || intsplit>max) {
						throw new wrongDataException(203);
					}
					fieldarray [i][j] = intsplit;
					totalsand+=intsplit;
					totalcells++;
				}
			}
		}
		if (totalsand/totalcells != (double)infoarray[2]) {
			throw new wrongDataException(204);
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
	 * @param actions: an array containing the possible actions in String format
	 * @throws IOException: on undetermined error while writing.
	 ***************************************************************************************************************/
	public static void writeFile(String newfilename,byte[][] initialfield, byte[] infoarray,byte[] newPosition, String[] actions, NodeState[] allStates, String strategy) throws IOException{
		int rows=infoarray[5];
		int columns = infoarray[4];
		int lines= rows+1;
		String data[] = new String[lines];
		String temporalRow= null;
		for (int i=0; i<infoarray.length; i++) {
			data[0]+=infoarray[i]+" ";
		}
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				data[i+1]+=" "+initialfield[i][j];
			}
		}
		for (int i=0; i<data.length; i++) {
			data[i] = data[i].replaceAll("null", "");
		}
		PrintWriter writer = new PrintWriter(newfilename, "UTF-8");
		for (int i=0; i<data.length;i++){
			writer.println(data[i]);
		}
		writer.println("(Above is our initial State)");
		writer.println();
		
		if (actions!=null) {
			writer.println("****The actions taken to reach the goal state are: ****");
			for (int i=0; i<actions.length; i++) {
				writer.println(actions[i]);
				for (int j=0; j<allStates[i].getField().length; j++) {
					for (int k=0; k<allStates[i].getField()[j].length; k++) {
						temporalRow += " "+allStates[i].getField()[j][k]+" ";
					}
					temporalRow = temporalRow.replaceAll("null", "");
					writer.println(temporalRow);
					temporalRow = null;
				}
			}
			writer.println();
			writer.println("Depth of the solution: "+allStates[allStates.length-1].getDepth()+", with Cost: "+allStates[allStates.length-1].getCost());
			writer.println("Solution found with strategy: "+strategy+".");
		}else {
			writer.println("Initial state already accomplishes solution.");
		}
		
		writer.close();
		System.out.println("File written correctly.\n");
	}
	
	/**********************************************************************************************************************************
	 * Method Name: checkSense
	 * Method Description: Method whose responsibility is checking whether the information read from the file has sense or not
	 * @param infoarray: an array containing the information we wish to check
	 * @return: true if information is correct, false in other case
	 * @throws wrongDataException: when the information in the file is incorrect
	 **********************************************************************************************************************************/
	private static void hasSense(byte[] infoarray) throws wrongDataException {
		if (infoarray[0]>=infoarray[4]) {
			throw new wrongDataException(200);
		}
		if (infoarray[1]>=infoarray[5]) {
			throw new wrongDataException(201);
		}
		if (infoarray[2]>infoarray[3]) {
			throw new wrongDataException(202);
		}	
	}
	
}