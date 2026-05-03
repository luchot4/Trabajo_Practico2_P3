package vista;

import controller.PlanificadorController;
import modelo.*;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame implements ModelObserver {

    private PlanificadorModelo modelo;
    private PanelLocalidades   panelLocalidades;
    private PanelParametros    panelParametros;
    private PanelResultados    panelResultados;
    private PanelMapa          panelMapa;        // ← declarado

    public VentanaPrincipal(PlanificadorModelo modelo, PlanificadorController controller) {
        super("Planificador de fibra óptica");
        this.modelo = modelo;
        modelo.addObserver(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 650);
        setLocationRelativeTo(null);

        // Crear paneles
        panelLocalidades = new PanelLocalidades(controller);
        panelParametros  = new PanelParametros(controller);
        panelResultados  = new PanelResultados();
        panelMapa        = new PanelMapa();

        // Botón calcular
        JButton btnCalcular = new JButton("Calcular planificación");
        btnCalcular.setFont(btnCalcular.getFont().deriveFont(Font.BOLD));
        btnCalcular.addActionListener(e -> {
            try {
                controller.calcularPlanificacion();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Atención", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Panel izquierdo: localidades + parámetros + botón en vertical
        JPanel wrapBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapBtn.add(btnCalcular);

        JPanel izquierda = new JPanel();
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        izquierda.add(panelLocalidades);
        izquierda.add(Box.createVerticalStrut(6));
        izquierda.add(panelParametros);
        izquierda.add(Box.createVerticalStrut(6));
        izquierda.add(wrapBtn);

        // Panel derecho: solo resultados
        JPanel derecha = new JPanel(new BorderLayout(6, 6));
        derecha.add(panelResultados, BorderLayout.CENTER);

        // Split derecho: mapa | resultados
        JSplitPane splitDer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelMapa, derecha);
        splitDer.setDividerLocation(500);
        splitDer.setResizeWeight(0.6);

        // Split principal: izquierda | (mapa + resultados)
        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, izquierda, splitDer);
        splitPrincipal.setDividerLocation(300);

        add(splitPrincipal);
    }

    @Override
    public void onModelChanged(ModelEvent evento) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case LOCALIDAD_AGREGADA:
                case LOCALIDAD_ELIMINADA:
                    panelLocalidades.actualizarTabla(modelo.getLocalidades());
                    panelMapa.actualizarMarcadores(modelo.getLocalidades());
                    break;
                case PLANIFICACION_CALCULADA:
                    panelResultados.mostrarResultado(modelo.getUltimoResultado());
                    panelMapa.actualizarMarcadores(modelo.getLocalidades());
                    panelMapa.dibujarMST(modelo.getUltimoResultado());
                    break;
                default:
                    break;
            }
        });
    }
}