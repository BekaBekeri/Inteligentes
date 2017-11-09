package Domain;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import Exceptions.wrongDataException;

/*************************************************************************************
 * Class Name: Control
 * Class Description:Class that implements the main functionality of the program
 *************************************************************************************/
public class Control {

	private static String[] finalactions;
	private static Node[] orderedStates;
	private static PriorityQueue<Node> frontier;
	private static Node finalNode;
	private static State initialState;
	private static byte mean;
	private static byte max;
	private static int maxDepth=-1;
	private static int currentDepth=0;
	private static String strategyToUse = null;
	
	/***************************************************************************************************************
	 * Method name: mainFunctionality
	 * Method Description: method whose responsibility is performing the main requirements of the program, calling other methods
	 * in the process.
	 * @param infoarray: an array containing information about the problem
	 * @param initialField: an array containing the information about the sand in the field
	 * @return 
	 **************************************************************************************************************/
	public static boolean mainFunctionality(byte[] infoarray, byte[][]initialField){
		
		if (strategyToUse.equals("IDS")) {
			currentDepth=0;
		}else {
			currentDepth=maxDepth;
		}
		
		frontier = new PriorityQueue<Node>();
		
		initialState = new State(initialField, infoarray[0], infoarray[1]);
		Node initialNode = new Node(null, initialState, null, 0, 0, strategyToUse);
		
		mean = infoarray[2];
		max = infoarray[3];
		
		boolean solutionFound=false;
		
		List<Action> actionList = null;
		System.out.println("Execution started. Please wait while our program looks for a solution.");
		
		do {
			
			frontier.clear();
			frontier.add(initialNode);
			
			while(!frontier.isEmpty() && !isGoal(frontier.peek().getState())){
				
				actionList=generateActions(frontier.peek());
				succesor(actionList, frontier.poll());
			}
			
			currentDepth++;
			
		}while(currentDepth<=maxDepth && frontier.isEmpty());
		
		if(frontier.isEmpty()){
			System.out.println("No solution found, using strategy: "+strategyToUse+ " , with Maximun Depth: "+maxDepth+".");
			System.out.println();
		}else{
			if (isGoal(frontier.peek().getState())){
				System.out.println("Solution found");
				System.out.println();
				finalNode = frontier.poll();
				createSolutionActions();
				solutionFound = true;
				System.out.println();
			}
		}
		
		return solutionFound;
		
	}
	
	
	/******************************************************************************************************
	 * Method name: generateActions
	 * Method description: method encharged of generating the possible actions given certain conditions
	 * @param state: the state that determines what actions can and cannot be taken
	 * @return
	 ****************************************************************************************************/
	public static List<Action> generateActions(Node currentNode){
		List<Action> actionList = new ArrayList<Action>();	
		List<Movement> movementList = generateMovements(currentNode);
		State currentState = currentNode.getState();
	    byte[] auxA = new byte[4];
	   
	    for(byte n = 0; n <= currentState.getPosition(currentState.getTractorX(), currentState.getTractorY()) - mean; n ++){
	    	
	    	for(byte e = 0; e <= currentState.getPosition(currentState.getTractorX(), currentState.getTractorY()) - mean; e++){
	    	
	    		for(byte s = 0; s <= currentState.getPosition(currentState.getTractorX(), currentState.getTractorY()) - mean; s++){
	    		
	    			for(byte w = 0; w <= currentState.getPosition(currentState.getTractorX(), currentState.getTractorY()) - mean; w++){
	    			 
	    				auxA[0] = n;		//NORTH MOVED SAND
	    				auxA[1] = e;		//EAST MOVED SAND
	    				auxA[2] = s;		//SOUTH MOVED SAND
	    				auxA[3] = w;		//WEST MOVED SAND
	    				
	    				if(n+e+s+w == currentState.getPosition(currentState.getTractorX(), currentState.getTractorY()) - mean){
	    	    			
	    					if( !((n > 0) && (currentState.getTractorX() == 0)) &&
		    					!((e > 0) && (currentState.getTractorY() == currentState.getField()[0].length - 1)) &&
		    					!((s > 0) && (currentState.getTractorX() == currentState.getField().length - 1)) &&
		    					!((w > 0) && (currentState.getTractorY() == 0))){
	    							
		    					if(isPossibleSand(auxA, currentState)) {
		    						for( int mov = 0; mov < movementList.size(); mov++){
			    						Movement move = new Movement(movementList.get(mov).getNewX(),
			    													 movementList.get(mov).getNewY());
			    						Action auxAction = new Action(move, n, e, s, w);
			    						actionList.add(auxAction);
		    						}
		    					}		    								
	    					}		
		    			}	
	    			}	
	    		}
	  		}
	    } 
	    
	    if (actionList.isEmpty()) {
	    	for( int mov = 0; mov < movementList.size(); mov++){
				Movement move = new Movement(movementList.get(mov).getNewX(), movementList.get(mov).getNewY());
				Action auxAction = new Action(move,(byte) 0,(byte) 0,(byte) 0,(byte) 0);
				actionList.add(auxAction);
			}
	    }
	    
		return actionList;
	}
	
	/*******************************************************************************************************
	 * Method name: generateMovements
	 * Method description: method encharged of creating the possible movements of the truck
	 * @param state: an object of the class State that contains the information of the current state
	 * @return
	 *******************************************************************************************************/
	private static List<Movement> generateMovements(Node node) {
		
		List<Movement> movementList = new ArrayList<Movement>();
		State currentState = node.getState();
		
		if (currentState.getTractorX()-1 >=0 ) {
			Movement north = new Movement((byte) (currentState.getTractorX()-1), currentState.getTractorY());
			movementList.add(north);
		}
		if (!(currentState.getTractorY()+1 >= currentState.getField().length)) {
			Movement east = new Movement(currentState.getTractorX(),(byte) (currentState.getTractorY()+1));
			movementList.add(east);
		}
		if (!(currentState.getTractorX()+1 >= currentState.getField().length)){
			Movement south = new Movement((byte) (currentState.getTractorX()+1), currentState.getTractorY());
			movementList.add(south);
		}
		if (currentState.getTractorY()-1 >=0) {
			Movement west = new Movement(currentState.getTractorX(), (byte) (currentState.getTractorY()-1));
			movementList.add(west);
		}
		return movementList;
	}

	/*************************************************************************************************
	 * Method name: applyAction
	 * Method description: method encharged of applying a given action to an initial state
	 * @param state: initial state to which the action is going to be applied
	 * @param action: action that is going to be performed
	 * @return: the new state after the changes done by action
	 *************************************************************************************************/
	public static Node applyAction(Node fatherNode, Action action){
		

		int depth = fatherNode.getDepth()+1;
		int cost = fatherNode.getCost() + action.getSandN() + action.getSandE() + action.getSandS() + action.getSandW() + 1;
		
		State fatherState = fatherNode.getState();
		State currentState = new State (fatherState.copyField(), fatherState.getTractorX(), fatherState.getTractorY());
		Node currentNode = new Node(fatherNode, currentState, action, cost, depth, strategyToUse);
		
		byte centric = fatherState.getPosition(fatherState.getTractorX(), fatherState.getTractorY());		
		centric = (byte) (centric - action.getSandN() - action.getSandE() - action.getSandS() - action.getSandW());
		currentState.setPosition(fatherState.getTractorX(), fatherState.getTractorY(), centric);
		
		if((action.getSandN()>0) && (fatherState.getTractorX() - 1 >= 0)){
			byte northValue = fatherState.getPosition((byte) (fatherState.getTractorX() - 1), fatherState.getTractorY());
			if ((northValue + action.getSandN())<= max) {
				northValue = (byte) (northValue + action.getSandN());
				currentState.setPosition((byte) (fatherState.getTractorX() -1), fatherState.getTractorY(), northValue);
			}
		}
		
		if((action.getSandE()>0) && (fatherState.getTractorY() + 1 < fatherState.getField()[0].length)){
			byte eastValue = fatherState.getPosition(fatherState.getTractorX(), (byte) (fatherState.getTractorY() + 1));
			if ((eastValue + action.getSandE())<= max) {
				eastValue = (byte) (eastValue + action.getSandE());
				currentState.setPosition(fatherState.getTractorX(),(byte) (fatherState.getTractorY() + 1), eastValue);
			}
		}
		
		if((action.getSandS()>0) && fatherState.getTractorX() + 1 < fatherState.getField().length){
			byte southValue = fatherState.getPosition((byte) (fatherState.getTractorX() + 1), fatherState.getTractorY());
			if ((southValue + action.getSandS()) <= max) {
				southValue = (byte) (southValue + action.getSandS());
				currentState.setPosition((byte) (fatherState.getTractorX() +1), fatherState.getTractorY(), southValue);
			}
		}
		
		if((action.getSandW()>0) && (fatherState.getTractorY() - 1 >= 0)){
			byte westValue = fatherState.getPosition(fatherState.getTractorX() , (byte) (fatherState.getTractorY() - 1));
			if ((westValue + action.getSandW()) <= max) {
				westValue = (byte) (westValue + action.getSandW());
				currentState.setPosition(fatherState.getTractorX() , (byte) (fatherState.getTractorY() - 1), westValue);
			}
			
		}
		
		return currentNode;
	}
	
	/*******************************************************************************************************
	 * Method name: isPossibleSand
	 * Method description: method encharged of determining if moving a given amount of sand is possible
	 * @param sandToMove: array containing the amount of sand that is going to be moved to each direction
	 * @param current: current state of the field
	 * @return: a boolean, true if is possible to move sand, false in other case
	 ******************************************************************************************************/
	private static boolean isPossibleSand(byte[] sandToMove, State currentState) {
		boolean check=true;
			if (sandToMove[0]>0) {
				if (currentState.getPosition((byte) (currentState.getTractorX()-1), currentState.getTractorY())+sandToMove[0]>max){
					check=false;
				}
			}
			if (sandToMove[1]>0) {
				if (currentState.getPosition(currentState.getTractorX(),(byte) (currentState.getTractorY()+1))+sandToMove[1]>max){
					check=false;
				}
			}	
			if (sandToMove[2]>0) {
				if (currentState.getPosition((byte) (currentState.getTractorX()+1), currentState.getTractorY())+sandToMove[2]>max){
					check=false;
				}
			}
			if (sandToMove[3]>0) {
				if (currentState.getPosition(currentState.getTractorX(), (byte) (currentState.getTractorY()-1))+sandToMove[3]>max){
					check=false;
				}
			}
			
		
		return check;
	}
	
	/*******************************************************************************************************
	 * Method name: read
	 * Method description: Method encharged of calling the Broker of persistence in order to read a file
	 * @param filename: name of the file to be read
	 * @return 
	 * @throws FileNotFoundException: on problems finding the file
	 * @throws wrongDataException: on controlled errors
	 ********************************************************************************************************/
	public static boolean read(String filename, int depth, String strategy) throws FileNotFoundException, wrongDataException, InputMismatchException {
		boolean resultAchieved;
		byte[] infoarray = new byte[6];
		byte[][] initialField = Persistence.Broker.readFile(filename, infoarray);
		maxDepth = depth;
		strategyToUse = strategy;
		resultAchieved = mainFunctionality(infoarray, initialField);
		return resultAchieved;
	}
	
	/********************************************************************************************************
	 * Method name:write
	 * Method description: method encharged of calling the Broker of persistence, in order to write to a file
	 * @param filename: the name of the file that information is going to be writen to
	 * @throws IOException: on unknown error while trying to write
	 ********************************************************************************************************/
	 public static void write(String filename) throws IOException {
		byte[] infoarray = new byte[6];
		byte[] newPosition = new byte[2];
		State finalState = finalNode.getState();
		
		infoarray[0] = initialState.getTractorX();
		infoarray[1] = initialState.getTractorY();
		infoarray[2] = mean;
		infoarray[3] = max;
		infoarray[4] = (byte) initialState.getField().length;
		infoarray[5] = (byte) initialState.getField()[0].length;
		
		newPosition[0]=finalState.getTractorX();
		newPosition[1]=finalState.getTractorY();
		
		Persistence.Broker.writeFile(filename,initialState.getField(), infoarray, newPosition, finalactions, orderedStates, strategyToUse);
	}
	
	/************************************************************************************************************
	 * Method name: isGoal
	 * Method description: This method is used to check if the state is the one that we are looking for
	 * @param state: the state we want to check
	 * @return boolean depending 
	 *************************************************************************************************************/
	public static boolean isGoal(State state){
		boolean aux = true;
	
		for(int i = 0; i < state.getField().length; i++){
			for(int j = 0; j < state.getField()[0].length; j++){
				if(state.getField()[i][j] != mean){
					aux = false;
				}
			}
		}
		
		return aux;
	}


	public static void succesor(List<Action> actionList, Node currentNode){
		for(int i = 0; i < actionList.size(); i++){
			if (currentNode.getDepth() < currentDepth) {
				//Duplicates
				frontier.add(applyAction(currentNode, actionList.get(i)));
			}
		}	
	}

	public static void createSolutionActions() {
		
		int totalNumber=0;
		
		Node fatherNode = finalNode.getFather();
		Node currentNode = finalNode;
		int finalCost = finalNode.getCost();
		int finalDepth = finalNode.getDepth();
		
		Stack<String> actionStack = new Stack<String>();
		Stack<Node> nodeStack = new Stack<Node>();

		if (finalDepth==0) {
			System.out.println("Initial state already accomplishes objective.");
		}else {
			
			while(currentNode.getFather()!=null) {
				actionStack.push(currentNode.getAppliedAction().toString(fatherNode.getState()));
				nodeStack.push(currentNode);
				fatherNode=fatherNode.getFather();
				currentNode=currentNode.getFather();
			}
			finalactions = new String[actionStack.size()];
			orderedStates = new Node[nodeStack.size()];
			totalNumber= actionStack.size();
			
			System.out.println("List of actions in order to reach the goal state: ");
			System.out.println();
			
			if (!actionStack.isEmpty()) {
				for (int i=0; i<totalNumber; i++) {
					System.out.println(actionStack.peek());
					for (int j=0; j<nodeStack.peek().getState().getField().length; j++) {
						for (int k=0; k<nodeStack.peek().getState().getField()[j].length; k++) {
							System.out.print(nodeStack.peek().getState().getField()[j][k]+" ");
						}
						System.out.print("\n");
					}
					orderedStates[i]=nodeStack.pop();
					finalactions[i]=actionStack.pop();
				}
			}
			System.out.println();
			System.out.println("Total cost to reach solution: "+finalCost+", Depth of the solution: "+finalDepth+".");
		}
		
	}
	
}