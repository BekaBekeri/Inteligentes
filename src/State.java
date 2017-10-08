
public class State {
	
	private int [][] field;
	private int tractorX;
	private int tractorY;
	private int mean;
	private int max;
	
	
	
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
		return field;
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
