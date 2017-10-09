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
		
		
	}
	
	
	public static List<State> generateStates(List<Action> actionList, State state){
		
		List<State> succesorStates = new ArrayList<State>();
		
		for(int i = 0; i < actionList.size(); i++){
			
			//if( state.getTractorX() + actionList.get(i).getNewMove().)
			State auxState = applyAction(state, actionList.get(i));						//obtiene un estado que no se sabe si esta bien
			if(!(auxState.getTractorX() > auxState.getField().length)){					
																						//estos dos if controlan que el tractor no se haya salido del campo
				if (!(auxState.getTractorY() > auxState.getField()[0].length)){
					
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
	    		if(auxSum <= state.getMax()){													//si no superan el limite se add a la lista de actions
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
	    
		return actionList;
	}
	
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

	public static State applyAction(State state, Action action){
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*State newState = new State();
		
		//es la cantidad que hay en la posicion en la que esta el tractor
		int newPosition = state.getPosition(state.getTractorX(), state.getTractorY());
		
		//se le resta la cantidad de arena que ha movido a los lados
		newPosition -= action.getSandE();
		newPosition -= action.getSandN();
		newPosition -= action.getSandS();
		newPosition -= action.getSandW();
		
		//se copia el campo tal cual para realizarle los cambios
		newState.setField(state.getField());
		
		//se cambia el valor de la cuadricula donde estaba el tractor al principio
		newState.setPosition(state.getTractorX(),
							 state.getTractorY(),
							 newPosition);
		//se cambia el valor de la posicion norte, este, sur, oeste por la misma + la arena que se ha movido alli
		if(state.getTractorX()-1 < 0){										//NORTE
			newState.setPosition(0,
					 			state.getTractorY(), 
					 			(newState.getPosition(0, state.getTractorY()) + action.getSandN()));
		}else{
			newState.setPosition(state.getTractorX()-1,
					 			 state.getTractorY(), 
					 			 (newState.getPosition((state.getTractorX() - 1), state.getTractorY()) + action.getSandN()));
		}

		if(state.getTractorY()+1 >= state.getField()[0].length){				//ESTE
			newState.setPosition(state.getTractorX(),
								 state.getField()[0].length,
								 (newState.getPosition(state.getTractorX(), state.getField()[0].length) + action.getSandE()));
		}else{
			newState.setPosition(state.getTractorX(),
					 			 state.getTractorY()+1,
					 			 (newState.getPosition(state.getTractorX(), (state.getTractorY() + 1)) + action.getSandE()));
		}	

		if(state.getTractorX()+1 >= state.getField().length){				//SUR
			newState.setPosition(state.getField().length,
					 			 state.getTractorY(),
					 			 (newState.getPosition(state.getField().length, state.getTractorY()) + action.getSandS()));
		}else{
			newState.setPosition(state.getTractorX()+1,
								 state.getTractorY(),
								 (newState.getPosition((state.getTractorX() + 1), state.getTractorY()) + action.getSandS()));
		}

		if(state.getTractorY()-1 < 0){										//OESTE
			newState.setPosition(state.getTractorX(),
					 			 0,
					 			 (newState.getPosition(state.getTractorX(), 0) + action.getSandN()));	
		}else{
			newState.setPosition(state.getTractorX(),
					 			 state.getTractorY()-1,
					 			 (newState.getPosition(state.getTractorX(), (state.getTractorY() + 1) + action.getSandN())));	
		}
		
		
		
		
		//aqui se establecen los valores fijos del nuevo estado
		newState.setTractorY(action.getNewMove().getNewY());
		newState.setTractorX(action.getNewMove().getNewX());
		newState.setMax(state.getMax());
		newState.setMean(state.getMean());
		
		return newState;*/
	}
	
}
