package Presentation;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import Exceptions.*;

/******************************************************************************************************************************
 * Class name: ReadWriteFrame
 * Class Description: class encharged of managing the interaction with the user with everything related to read and write files
 *
 *****************************************************************************************************************************/
public class ReadWriteFrame {
	
	private static Scanner read = new Scanner (System.in);
	
	/********************************************************************************************************
	 * Method name: getFileName
	 * Method description: method encharged of asking the user for the name of the file to be read
	 * @return an String containing the name of the file
	 *****************************************************************************************************/
	protected static String getFileName() {
		String filename;
		System.out.println("Introduce the name of the file (don't forget the extension) : ");
		filename=read.nextLine();
		return filename;	
	}
	
	/*********************************************************************************************************
	 * Method name: readFile
	 * Method description: method encharged of calling the read method of Control, in the Domain package
	 * @param filename: name of the file to be parsed
	 * @return: a boolean, true in correct execution, false in other case
	 **********************************************************************************************************/
	protected static boolean readFile(String filename) {
		boolean solutionFound = false;
		try {
			if(Domain.Control.read(filename)) {
				solutionFound=true;
			}else {
				solutionFound=false;
			}
			
		}catch (wrongDataException wde) {
			System.out.println(wde.getMessage());
		}catch (InputMismatchException ime) {
			System.out.println();
		}catch (FileNotFoundException fnf) {
			System.out.println("Error while looking for the file. Please chech that the name is correct.");
		}catch (Exception e) {
			System.out.println("\nUndetermined error. Please, chech that everything is correct.\n");
		}
		return solutionFound;
	}
	
	
	/**
	 * Method name: writeFile
	 * Method description: method encharged of calling Control, in Domain package, to write information to a file
	 * @param filename: the name of the filw to be written
	 * @return: a boolean, true in correct execution, false in other case
	 */
	
	protected static void writeFile(String filename) {
		try{
			Domain.Control.write(filename);
		}catch (Exception e) {
			System.out.println("Error while trying to write in the file.\n");
		}
	}
	
}
