
public class State implements Comparable<State>{
	

	private int [][] field = null;
	private int tractorX = 0;
	private int tractorY = 0;
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private int value;

	
	public State() {
		
		this.value = (int) Math.random()*2000000000 + 1;
		
	}

	public int[][] getField() {
		int [][] aux = new int[field.length][field[0].length];
		for(int i = 0; i <aux.length;i++) {
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
	
	public int compareTo(State newState){
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
