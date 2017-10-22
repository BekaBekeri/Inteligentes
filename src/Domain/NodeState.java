package Domain;

public class NodeState extends State implements Comparable<NodeState> {

	private NodeState father = null; 
	private Action appliedAction = null;
	private int cost = 0;
	private int value;
	
	public NodeState() {
		this.value = (int) Math.random()*2000000000 + 1;
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


	public int compareTo(NodeState newState){
		int r = 0;
		
		if(newState.getValue() > this.cost){
			r = -1;
		}
		else if(newState.getValue() < this.cost){
			r = +1;
		}
		
		return r;
	}

}
