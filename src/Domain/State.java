package Domain;

import java.util.Arrays;

public class State{
	
	private byte [][] field;
	private byte tractorX;
	private byte tractorY;
	private int value;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

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

	public boolean equals(State state) {
		
		for (byte i=0; i<field.length; i++) {
			for (byte j=0; j<field[i].length; j++) {
				if (field[i][j]!=state.getPosition(i, j)) {
					return false;
				}
			}
		}
		if (!(tractorX == state.getTractorX() && tractorY == state.getTractorY())) {
			return false;
		}
		return true;
		
		/*if (!Arrays.deepEquals(field, state.getField())) {
			return false;
		}else {
			if (!(tractorX == state.getTractorX() && tractorY == state.getTractorY())) {
				return false;
			}
		}
		return true;*/
	}
	
}