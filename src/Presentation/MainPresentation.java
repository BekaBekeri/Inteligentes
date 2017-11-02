package Presentation;

import java.util.InputMismatchException;
import java.util.Scanner;

/***********************************************************************************
 * Class Name: MainPresentation
 * Class Description: Class encharged of presenting the main interface to the user
 * @author Beka Bekeri -, Alvaro Guerrero del Pozo, Fernando Vallejo Banegas
 * Release Date: 2-11-2017
 * @version 3.0
 *********************************************************************************/
public class MainPresentation {

	private static Scanner read = new Scanner (System.in);
	
	/*****************************************************************************************
	 * Method name: main
	 * Method description: method encharged of representing the first interface to the user
	 * @param args
	 ****************************************************************************************/
	public static void main(String[] args) {
		int option=0;
		char saveornot = 0;
		String oldfile, newfile;
		while(true){
			System.out.println("Please, choose an option: ");
			System.out.println("1-Read the data from a file.");
			System.out.println("2-Exit.");
			try{
				option=read.nextInt();
				read.nextLine();
			}catch(InputMismatchException ime){
				System.out.println("Introduce an integer number, please.");
			}
			switch (option){
			case 1:
				oldfile=ReadWriteFrame.getFileName();
				if (ReadWriteFrame.readFile(oldfile)) {
					System.out.println("Do you wish to save the result in a file?(y/n): ");
					saveornot=read.next().charAt(0);
					if (Character.toString(saveornot).equalsIgnoreCase("y")) {
						newfile=ReadWriteFrame.getFileName();
						ReadWriteFrame.writeFile(newfile);
					}
					
				}
				option=-1;
				break;
			case 2:
				System.exit(0);
				break;
			default:
				System.out.println("Please introduce 1 or 2 .\n");
				option=-1;
				read.nextLine();
			}	
		}
	}
}
