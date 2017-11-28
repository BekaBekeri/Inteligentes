package Testing;

import Domain.State;
import java.util.*;

public class Test {

	
	public static void main(String[] args){
		Hashtable<Long, State> hashT = new Hashtable<Long, State>();
		State st;
		byte[][] field = {{1, 0, 1}, 
						 {3, 9, 8}, 
						 {7, 7, 3}};
		long t1 = System.currentTimeMillis();
		for(byte i = 0; i < 127; i++){
			for(byte j = 127; j > 0; j--){
				st = new State(field, i, j);
				hashT.put(md5(st), st);
			}
		}
		long t2 = System.currentTimeMillis();
		
		byte a = 15, b = 20;
		State st2 = new State(field, a, b);
		if(hashT.get(md5(st2)) != null){
			System.out.println("Bk pto amo");
		}
		
		System.out.println("time to insert 128x128 states in hash table: "+ (t2-t1));
		
	}
	
	public static long md5(State state){

		byte[][] field = state.copyField();
		String aux =  Integer.toString(field[0][0]) + Integer.toString(field[0][1]) + Integer.toString(field[0][2]) +
					  Integer.toString(field[1][0]) + Integer.toString(field[1][1]) + Integer.toString(field[1][2]) + 
					  Integer.toString(field[2][0]) + Integer.toString(field[2][1]) + Integer.toString(field[2][2]) + 
					  Integer.toString(state.getTractorX()) + Integer.toString(state.getTractorY()); 
		return strToLong(aux);
	}
	
	public static long strToLong( String str ){
	    int i = 0;
	    long num = 0;
	    boolean isNeg = false;

	    if (str.charAt(0) == '-') {
	        isNeg = true;
	        i = 1;
	    }

	    while( i < str.length()) {
	        num *= 10;
	        num += str.charAt(i++) - '0';
	    }
	    if (isNeg)
	        num = -num;
	    return num;
	}
}
