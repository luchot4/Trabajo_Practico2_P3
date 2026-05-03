package Trabajo_Practico2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Modelo {
	private List<Localidad> localidades = new ArrayList<>();
	private List<Observador> observador = new ArrayList<>();
	
	public void agregarLocalidad(Localidad l) {
		localidades.add(l);
		notificar("Localidad agregada " + l.nombre);
		
	}
	public double calcularAGM(double costoKm, double porcentajeExtra, double costoFijoProvincia) {
		List<AristaConexion> aristas = construirAristas(localidades, costoKm, porcentajeExtra, costoFijoProvincia);
		double total = kruskalMST(aristas, localidades.size());
		notificar("Costo total de AGM: " + total);
		return total;
	}
	private List<AristaConexion> construirAristas(List<Localidad> locs, double costoKm, double porcentajeExtra, double costoFijoProvincia) {
		List<AristaConexion> aristas = new ArrayList<>();
		for(int i = 0; i < locs.size(); i++) {
			for(int j = i+1; j < locs.size(); j++) {
				double costo = costoConexion(locs.get(i), locs.get(j), costoKm, porcentajeExtra, costoFijoProvincia);
				aristas.add(new AristaConexion(i,j,costo));
			}
		}
		return aristas;
	}
	private double costoConexion(Localidad a, Localidad b, double costoKm, double porcentajeExtra, double costoFijoProvincia) {
		double distancia = distanciaKm(a, b);
		double costo = distancia * costoKm;
		if(distancia > 300) costo *=(1+porcentajeExtra /100.0);
		if(!a.provincia.equals(b.provincia)) costo += costoFijoProvincia;
		return costo;
	}
	private double distanciaKm(Localidad a, Localidad b) {
		double R = 6471;
		double distLatitud = Math.toRadians(b.latitud - a.latitud);
		double distLongitud = Math.toRadians(b.longitud - a.longitud);
		double latitud1 = Math.toRadians(a.latitud);
		double latitud2 = Math.toRadians(b.latitud);
		double h = Math.sin(distLatitud/2)*Math.sin(distLatitud/2) + Math.sin(distLongitud/2)*Math.sin(distLongitud/2)*
				Math.cos(latitud1)*Math.cos(latitud2);
		double c = 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1-h));
		return R * c;
	}
	private double kruskalMST(List<AristaConexion> aristas, int n) {
		Collections.sort(aristas);
		UnionFind uf = new UnionFind(n);
		double total = 0;
		int usados = 0;
		
		for(AristaConexion a : aristas) {
			if(uf.Union(a.origen, a.destino)) {
				total+=a.costo;
				notificar("Conectar " + localidades.get(a.origen).nombre + " - " + localidades.get(a.destino).nombre
						+ " costo: " + a.costo);
				usados++;
				if(usados == n-1) break;
				
			}
		}
		return total;
	}
	public void addObserver(Observador o) {
		observador.add(o);
	}
	public void notificar(String mensaje) {
		for(Observador o : observador) {
			o.actualizar(mensaje);
		}
	}
}
