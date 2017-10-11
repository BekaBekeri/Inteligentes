package Domain;
public class Action {
	

	private Movement newMove;
	private int sandN;
	private int sandS;
	private int sandW;
	private int sandE;
	
	
	public Action(Movement newMove, int sandN, int sandE, int sandS, int sandW) {
		super();
		this.newMove = newMove;
		this.sandN = sandN;
		this.sandS = sandS;
		this.sandW = sandW;
		this.sandE = sandE;
	}

	
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
	
	public String toString(State state){
		String aux = "";
		
		aux = "(" + newMove.getNewX() + ", " + newMove.getNewY() + "), [(" + sandN + ", (" + 
			  (state.getTractorX()-1) + ", " + state.getTractorY() + ")), ("+ sandE + ", (" + 
			  state.getTractorX() + ", " + (state.getTractorY()+1) + ")), ("+ sandS + ", (" + 
			  (state.getTractorX()+1) + ", " + state.getTractorY() + ")), (" + sandW + ", (" + 
			  state.getTractorX() + ", " + (state.getTractorY()-1) + "))].";

		return aux;
	}
	
}
