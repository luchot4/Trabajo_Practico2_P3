package modelo;

public class CalculadorCosto {

    private static final double RADIO_TIERRA_KM = 6371.0;
    private static final double LIMITE_LARGA_DISTANCIA_KM = 300.0;

    /**
     * Calcula el costo de conectar dos localidades según los parámetros de red.
     */
    public static double calcular(Localidad a, Localidad b, ParametrosRed params) {
        double distancia = calcularDistanciaKm(a, b);

        double costo = distancia * params.getCostoPorKm();

        if (distancia > LIMITE_LARGA_DISTANCIA_KM) {
            costo += costo * (params.getPorcentajeExtraLargaDistancia() / 100.0);
        }

        if (!a.getProvincia().equalsIgnoreCase(b.getProvincia())) {
            costo += params.getCostoFijoInterProvincial();
        }

        return costo;
    }

    /**
     * Fórmula de Haversine: distancia entre dos puntos en la superficie terrestre.
     */
    public static double calcularDistanciaKm(Localidad a, Localidad b) {
        double lat1 = Math.toRadians(a.getLatitud());
        double lat2 = Math.toRadians(b.getLatitud());
        double dLat = Math.toRadians(b.getLatitud() - a.getLatitud());
        double dLon = Math.toRadians(b.getLongitud() - a.getLongitud());

        double h = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(lat1) * Math.cos(lat2)
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 2 * RADIO_TIERRA_KM * Math.asin(Math.sqrt(h));
    }
}