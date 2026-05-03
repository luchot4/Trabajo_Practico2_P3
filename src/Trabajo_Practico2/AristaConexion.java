package Trabajo_Practico2;

public class AristaConexion implements Comparable<AristaConexion> {
	
	int origen;
	int destino;
	double costo;
	
	public AristaConexion(int origen, int destino, double costo) {
		this.origen = origen;
		this.destino = destino;
		this.costo = costo;
	}
	public int getOrigen() {
		return origen;
	}
	public int getDestino() {
		return destino;
	}
	public double getCosto() {
		return costo;
	}
	@Override
	public int compareTo(AristaConexion otraArista) {
		// TODO Auto-generated method stub
		return Double.compare(this.costo, otraArista.costo);
	}
	
}
