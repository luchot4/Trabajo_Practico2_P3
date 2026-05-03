package modelo;

import java.util.List;

public class ResultadoMST {
    private List<Arista> conexiones;
    private double costoTotal;

    public ResultadoMST(List<Arista> conexiones, double costoTotal) {
        this.conexiones = conexiones;
        this.costoTotal = costoTotal;
    }

    public List<Arista> getConexiones() { return conexiones; }
    public double getCostoTotal()       { return costoTotal; }
}