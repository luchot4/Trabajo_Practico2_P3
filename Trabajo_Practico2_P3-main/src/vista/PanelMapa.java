package vista;

import modelo.Arista;
import modelo.Localidad;
import modelo.ResultadoMST;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelMapa extends JPanel {

    private JMapViewer mapa;
    private List<MapMarkerDot> marcadores = new ArrayList<>();
    private List<MapPolygon>   lineasMST  = new ArrayList<>();

    public PanelMapa() {
        setLayout(new BorderLayout());
        mapa = new JMapViewer();
        mapa.setTileSource(new OsmTileSource.Mapnik());
        mapa.setZoomControlsVisible(true);
        // Centrar en Argentina por default
        mapa.setDisplayPosition(new Coordinate(-38.0, -63.0), 4);
        add(mapa, BorderLayout.CENTER);
    }

    // Llamado cuando se agrega/elimina una localidad
    public void actualizarMarcadores(List<Localidad> localidades) {
        // Limpiar marcadores anteriores
        for (MapMarkerDot m : marcadores) mapa.removeMapMarker(m);
        marcadores.clear();

        for (Localidad l : localidades) {
            MapMarkerDot marker = new MapMarkerDot(
                l.getNombre(),
                new Coordinate(l.getLatitud(), l.getLongitud())
            );
            marker.setBackColor(Color.BLUE);
            marcadores.add(marker);
            mapa.addMapMarker(marker);
        }

        // Ajustar zoom para mostrar todos los puntos
        if (!localidades.isEmpty()) {
            mapa.setDisplayToFitMapMarkers();
        }
    }

    // Llamado cuando se calcula el MST
    public void dibujarMST(ResultadoMST resultado) {
        // Limpiar líneas anteriores
        for (MapPolygon p : lineasMST) mapa.removeMapPolygon(p);
        lineasMST.clear();

        for (Arista a : resultado.getConexiones()) {
            Coordinate desde = new Coordinate(
                a.getOrigen().getLatitud(), a.getOrigen().getLongitud());
            Coordinate hasta = new Coordinate(
                a.getDestino().getLatitud(), a.getDestino().getLongitud());

            // MapPolygon con 3 puntos donde el 3ro repite el 1ro = línea
            List<Coordinate> puntos = List.of(desde, hasta, desde);
            MapPolygon linea = new MapPolygonImpl(puntos);
            lineasMST.add(linea);
            mapa.addMapPolygon(linea);
        }
    }
}
