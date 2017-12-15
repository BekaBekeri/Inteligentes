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

	private static PriorityQueue<Node> frontier;
	private static Hashtable<String, Integer> visitedTable;
	private static byte mean;
	private static byte max;
	
	private static int maxDepth;
	private static String strategyToUse = null;
	private static boolean optimization=true;
	private static int currentDepth=0;
	
	private static State initialState;
	private static Node finalNode;
	private static String[] actionsToGoal;
	private static Node[] nodesToGoal;
	private static long executionTime;
	private static int n_nodes;
	
	/***************************************************************************************************************
	 * Method name: mainFunctionality
	 * Method Description: method whose responsibility is performing the main requirements of the program, calling other methods
	 * in the process.
	 * @param infoarray: an array containing information about the problem
	 * @param initialField: an array containing the information about the sand in the field
	 * @return 
	 **************************************************************************************************************/
	public static boolean mainFunctionality(byte[] infoarray, byte[][]initialField){
		
		long startTime;
		boolean solutionFound=false;
		
		frontier = new PriorityQueue<Node>();
		visitedTable = new Hashtable<String, Integer>();
			
		mean = infoarray[2];
		max = infoarray[3];
		n_nodes=1;
		
		if (strategyToUse.equals("IDS")) {
			currentDepth=0;
		}else {
			currentDepth=maxDepth;
		}
		
		initialState = new State(initialField, infoarray[0], infoarray[1]);
		Node initialNode = new Node(null, initialState, null, 0, 0);
		initialNode.updateValues(mean, strategyToUse);
		
		List<Action> actionList = null;
		System.out.println("Execution started. Please wait while our program looks for a solution.");
		startTime = System.nanoTime();
		
		do {
			
			visitedTable.clear();
			frontier.clear();
			frontier.add(initialNode);
			
			while(!frontier.isEmpty() && !isGoal(frontier.peek().getState())){

				visitedTable.put(md5(frontier.peek().getState()) ,frontier.peek().getCost());

				actionList=generateActions(frontier.peek());
				generateSuccessors(actionList, frontier.poll());
				n_nodes++;
				
			}
			
			currentDepth++;
			
		}while(currentDepth<=maxDepth && frontier.isEmpty());
		
		executionTime = System.nanoTime() - startTime;
		
		if(frontier.isEmpty()){
			System.out.println("No solution found, using strategy: "+strategyToUse+ " , with Maximun Depth: "+maxDepth+".");
			System.out.println();
		}else{
			if (isGoal(frontier.peek().getState())){
				System.out.println("Solution found.");
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
	 * @param node: the node that determines what actions can and cannot be taken
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
	    
	    //Exam December 1
	    if (currentNode.getState().getTractorX() == 0 && currentNode.getState().getTractorY()==1) {
	    	System.out.println();
	    	System.out.println("Possible actions at position 0,1:");
	    	for (int i=0; i< actionList.size(); i++) {
	    		System.out.println(actionList.get(i).toString(currentNode.getState()));
	    	}
	    }
	    
		return actionList;
	}
	
	/*******************************************************************************************************
	 * Method name: generateMovements
	 * Method description: method encharged of creating the possible movements of the truck
	 * @param node: an object of the class Node that contains the information of the current node
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
	 * Method description: method encharged of applying a given action to a node
	 * @param fatherNode: father node to which the action is going to be applied
	 * @param action: action that is going to be performed
	 * @return: the new node after the changes done by action
	 *************************************************************************************************/
	public static Node applyAction(Node fatherNode, Action action){

		int depth = fatherNode.getDepth()+1;
		int cost = fatherNode.getCost() + action.getSandN() + action.getSandE() + action.getSandS() + action.getSandW() + 1;
		
		State fatherState = fatherNode.getState();
		State currentState = new State (fatherState.copyField(), fatherState.getTractorX(), fatherState.getTractorY());
		Node currentNode = new Node(fatherNode, currentState, action, cost, depth);
		
		byte centric = fatherState.getPosition(fatherState.getTractorX(), fatherState.getTractorY());		
		centric = (byte) (centric - action.getSandN() - action.getSandE() - action.getSandS() - action.getSandW());
		currentState.setPosition(currentState.getTractorX(), currentState.getTractorY(), centric);
		
		if((action.getSandN()>0) && (currentState.getTractorX() - 1 >= 0)){
			byte northValue = currentState.getPosition((byte) (currentState.getTractorX() - 1), currentState.getTractorY());
			if ((northValue + action.getSandN())<= max) {
				northValue = (byte) (northValue + action.getSandN());
				currentState.setPosition((byte) (currentState.getTractorX() -1), currentState.getTractorY(), northValue);
			}
		}
		
		if((action.getSandE()>0) && (currentState.getTractorY() + 1 < currentState.getField()[0].length)){
			byte eastValue = currentState.getPosition(currentState.getTractorX(), (byte) (currentState.getTractorY() + 1));
			if ((eastValue + action.getSandE())<= max) {
				eastValue = (byte) (eastValue + action.getSandE());
				currentState.setPosition(currentState.getTractorX(),(byte) (currentState.getTractorY() + 1), eastValue);
			}
		}
		
		if((action.getSandS()>0) && currentState.getTractorX() + 1 < currentState.getField().length){
			byte southValue = currentState.getPosition((byte) (currentState.getTractorX() + 1), currentState.getTractorY());
			if ((southValue + action.getSandS()) <= max) {
				southValue = (byte) (southValue + action.getSandS());
				currentState.setPosition((byte) (currentState.getTractorX() +1), currentState.getTractorY(), southValue);
			}
		}
		
		if((action.getSandW()>0) && (currentState.getTractorY() - 1 >= 0)){
			byte westValue = currentState.getPosition(currentState.getTractorX() , (byte) (currentState.getTractorY() - 1));
			if ((westValue + action.getSandW()) <= max) {
				westValue = (byte) (westValue + action.getSandW());
				currentState.setPosition(currentState.getTractorX() , (byte) (currentState.getTractorY() - 1), westValue);
			}
			
		}
		
		currentState.setTractorX(action.getNewMove().getNewX());
		currentState.setTractorY(action.getNewMove().getNewY());
		
		currentNode.updateValues(mean, strategyToUse);
		
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
	
	/************************************************************************************************************
	 * Method name: isGoal
	 * Method description: This method is used to check if the state is the one that we are looking for
	 * @param state: the state we want to check
	 * @return boolean 
	 *************************************************************************************************************/
	public static boolean isGoal(State state){
		for(int i = 0; i < state.getField().length; i++){
			for(int j = 0; j < state.getField()[0].length; j++){
				if(state.getField()[i][j] != mean){
					return false;
				}
			}
		}
		
		return true;
	}

	/*************************************************************************************************************
	 * Method name: successor
	 * Method description: generate all the successors of a given node and add them to the frontier
	 * @param actionList: the list of the actions to be applied
	 * @param currentNode: the node where we apply the actions
	 *************************************************************************************************************/
	public static void generateSuccessors(List<Action> actionList, Node currentNode){
		
		for(int i = 0; i < actionList.size(); i++){
			if (currentNode.getDepth() < currentDepth) {
				Node newNode = applyAction(currentNode, actionList.get(i));
				if (optimization) {
					if (checkVisited(newNode)) {
						frontier.add(newNode);
					}
				}else {
					frontier.add(newNode);
				}
			}
		}	
	}

	/*************************************************************************************************************
	 * Method name: createSolutionActions
	 * Method description: Sort the final solution and print it on the screen
	 *************************************************************************************************************/
	public static void createSolutionActions() {
		
		int totalNumber=0;
		
		Node fatherNode = finalNode.getFather();
		Node currentNode = finalNode;
		
		Stack<String> actionStack = new Stack<String>();
		Stack<Node> nodeStack = new Stack<Node>();
		
		if (currentNode.getDepth()==0) {
			System.out.println("Initial state already accomplishes objective.");
		}else {
			
			while(currentNode.getFather()!=null) {
				actionStack.push(currentNode.getAppliedAction().toString(fatherNode.getState()));
				nodeStack.push(currentNode);
				
				fatherNode=fatherNode.getFather();
				currentNode=currentNode.getFather();
			}
			actionsToGoal = new String[actionStack.size()];
			nodesToGoal = new Node[nodeStack.size()];
			totalNumber= actionStack.size();
			
			System.out.println("**************************************************************");
			System.out.println("List of actions and states in order to reach the goal state: ");
			System.out.println();
			
			if (!actionStack.isEmpty()) {
				for (int i=0; i<totalNumber; i++) {
					
					
					
					System.out.println(actionStack.peek());
					
					for (byte j=0; j<initialState.getField().length; j++) {
						for (byte h=0; h<initialState.getField()[j].length; h++) {
							System.out.print(nodeStack.peek().getState().getPosition(j, h)+ " ");
						}
						System.out.println();
					}
					System.out.println("Value: "+nodeStack.peek().getValue()+", Cost: "+nodeStack.peek().getCost()+", Depth: "+nodeStack.peek().getDepth()+".\n");
					actionsToGoal[i]=actionStack.pop();
					nodesToGoal[i] = nodeStack.pop();
				}
			}
			System.out.println("*******Execution Statistics*******");
			System.out.println("Total cost to reach solution: "+finalNode.getCost()+", Depth of the solution: "+finalNode.getDepth()+".");
			System.out.println("Number of visited nodes: "+n_nodes+", Execution Time: "+executionTime+" ns ("+executionTime/1000000000.0+" s).");
		}
		
	}
	
	/*************************************************************************************************************
	 * Method name: checkVisited
	 * Method description: Checks if that state is already in the visited states table and if it has been visited
	 * checks if the value associated is bigger, in that case, overwrites the existing value by the new one
	 *************************************************************************************************************/
	private static boolean checkVisited(Node currentNode) {						
		if(visitedTable.get(md5(currentNode.getState())) !=  null){
			if(currentNode.getCost() < visitedTable.get(md5(currentNode.getState()))){
				visitedTable.put(md5(currentNode.getState()), currentNode.getCost());
				return true;
			}else {
				return false;
			}
		}else{
			return true;
		}
	}
	
	/*******************************************************************************************************
	 * Method name: read
	 * Method description: Method in charged of of calling the Broker of persistence in order to read a file
	 * @param filename: name of the file to be read
	 * @param depth: the maximum depth
	 * @param strategy: the name of the strategy to be applied 
	 * @return 
	 * @throws FileNotFoundException: on problems finding the file
	 * @throws wrongDataException: on controlled errors
	 ********************************************************************************************************/
	public static boolean read(String filename, int depth, String strategy, boolean optimize) throws FileNotFoundException, wrongDataException, InputMismatchException {
		boolean resultAchieved;
		optimization = optimize;
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
		
		Persistence.Broker.writeFile(filename,initialState.getField(), infoarray, newPosition, actionsToGoal, nodesToGoal, strategyToUse, n_nodes, executionTime);
	}
	 
	 /*************************************************************************************************************
	 * Method name: md5
	 * Method description: This method is made to create a numerical representation of a state that can be used 
	 * as an index in the hash table.
	 *************************************************************************************************************/
	public static String md5(State state){
		byte[][] field = state.copyField();
		String aux="";
		for (byte i=0; i<field.length; i++) {
			for (byte j=0; j<field[0].length; j++) {
				aux += state.getPosition(i, j);
			}
		}
		aux += state.getTractorX();
		aux += state.getTractorY();
		return aux;
	}
	
}