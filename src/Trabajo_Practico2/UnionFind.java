package Trabajo_Practico2;

public class UnionFind {
	private int[] padre;
	private int[] rango;
	
	public UnionFind(int n) {
		padre = new int[n];
		rango = new int[n];
		for(int i = 0; i < n; i++) {
			padre[i] = i;
			rango[i] = 0;
		}
	}
	public int Find(int x) {
		if(padre[x] != x) padre[x] = Find(padre[x]);
		return padre[x];
	}
	
	public boolean Union(int a, int b) {
		int ramaA = Find(a);
		int ramaB = Find(b);
		
		if(ramaA == ramaB) {
			return false;
		}
		
		if(rango[ramaA] < rango[ramaB]) {
			padre[ramaA] = ramaB;
		}else if(rango[ramaA] > rango[ramaB]){
			padre[ramaB] = ramaA;
		}else {
			padre[ramaB] = ramaA;
			rango[ramaA]++;
		}
		return true;
	}
	
}
