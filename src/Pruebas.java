import java.util.*;

public class Pruebas {

	
	public static void main(String[] args){
		
	}
	
	
	public List<Action> generateActions(int max, State state){
		List<Action> actionList = new ArrayList<Action>();
		List<Movement> movementList = generateMovements(state);
		List<int[]> aux = new ArrayList<int[]>();
		
		//Controlar que no mueva arena
		int[] array0 = {0, 0, 0, 0};
		aux.add(array0);
		
		//el ultimo caso antes de empezar a descartar
		int[] maxArray = {max, max, max, max};
		int k = 0;
		for (int i = 0; i < maxArray.length; i++){
			k = 10 * k + maxArray[i];
		}
		    
		//obtener el array con todos lso movs posibles
	    for (int i = 1; i < k; i++ ){
	    	aux.add(digits(i));
	    }
		
	    int p = 0;
	    do{
	    	int auxSum = 0;
	    	for( int i = 0; i < aux.get(p).length; p++){
	    		auxSum += aux.get(p)[i];
	    	}
	    	p++;
	    }while( p < aux.size());
	    
		return actionList;
	}
	
	 public int[] digits(int number) {
	    
		int[] digits = new int[4];
	    int i = 0;
	   
	    while(number > 0) {
	        digits[i] = (number % 10);
	        number /= 10;
	        i++;
	    }
	    return digits;
	}
	private List<Movement> generateMovements(State state) {
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

	public State applyAction(State state, Action action){
		
		State newState = new State();
		
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
		newState.setPosition(state.getTractorX()-1,
							 state.getTractorY(), 
							 (newState.getPosition((state.getTractorX() - 1), state.getTractorY()) + action.getSandN()));	//NORTE
		
		newState.setPosition(state.getTractorX(),
							 state.getTractorY()+1,
							 (newState.getPosition(state.getTractorX(), (state.getTractorY() + 1)) + action.getSandE()));	//ESTE
		
		newState.setPosition(state.getTractorX()+1,
							 state.getTractorY(),
							 (newState.getPosition((state.getTractorX() + 1), state.getTractorY()) + action.getSandS()));	//SUR
		
		newState.setPosition(state.getTractorX(),
							 state.getTractorY()-1,
							 (newState.getPosition(state.getTractorX(), (state.getTractorY() + 1) + action.getSandN())));	//OESTE
		
		
		//aqui se establecen los valores fijos del nuevo estado
		newState.setTractorY(action.getNewMove().getNewY());
		newState.setTractorX(action.getNewMove().getNewX());
		newState.setMax(state.getMax());
		newState.setMean(state.getMean());
		
		return newState;
	}
	
}
