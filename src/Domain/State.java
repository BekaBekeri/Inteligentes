package Domain;
public class State{
	
	private int [][] field;
	private int tractorX;
	private int tractorY;
	
	public State(int[][] field, int tractorX, int tractorY, int mean, int max) {
		this.field = field;
		this.tractorX = tractorX;
		this.tractorY = tractorY;
	}

	
	public State() {
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

	
}