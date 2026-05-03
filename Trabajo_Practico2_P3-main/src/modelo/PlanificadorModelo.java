package modelo;

import java.util.*;

public class PlanificadorModelo {

    private List<Localidad> localidades = new ArrayList<>();
    private ParametrosRed parametros    = new ParametrosRed(0, 0, 0);
    private ResultadoMST ultimoResultado;

    private List<ModelObserver> observers = new ArrayList<>();

    // ── Observer ────────────────────────────────────────────────────────────

    public void addObserver(ModelObserver o)    { observers.add(o); }
    public void removeObserver(ModelObserver o) { observers.remove(o); }

    private void notificar(ModelEvent evento) {
        for (ModelObserver o : observers) o.onModelChanged(evento);
    }

    // ── Localidades ──────────────────────────────────────────────────────────

    public void agregarLocalidad(Localidad l) {
        localidades.add(l);
        notificar(ModelEvent.LOCALIDAD_AGREGADA);
    }

    public void eliminarLocalidad(int indice) {
        localidades.remove(indice);
        notificar(ModelEvent.LOCALIDAD_ELIMINADA);
    }

    public List<Localidad> getLocalidades() {
        return Collections.unmodifiableList(localidades);
    }

    // ── Parámetros ───────────────────────────────────────────────────────────

    public void setParametros(double costoPorKm, double porcentajeExtra, double costoFijo) {
        parametros.setCostoPorKm(costoPorKm);
        parametros.setPorcentajeExtraLargaDistancia(porcentajeExtra);
        parametros.setCostoFijoInterProvincial(costoFijo);
        notificar(ModelEvent.PARAMETROS_ACTUALIZADOS);
    }

    public ParametrosRed getParametros() { return parametros; }

    // ── Planificación ────────────────────────────────────────────────────────

    public void calcularPlanificacion() {
        ultimoResultado = AlgoritmoKruskal.calcular(localidades, parametros);
        notificar(ModelEvent.PLANIFICACION_CALCULADA);
    }

    public ResultadoMST getUltimoResultado() { return ultimoResultado; }
}