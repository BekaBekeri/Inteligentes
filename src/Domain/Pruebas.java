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
		

		for (int i=0; i<actionList.size(); i++) {
			System.out.println("Action "+i+": "+actionList.get(i).getSandN()+"N "+ actionList.get(i).getSandS()+ "S "+actionList.get(i).getSandE()+ "E "+actionList.get(i).getSandW()+"W, Movement to: "+actionList.get(i).getNewMove().getNewX()+", "+actionList.get(i).getNewMove().getNewY());
		}
		System.out.println();
		System.out.println(actionList.size());
		System.out.println();
		
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

	
}
