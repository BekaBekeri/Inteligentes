package Domain;

public class Node implements Comparable<Node> {

	private Node father = null;
	private State state=null;
	private Action appliedAction = null;
	private int cost;
	private double value;
	private int depth;
	private double heuristic;
	
	public Node(Node father, State state, Action appliedAction, int cost, int depth) {
		this.father = father;
		this.state = state;
		this.appliedAction= appliedAction;
		this.cost=cost;
		this.depth=depth;
		
	}
	
	public void updateValues(int mean, String strategy) {
		setHeuristic(mean);
		setStrategy(strategy);
	}
	
	public State getState() {
		return state;
	}
	
	public double getHeuristic() {
		return heuristic;
	}
	
	public int getDepth() {
		return depth;
	}

	public double getValue() {
		return value;
	}

	public Node getFather() {
		return father;
	}

	public Action getAppliedAction() {
		return appliedAction;
	}

	public int getCost() {
		return cost;
	}

	public int compareTo(Node newState){
		int r = 0;
		
		if(newState.getValue() > this.value){
			r = -1;
		}
		else if(newState.getValue() < this.value){
			r = +1;
		}
		
		return r;
	}

	private void setStrategy(String strategy) {
		if (strategy.equals("BFS")) {
			value = depth;
		}else if(strategy.equals("DFS") || strategy.equals("IDS")){
			value = -depth;
		}else if(strategy.equals("UCS")) {
			value = cost;
		}else if(strategy.equals("A*")) {
			value = cost + heuristic;
		}else if(strategy.equals("Variant A*")) {
			value = heuristic * 0.7 + cost * 0.3;
		}
	}
	
	protected void setHeuristic(int mean) {
		int heuristic=0;
		for (byte i=0; i<state.getField().length; i++) {
			for (byte j=0; j<state.getField()[0].length; j++) {
				if (state.getPosition(i, j)!=mean) heuristic++;
			}
		}
		this.heuristic = heuristic;
	}
	
}
