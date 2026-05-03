package modelo;

public class ParametrosRed {
    private double costoPorKm;
    private double porcentajeExtraLargaDistancia; // ej: 20 significa 20%
    private double costoFijoInterProvincial;

    public ParametrosRed(double costoPorKm, double porcentajeExtra, double costoFijo) {
        this.costoPorKm = costoPorKm;
        this.porcentajeExtraLargaDistancia = porcentajeExtra;
        this.costoFijoInterProvincial = costoFijo;
    }

    public double getCostoPorKm()                  { return costoPorKm; }
    public double getPorcentajeExtraLargaDistancia(){ return porcentajeExtraLargaDistancia; }
    public double getCostoFijoInterProvincial()     { return costoFijoInterProvincial; }

    public void setCostoPorKm(double v)                   { this.costoPorKm = v; }
    public void setPorcentajeExtraLargaDistancia(double v){ this.porcentajeExtraLargaDistancia = v; }
    public void setCostoFijoInterProvincial(double v)     { this.costoFijoInterProvincial = v; }
}