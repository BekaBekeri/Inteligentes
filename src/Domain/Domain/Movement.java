package Domain;

public class Movement {

	private int newX;
	private int newY;
	
	public Movement(int x, int y) {
		newX = x;
		newY = y;
	}
	public int getNewX() {
		return newX;
	}
	public void setNewX(int newX) {
		this.newX = newX;
	}
	public int getNewY() {
		return newY;
	}
	public void setNewY(int newY) {
		this.newY = newY;
	}
	
	
}
