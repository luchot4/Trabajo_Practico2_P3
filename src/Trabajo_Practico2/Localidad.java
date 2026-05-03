package Trabajo_Practico2;

public class Localidad {
	String nombre;
	String provincia;
	double latitud;
	double longitud;
	
	public Localidad(String nombre, String provincia, double latitud, double longitud) {
		this.nombre = nombre;
		this.provincia = provincia;
		this.latitud = latitud;
		this.longitud = longitud;
		
	}
	public String getNombre() {
		return nombre;
	}
	public String getProvincia() {
		return provincia;
	}
	public double getLatitud() {
		return latitud;
	}
	public double getLongitud() {
		return longitud;
	}
}
