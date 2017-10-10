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
									   5,
									   8);
		
		List<Action> actionList = generateActions(initialState);
		
		List<State> stateList = generateStates(actionList, initialState);

		for(int i = 0; i < stateList.size(); i++){
			
			System.out.println("Values are: \n"	+
							   "TractorX: " + stateList.get(i).getTractorX() + " TractorY: " + stateList.get(i).getTractorY() + 
							   " Mean: " + stateList.get(i).getMean() + " Max: " + stateList.get(i).getMax());
			System.out.println("-----------------FIELD-----------------");
			
			for(int x = 0; x < stateList.get(i).getField().length; x++){
				for(int y = 0; y < stateList.get(i).getField()[0].length; y++){
					System.out.print(" " + stateList.get(i).getField()[x][y]);
				}
				System.out.println();
			}
			
		}
		
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
		return succesorStates;
	}
	
	//CORRECTO
	public static List<Action> generateActions(State state){
		List<Action> actionList = new ArrayList<Action>();
		List<Movement> movementList = generateMovements(state);
		List<int[]> aux = new ArrayList<int[]>();
		
		//Controlar que no mueva arena
		int[] array0 = {0, 0, 0, 0};
		aux.add(array0);
		
		//el ultimo caso antes de empezar a descartar
		int[] maxArray = {state.getMax(), state.getMax(), state.getMax(), state.getMax()};
		int k = 0;
		for (int i = 0; i < maxArray.length; i++){
			k = 10 * k + maxArray[i];
		}
		    
		//obtener el array con todos los movs posibles
	    for (int i = 1; i < k; i++ ){
	    	aux.add(digits(i));
	    }
	    
	    int auxSum;																	//variable que controla que no se mueva mas de k unidades de tierra
	    for(int i = 0; i < movementList.size(); i++){								//Para todo movimiento ( N, E, S, O, NULL)
	    	for(int j = 0; j < aux.size(); j++){									//Para cada posible combiancion
	    		auxSum = 0;
	    		
	    		for(int m = 0; m < aux.get(j).length; m++){							//vemos los movimientos de tierra
	    			auxSum += aux.get(j)[m];
	    		}
	    		if(auxSum <= state.getPosition(state.getTractorX(), state.getTractorY())){													//si no superan el limite se add a la lista de actions
	    			
	    			if ((movementList.get(i).getNewX() >= 0) && (movementList.get(i).getNewX() <= state.getField().length)){
	    				
	    				if((movementList.get(i).getNewY() >= 0) && (movementList.get(i).getNewY() <= state.getField().length)){
	    					
	    					Movement m = new Movement(movementList.get(i).getNewX(),
								      				  movementList.get(i).getNewY());
	    					Action auxAction = new Action(m,
										  				  aux.get(j)[0],
										  				  aux.get(j)[1],
										  				  aux.get(j)[2],
										  				  aux.get(j)[3]);
	    					actionList.add(auxAction);
	    				}		
	    			}
	    		}
	    	}
	    }
		return actionList;
	}
	
	//CORRECTO
	public static int[] digits(int number) {
	    
		int[] digits = new int[4];
	    int i = 0;
	   
	    while(number > 0) {
	        digits[i] = (number % 10);
	        number /= 10;
	        i++;
	    }
	    return digits;
	}
	
	//CORRECTO
	private static List<Movement> generateMovements(State state) {
		List<Movement> movementList = new ArrayList<Movement>();
		
		Movement north = new Movement(state.getTractorX()-1, state.getTractorY());
		Movement east = new Movement(state.getTractorX(), state.getTractorY()+1);
		Movement south = new Movement(state.getTractorX()+1, state.getTractorY());
		Movement west = new Movement(state.getTractorX(), state.getTractorY()-1);
		Movement no = new Movement(state.getTractorX(), state.getTractorY());
		
		movementList.add(north);
		movementList.add(east);
		movementList.add(south);
		movementList.add(west);
		movementList.add(no);
		
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
