package Domain;
import java.util.*;

public class Pruebas {

	
	public static void main(String[] args){
		
		
		int[][] field = {{6, 8, 5,},
						 {6, 8, 2,},
						 {2, 0, 8}};
		
		State initialState = new State(field,
									   0,
									   2,
									   3,
									   8);
		
		List<Action> actionList = generateActions(initialState);
		
		System.out.println("------------ACTIONS----------");

		for(int i = 0; i < actionList.size(); i++){
			System.out.println("MX: " + actionList.get(i).getNewMove().getNewX() +
								" MY: " + actionList.get(i).getNewMove().getNewY() + 
								" N: " + actionList.get(i).getSandN() + 
								" E: " + actionList.get(i).getSandE() + 
								" S: " + actionList.get(i).getSandS() + 
								" W: " + actionList.get(i).getSandW());
		}
		System.out.println(actionList.size());	
		
		List<State> stateList = generateStates(actionList, initialState);
		
		System.out.println(stateList.size());
	}
	
	//CORRECTO
	public static List<State> generateStates(List<Action> actionList, State state){
		
		List<State> succesorStates = new ArrayList<State>();
		
		for(int i = 0; i < actionList.size(); i++){
			//si x+movx es menor que el borde y si x-movx es mayor que 0
			if( (actionList.get(i).getNewMove().getNewX() < state.getField().length) &&
			    (actionList.get(i).getNewMove().getNewX() >= 0) &&
			    (actionList.get(i).getNewMove().getNewY() < state.getField()[0].length) &&
			    (actionList.get(i).getNewMove().getNewY() >= 0)){
				
				if(state.getPosition(state.getTractorX(), state.getTractorY()) <= actionList.get(i).getSandN() + 
																				  actionList.get(i).getSandE() + 
																				  actionList.get(i).getSandS() + 
																				  actionList.get(i).getSandW()){
					State auxState = applyAction(state, actionList.get(i));
					boolean correct = true;												//controla que en cada terreno no haya una cantidad de arena mas grande que max
					for(int x = 0; x < auxState.getField().length; x++){	
						for(int y = 0; y < auxState.getField()[0].length; y++){
							if(auxState.getField()[x][y] > auxState.getMax()){
								correct = false;
							}
						}
					}
					if(correct){
						succesorStates.add(auxState);
					}
				}
			}
		}
		return succesorStates;
	}
	
	//CORRECTO
	public static List<Action> generateActions(State state){
		List<Action> actionList = new ArrayList<Action>();
		List<Movement> movementList = generateMovements(state);
		boolean goOn;
	    int[] auxA = new int[4];
	   
	    for(int i = 0; i <= state.getPosition(state.getTractorX(), state.getTractorY()) - state.getMean(); i ++){
	    	
	    	for(int j = 0; j <= state.getPosition(state.getTractorX(), state.getTractorY()) - state.getMean(); j++){
	    	
	    		for(int x = 0; x <= state.getPosition(state.getTractorX(), state.getTractorY()) - state.getMean(); x++){
	    		
	    			for( int y = 0; y <= state.getPosition(state.getTractorX(), state.getTractorY()) - state.getMean(); y++){
	    			
	    				auxA[0] = i;		//NORTH MOVED SAND
	    				auxA[1] = j;		//EAST MOVED SAND
	    				auxA[2] = x;		//SOUTH MOVED SAND
	    				auxA[3] = y;		//WEST MOVED SAND
	    				
	    				if(i+j+x+y == state.getPosition(state.getTractorX(), state.getTractorY()) - state.getMean()){
	    	    			
	    					if( !((i > 0) && (state.getTractorX() == 0)) &&
		    					!((j > 0) && (state.getTractorY() == state.getField()[0].length - 1)) &&
		    					!((x > 0) && (state.getTractorX() == state.getField().length - 1)) &&
		    					!((y > 0) && (state.getTractorY() == 0))){
	    					
	    							for( int mov = 0; mov < movementList.size(); mov++){
	    	    						goOn = true;
	    								for( int mov2 = 0; mov2 < movementList.size(); mov2++){
	    									if(i > 0){
	    		    							if(state.getPosition(movementList.get(mov2).getNewX(), movementList.get(mov2).getNewY()) + i > state.getMax()){
	    		    								goOn = false;
	    		    							}
	    		    						}
	    		    						
	    		    						if(j > 0){
	    		    							if(state.getPosition(movementList.get(mov2).getNewX(), movementList.get(mov2).getNewY()) + j > state.getMax()){
	    		    								goOn = false;
	    		    							}
	    		    						}
	    		    						
	    		    						if(x > 0){
	    		    							if(state.getPosition(movementList.get(mov2).getNewX(), movementList.get(mov2).getNewY()) + x > state.getMax()){
	    		    								goOn = false;
	    		    							}
	    		    						}
	    		    						
	    		    						if(y > 0){
	    		    							if(state.getPosition(movementList.get(mov2).getNewX(), movementList.get(mov2).getNewY()) + y > state.getMax()){
	    		    								goOn = false;
	    		    							}
	    		    						}
	    								}
	    									
	    								if(goOn){
		    								Movement m = new Movement(movementList.get(mov).getNewX(),
		    														  movementList.get(mov).getNewY());
		    								Action auxAction = new Action(m, i, j, x, y);
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
	
	//CORRECTO
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

	//CORRECTO
	public static State applyAction(State state, Action action){
		
		State newState = new State();
		
		newState.setField(state.getField());
		newState.setMax(state.getMax());
		newState.setMean(state.getMean());
		newState.setTractorX(action.getNewMove().getNewX());
		newState.setTractorY(action.getNewMove().getNewY());
		
		int centric = state.getPosition(state.getTractorX(), state.getTractorY());		
		centric = centric - action.getSandN() - action.getSandE() - action.getSandS() - action.getSandW();
		newState.setPosition(state.getTractorX(), state.getTractorY(), centric);
		
		if(!(action.getSandN()==0) && (state.getTractorX() - 1 >= 0)){
			int northValue = state.getPosition(state.getTractorX() - 1, state.getTractorY());
			if (!(northValue<= newState.getMax())) {
				northValue = northValue + action.getSandW();
				newState.setPosition(state.getTractorX() , state.getTractorY() - 1, northValue);
			}
		}
		
		if(!(action.getSandE()==0) && (state.getTractorY() + 1 < state.getField()[0].length)){
			int eastValue = state.getPosition(state.getTractorX(), state.getTractorY() + 1);
			if (!( eastValue<= newState.getMax())) {
				eastValue = eastValue + action.getSandW();
				newState.setPosition(state.getTractorX() , state.getTractorY() - 1, eastValue);
			}
		}
		
		if(!(action.getSandS()==0) && state.getTractorX() + 1 < state.getField().length){
			int southValue = state.getPosition(state.getTractorX() + 1, state.getTractorY());
			if (!(southValue <= newState.getMax())) {
				southValue = southValue + action.getSandW();
				newState.setPosition(state.getTractorX() , state.getTractorY() - 1, southValue);
			}
		}
		
		if(!(action.getSandW()==0) && state.getTractorY() - 1 >= 0){
			int westValue = state.getPosition(state.getTractorX() , state.getTractorY() - 1);
			if (!(westValue <= newState.getMax())) {
				westValue = westValue + action.getSandW();
				newState.setPosition(state.getTractorX() , state.getTractorY() - 1, westValue);
			}
			
		}
		return newState;
	}
	
}
