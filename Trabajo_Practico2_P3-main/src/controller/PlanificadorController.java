package controller;

import modelo.*;

public class PlanificadorController {

    private PlanificadorModelo modelo;

    public PlanificadorController(PlanificadorModelo modelo) {
        this.modelo = modelo;
    }

    public void agregarLocalidad(String nombre, String provincia,
                                  String latStr, String lonStr) {
        try {
            double lat = Double.parseDouble(latStr.replace(",", "."));
            double lon = Double.parseDouble(lonStr.replace(",", "."));
            modelo.agregarLocalidad(new Localidad(nombre, provincia, lat, lon));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Latitud y longitud deben ser números.");
        }
    }

    public void eliminarLocalidad(int indice) {
        modelo.eliminarLocalidad(indice);
    }

    public void actualizarParametros(String costoPorKmStr,
                                      String porcentajeStr,
                                      String costoFijoStr) {
        try {
            double costoPorKm    = Double.parseDouble(costoPorKmStr.replace(",", "."));
            double porcentaje    = Double.parseDouble(porcentajeStr.replace(",", "."));
            double costoFijo     = Double.parseDouble(costoFijoStr.replace(",", "."));
            modelo.setParametros(costoPorKm, porcentaje, costoFijo);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los parámetros deben ser números válidos.");
        }
    }

    public void calcularPlanificacion() {
        if (modelo.getLocalidades().size() < 2) {
            throw new IllegalStateException("Se necesitan al menos 2 localidades.");
        }
        double km    = modelo.getParametros().getCostoPorKm();
        double porc  = modelo.getParametros().getPorcentajeExtraLargaDistancia();
        double fijo  = modelo.getParametros().getCostoFijoInterProvincial();
        if (km <= 0 || porc < 0 || fijo < 0) {
            throw new IllegalStateException("Configure los parámetros antes de calcular.");
        }
        modelo.calcularPlanificacion();
    }
}