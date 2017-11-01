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
	private static PriorityQueue<NodeState> frontier;
	private static NodeState finalstate;
	private static NodeState initialState;
	private static int mean;
	private static int max;
	
	/***************************************************************************************************************
	 * Method name: mainFunctionality
	 * Method Description: method whose responsibility is performing the main requirements of the program, calling other methods
	 * in the process.
	 * @param infoarray: an array containing information about the problem
	 * @param initialField: an array containing the information about the sand in the field
	 * @return 
	 **************************************************************************************************************/
	public static boolean mainFunctionality(int[] infoarray, int[][]initialField){
		
		frontier = new PriorityQueue<NodeState>();
		
		initialState = new NodeState();
		initialState.setTractorX(infoarray[0]);
		initialState.setTractorY(infoarray[1]);
		
		mean = infoarray[2];
		max = infoarray[3];
		
		boolean solutionFound=false;
		
		initialState.setField(initialField);
		
		List<Action> actionList = null;
		frontier.add(initialState);
		
		System.out.println("Execution started. Please wait while our program looks for a solution.");
		
		while(!frontier.isEmpty() && !isGoal(frontier.peek())){
			actionList=generateActions(frontier.peek());
			succesor(actionList, frontier.poll());
		}
		
		if(frontier.isEmpty()){
			System.out.println("No solution found.");
			System.out.println();
		}else{
			if (isGoal(frontier.peek())){
				System.out.println("Solution found");
				System.out.println();
				finalstate = frontier.poll();
				
				NodeState father = finalstate.getFather();
				NodeState currentState = finalstate;
				Stack<String> actionstack = new Stack<String>();

				while(father.getFather()!=null) {
					actionstack.push(currentState.getAppliedAction().toString(father));
					father=father.getFather();
					currentState=currentState.getFather();
				}
				actionstack.push(currentState.getAppliedAction().toString(father));
				
				finalactions = new String[actionstack.size()];
				
				System.out.println("List of actions in order to reach the goal state: ");
				
				for (int i=0; i<actionstack.size(); i++) {
					System.out.println(actionstack.peek());
					finalactions[i]=actionstack.pop();
				}
				solutionFound = true;
				System.out.println();
				System.out.println("And final position of the truck: "+finalstate.getTractorX()+", "+finalstate.getTractorY());
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
	public static List<Action> generateActions(State state){
		List<Action> actionList = new ArrayList<Action>();
		List<Movement> movementList = generateMovements(state);
	    int[] auxA = new int[4];
	   
	    for(int n = 0; n <= state.getPosition(state.getTractorX(), state.getTractorY()) - mean; n ++){
	    	
	    	for(int e = 0; e <= state.getPosition(state.getTractorX(), state.getTractorY()) - mean; e++){
	    	
	    		for(int s = 0; s <= state.getPosition(state.getTractorX(), state.getTractorY()) - mean; s++){
	    		
	    			for( int w = 0; w <= state.getPosition(state.getTractorX(), state.getTractorY()) - mean; w++){
	    			 
	    				auxA[0] = n;		//NORTH MOVED SAND
	    				auxA[1] = e;		//EAST MOVED SAND
	    				auxA[2] = s;		//SOUTH MOVED SAND
	    				auxA[3] = w;		//WEST MOVED SAND
	    				
	    				if(n+e+s+w == state.getPosition(state.getTractorX(), state.getTractorY()) - mean){
	    	    			
	    					if( !((n > 0) && (state.getTractorX() == 0)) &&
		    					!((e > 0) && (state.getTractorY() == state.getField()[0].length - 1)) &&
		    					!((s > 0) && (state.getTractorX() == state.getField().length - 1)) &&
		    					!((w > 0) && (state.getTractorY() == 0))){
	    							
		    					if(isPossibleSand(auxA, state)) {
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
		return actionList;
	}
	
	/*******************************************************************************************************
	 * Method name: generateMovements
	 * Method description: method encharged of creating the possible movements of the truck
	 * @param state: an object of the class State that contains the information of the current state
	 * @return
	 *******************************************************************************************************/
	private static List<Movement> generateMovements(State state) {
		List<Movement> movementList = new ArrayList<Movement>();
		
		if (state.getTractorX()-1 >=0 ) {
			Movement north = new Movement(state.getTractorX()-1, state.getTractorY());
			movementList.add(north);
		}
		if (!(state.getTractorY()+1 >= state.getField().length)) {
			Movement east = new Movement(state.getTractorX(), state.getTractorY()+1);
			movementList.add(east);
		}
		if (!(state.getTractorX()+1 >= state.getField().length)){
			Movement south = new Movement(state.getTractorX()+1, state.getTractorY());
			movementList.add(south);
		}
		if (state.getTractorY()-1 >=0) {
			Movement west = new Movement(state.getTractorX(), state.getTractorY()-1);
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
	public static NodeState applyAction(NodeState state, Action action){
		
		NodeState newState = new NodeState();
		
		newState.setField(state.getField());
		newState.setTractorX(action.getNewMove().getNewX());
		newState.setTractorY(action.getNewMove().getNewY());
		newState.setFather(state);
		newState.setAppliedAction(action);
		newState.setCost(state.getCost() + action.getSandN() + action.getSandE() + action.getSandS() + action.getSandW() + 1); //+1 cause the movement of the tractor
		int centric = state.getPosition(state.getTractorX(), state.getTractorY());		
		centric = centric - action.getSandN() - action.getSandE() - action.getSandS() - action.getSandW();
		newState.setPosition(state.getTractorX(), state.getTractorY(), centric);
		
		if((action.getSandN()>0) && (state.getTractorX() - 1 >= 0)){
			int northValue = state.getPosition(state.getTractorX() - 1, state.getTractorY());
			if ((northValue + action.getSandN())<= max) {
				northValue = northValue + action.getSandN();
				newState.setPosition(state.getTractorX() -1, state.getTractorY(), northValue);
			}
		}
		
		if((action.getSandE()>0) && (state.getTractorY() + 1 < state.getField()[0].length)){
			int eastValue = state.getPosition(state.getTractorX(), state.getTractorY() + 1);
			if ((eastValue + action.getSandE())<= max) {
				eastValue = eastValue + action.getSandE();
				newState.setPosition(state.getTractorX(), state.getTractorY() + 1, eastValue);
			}
		}
		
		if((action.getSandS()>0) && state.getTractorX() + 1 < state.getField().length){
			int southValue = state.getPosition(state.getTractorX() + 1, state.getTractorY());
			if ((southValue + action.getSandS()) <= max) {
				southValue = southValue + action.getSandS();
				newState.setPosition(state.getTractorX() +1 , state.getTractorY(), southValue);
			}
		}
		
		if((action.getSandW()>0) && (state.getTractorY() - 1 >= 0)){
			int westValue = state.getPosition(state.getTractorX() , state.getTractorY() - 1);
			if ((westValue + action.getSandW()) <= max) {
				westValue = westValue + action.getSandW();
				newState.setPosition(state.getTractorX() , state.getTractorY() - 1, westValue);
			}
			
		}
		
		if(newState.equals(state)) {
			return null;
		}
		
		return newState;
	}
	
	/*******************************************************************************************************
	 * Method name: isPossibleSand
	 * Method description: method encharged of determining if moving a given amount of sand is possible
	 * @param sandToMove: array containing the amount of sand that is going to be moved to each direction
	 * @param current: current state of the field
	 * @return: a boolean, true if is possible to move sand, false in other case
	 ******************************************************************************************************/
	private static boolean isPossibleSand(int[] sandToMove, State current) {
		boolean check=true;
			if (sandToMove[0]>0) {
				if (current.getPosition(current.getTractorX()-1, current.getTractorY())+sandToMove[0]>max){
					check=false;
				}
			}
			if (sandToMove[1]>0) {
				if (current.getPosition(current.getTractorX(), current.getTractorY()+1)+sandToMove[1]>max){
					check=false;
				}
			}	
			if (sandToMove[2]>0) {
				if (current.getPosition(current.getTractorX()+1, current.getTractorY())+sandToMove[2]>max){
					check=false;
				}
			}
			if (sandToMove[3]>0) {
				if (current.getPosition(current.getTractorX(), current.getTractorY()-1)+sandToMove[3]>max){
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
	public static boolean read(String filename) throws FileNotFoundException, wrongDataException {
		boolean resultAchieved;
		int[] infoarray = new int[6];
		int[][] initialField = Persistence.Broker.readFile(filename, infoarray);
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
		int[] infoarray = new int[6];
		int[] newPosition = new int[2];
		
		infoarray[0] = initialState.getTractorX();
		infoarray[1] = initialState.getTractorY();
		infoarray[2] = mean;
		infoarray[3] = max;
		infoarray[4] = initialState.getField().length;
		infoarray[5] = initialState.getField()[0].length;
		
		newPosition[0]=finalstate.getTractorX();
		newPosition[1]=finalstate.getTractorY();
		
		Persistence.Broker.writeFile(filename,initialState.getField(), infoarray, newPosition, finalactions);
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


	public static void succesor(List<Action> actionList, NodeState currentState){
		for(int i = 0; i < actionList.size(); i++){
			frontier.add(applyAction(currentState, actionList.get(i)));
		}
		
	}

}