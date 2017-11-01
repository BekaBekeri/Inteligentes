import java.util.*;


public class Main {

	
	public static void main(String[] args){
		SortedSet<State> set;
		LinkedList<State> linkedList;
		PriorityQueue<State> pQueue;
		
		Integer[] optionsS = {100, 1000, 10000, 100000, 200000, 500000, 1000000, 2000000, 5000000, 10000000, 20000000, 50000000, 100000000, 1000000000};
		Integer[] optionsP = {100, 1000, 10000, 100000, 200000, 500000, 1000000, 2000000, 5000000, 10000000, 20000000, 50000000};
		Integer[] optionsL = {100, 1000, 5000};
		long t1, t2;
		
		for(int i = 0; i < optionsL.length; i++){
			t1 = System.currentTimeMillis();
			linkedList = new LinkedList<State>();
			for(int j = 0; j <optionsL[i] ; j++){
				State state = new State();
				linkedList.add(state);
			}
			selection(linkedList);
			t2 = System.currentTimeMillis();
			System.out.println("The time to insert " + optionsL[i] + " states in a LinkedList has been: " + (t2 - t1) + ".");
		}
		
		for(int i = 0; i < optionsS.length; i++){
			t1 = System.currentTimeMillis();
			set = new TreeSet<State>();
			for(int j = 0; j < optionsS[i]; j++){
				State state = new State();
				set.add(state);
			}
			
			t2 = System.currentTimeMillis();
			System.out.println("The time to instert " + optionsS[i] + " states in a SortedSet has been: " + (t2 - t1) + ".");
		}
		
		for(int i = 0; i < optionsP.length; i++){
			t1 = System.currentTimeMillis();
			pQueue = new PriorityQueue<State>();
			for(int j = 0; j < optionsS[i]; j++){
				State state = new State();
				pQueue.add(state);
			}
			
			t2 = System.currentTimeMillis();
			System.out.println("The time to instert " + optionsP[i] + " states in a PriorityQueue has been: " + (t2 - t1) + ".");
		}
	}
	
	public static void selection(LinkedList<State> list){
		int index;
		State aux;
		int n = list.size();
		
		for(int i = 0; i < n-1; i++){
			index = i;
			
			for (int j = i + 1; j < n; j++){
				if(list.get(j).getValue() < list.get(index).getValue()){
					index = j;
				}
			}
			
			aux = list.get(i);
			list.set(i, list.get(index));
			list.set(index, aux);
			
		}
				
	}
}
