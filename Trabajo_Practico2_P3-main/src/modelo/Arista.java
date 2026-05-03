package modelo;

public class Arista implements Comparable<Arista> {
    private Localidad origen;
    private Localidad destino;
    private double costo;

    public Arista(Localidad origen, Localidad destino, double costo) {
        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }

    public Localidad getOrigen()  { return origen; }
    public Localidad getDestino() { return destino; }
    public double getCosto()      { return costo; }

    @Override
    public int compareTo(Arista otra) {
        return Double.compare(this.costo, otra.costo);
    }

    @Override
    public String toString() {
        return origen.getNombre() + " — " + destino.getNombre()
               + " | $" + String.format("%,.2f", costo);
    }
}