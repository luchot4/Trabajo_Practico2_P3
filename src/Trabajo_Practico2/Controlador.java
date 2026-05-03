package Trabajo_Practico2;

public class Controlador {
	private Modelo modelo;

	public Controlador(Modelo modelo) {
		this.modelo = modelo;
	}
	public void registrarLocalidad(String nombre, String provincia, double latitud, double longitud) {
		modelo.agregarLocalidad(new Localidad(nombre, provincia, latitud, longitud));
	}
	
	public void planificar(double costoKm, double porcentajeExtra, double costoFijoProvincia) {
		modelo.calcularAGM(costoKm, porcentajeExtra, costoFijoProvincia);
	}
}
