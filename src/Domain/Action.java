package Domain;
public class Action {

	private Movement move;
	private byte sandN;
	private byte sandS;
	private byte sandW;
	private byte sandE;
	
	
	public Action(Movement newMove, byte sandN, byte sandE, byte sandS, byte sandW) { //Just testing EGit
		super();
		this.move = newMove;
		this.sandN = sandN;
		this.sandS = sandS;
		this.sandW = sandW;
		this.sandE = sandE;
	}

	
	public Action(){
		
	}
	
	public byte getSandN() {
		return sandN;
	}
	public void setSandN(byte sandN) {
		this.sandN = sandN;
	}
	public byte getSandS() {
		return sandS;
	}
	public void setSandS(byte sandS) {
		this.sandS = sandS;
	}
	public byte getSandW() {
		return sandW;
	}
	public void setSandW(byte sandW) {
		this.sandW = sandW;
	}
	public byte getSandE() {
		return sandE;
	}
	public void setSandE(byte sandE) {
		this.sandE = sandE;
	}

	public Movement getNewMove() {
		return move;
	}

	public void setNewMove(Movement newMove) {
		this.move = newMove;
	}
	
	public String toString(State state){
		String aux = "";
		int totalCost = sandN + sandS + sandE + sandW +1;
		aux = "((" + move.getNewX() + ", " + move.getNewY() + "), [(" + sandN + ", (" + 
			  (state.getTractorX()-1) + ", " + state.getTractorY() + ")), ("+ sandE + ", (" + 
			  state.getTractorX() + ", " + (state.getTractorY()+1) + ")), ("+ sandS + ", (" + 
			  (state.getTractorX()+1) + ", " + state.getTractorY() + ")), (" + sandW + ", (" + 
			  state.getTractorX() + ", " + (state.getTractorY()-1) + "))] " + totalCost+")";

		return aux;
	}
	
}