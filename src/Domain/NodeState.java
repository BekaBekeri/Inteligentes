package Domain;

public class NodeState extends State implements Comparable<NodeState> {

	private NodeState father = null; 
	private Action appliedAction = null;
	private int cost;
	private int value;
	private int depth;
	
	
	public NodeState() {
	}
	
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public NodeState getFather() {
		return father;
	}
	

	public void setFather(NodeState father) {
		this.father = father;
	}

	public Action getAppliedAction() {
		return appliedAction;
	}

	public void setAppliedAction(Action appliedAction) {
		this.appliedAction = appliedAction;
	}
	
	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}

	
	public void setStrategy(String strategy) {
		if (strategy.equals("BFS")) value=depth;
		if (strategy.equals("DFS") || strategy.equals("IDS")) value=-depth;
		if (strategy.equals("UCS")) value = cost;
	}

	public int compareTo(NodeState newState){
		int r = 0;
		
		if(newState.getValue() > this.value){
			r = -1;
		}
		else if(newState.getValue() < this.value){
			r = +1;
		}
		
		return r;
	}

}
