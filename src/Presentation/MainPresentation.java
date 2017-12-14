package Presentation;

import java.util.InputMismatchException;
import java.util.Scanner;

/***********************************************************************************
 * Class Name: MainPresentation
 * Class Description: Class encharged of presenting the main interface to the user
 * @author Beka Bekeri -, Alvaro Guerrero del Pozo, Fernando Vallejo Banegas
 * Release Date: 29-11-2017
 * @version 5.1
 *********************************************************************************/
public class MainPresentation {

	private static Scanner read = new Scanner (System.in);
	
	/*****************************************************************************************
	 * Method name: main
	 * Method description: method in charged of representing the first interface to the user
	 * @param args
	 ****************************************************************************************/
	public static void main(String[] args){
		int option=0;
		
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
				option1Chosen();
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
	
	private static void option1Chosen(){
		
		char saveornot = 0;
		String oldfile, newfile;
		String strategy = null;
		boolean optimize;
		int depth=0;
		
		oldfile=ReadWriteFrame.getFileName();
		depth=readDepth();
		strategy = readStrategy();
		optimize=readOptimize();
		
		if (ReadWriteFrame.readFile(oldfile, depth, strategy, optimize)) {
			System.out.println("Do you wish to save the result in a file?(y/n): ");
			saveornot=read.next().charAt(0);
			if (Character.toString(saveornot).equalsIgnoreCase("y")) {
				newfile=ReadWriteFrame.getFileName();
				ReadWriteFrame.writeFile(newfile);
			}
			
		}
	}
	
	private static int readDepth() {
		int depth=-1;
		while (depth<=0) {
			System.out.println("Introduce the maxium depth: ");
			try {
				depth = read.nextInt();
				read.nextLine();
			}catch (Exception e) {
				System.out.println("Introduce an integer number greater than 0. Don't introduce a number too big either, or the program may not find any solution.");
			}
		}
		return depth;
	}
	
	private static String readStrategy() {
		String strategy=null;
		int option= -1;
		
		while (option<1 || option>6) {
			System.out.println("Choose an strategy. Note that all of them are limited by previous maximun depth introduced: ");
			System.out.println("1-BFS (Breath-first search).");
			System.out.println("2-DFS (Depth-first search).");
			System.out.println("3-IDS (Iterative deepening search).");
			System.out.println("4-UCS (Uniform cost search).");
			System.out.println("5-A*");
			System.out.println("6-Variant A*");
			try{
				option=read.nextInt();
				read.nextLine();
			}catch(InputMismatchException ime){
				System.out.println("Introduce an integer number, please.");
			}
			switch (option){
			case 1:
				strategy = "BFS";
				break;
			case 2:
				strategy = "DFS";
				break;
			case 3:
				strategy = "IDS";
				break;
			case 4:
				strategy = "UCS";
				break;
			case 5:
				strategy = "A*";
				break;
			case 6:
				strategy = "Variant A*";
				break;
			default:
				System.out.println("Please introduce 1,2,3, 4, 5 or 6.\n");
				option=-1;
				read.nextLine();
			}
		}
		
		return strategy;
	}
	
	private static boolean readOptimize() {
		char optimize;
		System.out.println("Do you wish to use the optimized version (Prunning)?(y/n): ");
		optimize=read.next().charAt(0);
		if (Character.toString(optimize).equalsIgnoreCase("y")) {
			return true;
		}else {
			return false;
		}
	}
	
}
