package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.CalculadorCosto;

public class PanelResultados extends JPanel {

    private DefaultTableModel tableModel;
    private JLabel labelCostoTotal = new JLabel("Costo total: —");

    public PanelResultados() {
        setBorder(BorderFactory.createTitledBorder("Resultado de la planificación"));
        setLayout(new BorderLayout(6, 6));

        String[] columnas = {"Origen", "Destino", "Distancia aprox.", "Costo ($)"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = new JTable(tableModel);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        labelCostoTotal.setFont(labelCostoTotal.getFont().deriveFont(Font.BOLD, 14f));
        labelCostoTotal.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        add(labelCostoTotal, BorderLayout.SOUTH);
    }

    public void mostrarResultado(ResultadoMST resultado) {
        tableModel.setRowCount(0);
        for (Arista a : resultado.getConexiones()) {
            double distancia = CalculadorCosto.calcularDistanciaKm(
                a.getOrigen(), a.getDestino());
            tableModel.addRow(new Object[]{
                a.getOrigen().getNombre(),
                a.getDestino().getNombre(),
                String.format("%.1f km", distancia),
                String.format("$%,.2f", a.getCosto())
            });
        }
        labelCostoTotal.setText(
            String.format("Costo total de instalación: $%,.2f", resultado.getCostoTotal()));
    }
}