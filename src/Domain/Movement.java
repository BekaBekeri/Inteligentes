package Domain;

public class Movement {

	private byte newX;
	private byte newY;
	
	public Movement(byte x, byte y) {
		newX = x;
		newY = y;
	}
	public byte getNewX() {
		return newX;
	}
	public void setNewX(byte newX) {
		this.newX = newX;
	}
	public byte getNewY() {
		return newY;
	}
	public void setNewY(byte newY) {
		this.newY = newY;
	}
	
	
}