
public class Action {
	
	private Movement newMove;
	private int sandN;
	private int sandS;
	private int sandW;
	private int sandE;
	
	public Action(){
		
	}
	
	public int getSandN() {
		return sandN;
	}
	public void setSandN(int sandN) {
		this.sandN = sandN;
	}
	public int getSandS() {
		return sandS;
	}
	public void setSandS(int sandS) {
		this.sandS = sandS;
	}
	public int getSandW() {
		return sandW;
	}
	public void setSandW(int sandW) {
		this.sandW = sandW;
	}
	public int getSandE() {
		return sandE;
	}
	public void setSandE(int sandE) {
		this.sandE = sandE;
	}

	public Movement getNewMove() {
		return newMove;
	}

	public void setNewMove(Movement newMove) {
		this.newMove = newMove;
	}
	
}
