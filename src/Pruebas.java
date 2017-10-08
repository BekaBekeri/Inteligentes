import java.util.*;

public class Pruebas {

	
	public static void main(String[] args){
		
	}
	
	
	public List<Action> generateActions(int max){
		List<Action> actionList = null;
		int i = 0;
		
		do{
			Action auxAction = new Action();
			
			
			actionList.add(auxAction);
			i++;
		}while(checkActions(actionList.get(i), max));
		
		return actionList;
	}
	
	public boolean checkActions(Action action, int max){
		
		
		
		return true;
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
		//se cambia el valor de la posicion norte por la misma + la arena que se ha movido alli
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
		newState.setTractorY(action.getNewY());
		newState.setTractorX(action.getNewX());
		newState.setMax(state.getMax());
		newState.setMean(state.getMean());
		
		return newState;
	}
	
}
