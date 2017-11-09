package Domain;
public class State{
	
	private byte [][] field;
	private byte tractorX;
	private byte tractorY;
	
	public State(byte[][] field, byte tractorX, byte tractorY) {
		this.field = field;
		this.tractorX = tractorX;
		this.tractorY = tractorY;
	}

	public byte[][] getField(){
		return this.field;
	}
	
	public byte[][] copyField() {
		byte [][] aux=new byte[field.length][field[0].length];
		for(int i=0;i<aux.length;i++) {
			System.arraycopy(field[i], 0, aux[i], 0, field[0].length);
		}
		return aux;
	}

	public byte getTractorX() {
		return tractorX;
	}

	public void setTractorX(byte tractorX) {
		this.tractorX = tractorX;
	}

	public byte getTractorY() {
		return tractorY;
	}

	public void setTractorY(byte tractorY) {
		this.tractorY = tractorY;
	}

	public byte getPosition(byte x, byte y){
		return field[x][y];
	}
	
	public void setPosition(byte x, byte y, byte value){
		field[x][y] = value;
	}

	
}