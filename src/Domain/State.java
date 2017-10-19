package Domain;
public class State implements Comparable<State> {
	
	private int [][] field;
	
	private int tractorX;
	private int tractorY;
	private int mean;
	private int max;
	private State father = null; 
	private Action appliedAction = null;
	private int cost = 0; 
	
	public State(int[][] field, int tractorX, int tractorY, int mean, int max) {
		super();
		this.field = field;
		this.tractorX = tractorX;
		this.tractorY = tractorY;
		this.mean = mean;
		this.max = max; 
	}

	
	public State() {
	}

	public int getMean() {
		return mean;
	}

	public void setMean(int mean) {
		this.mean = mean;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int[][] getField() {
		int [][] aux=new int[field.length][field[0].length];
		for(int i=0;i<aux.length;i++) {
			for(int j=0;j<aux[i].length;j++) {
				aux[i][j]=field[i][j];
			}
		}
		return aux;
	}

	public void setField(int[][] field) {
		this.field = field;
	}

	public int getTractorX() {
		return tractorX;
	}

	public void setTractorX(int tractorX) {
		this.tractorX = tractorX;
	}

	public int getTractorY() {
		return tractorY;
	}

	public void setTractorY(int tractorY) {
		this.tractorY = tractorY;
	}

	public int getPosition(int x, int y){
		return field[x][y];
	}
	
	public void setPosition(int x, int y, int value){
		field[x][y] = value;
	}

	public State getFather() {
		return father;
	}

	public void setFather(State father) {
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


	public int compareTo(State newState){
		int r = 0;
		
		if(newState.getCost() > cost){
			r = -1;
		}
		else if(newState.getCost() < cost){
			r = +1;
		}
		
		return r;
	}
	
}