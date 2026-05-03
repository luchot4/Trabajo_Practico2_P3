package vista;

import controller.PlanificadorController;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelLocalidades extends JPanel {

    private DefaultTableModel tableModel;
    private JTable tabla;

    private JTextField campoNombre    = new JTextField(12);
    private JTextField campoProvincia = new JTextField(12);
    private JTextField campoLat       = new JTextField(8);
    private JTextField campoLon       = new JTextField(8);
    private JButton    btnAgregar     = new JButton("Agregar");
    private JButton    btnEliminar    = new JButton("Eliminar seleccionada");

    public PanelLocalidades(PlanificadorController controller) {
        setBorder(BorderFactory.createTitledBorder("Localidades"));
        setLayout(new BorderLayout(6, 6));

        // ── Tabla ──
        String[] columnas = {"Nombre", "Provincia", "Latitud", "Longitud"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(tableModel);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ── Formulario ──
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.anchor = GridBagConstraints.WEST;

        agregarCampo(form, gbc, 0, "Nombre:",    campoNombre);
        agregarCampo(form, gbc, 1, "Provincia:", campoProvincia);
        agregarCampo(form, gbc, 2, "Latitud:",   campoLat);
        agregarCampo(form, gbc, 3, "Longitud:",  campoLon);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        botones.add(btnAgregar);
        botones.add(btnEliminar);
        form.add(botones, gbc);

        add(form, BorderLayout.SOUTH);

        // ── Listeners ──
        btnAgregar.addActionListener(e -> {
            try {
                controller.agregarLocalidad(
                    campoNombre.getText().trim(),
                    campoProvincia.getText().trim(),
                    campoLat.getText().trim(),
                    campoLon.getText().trim()
                );
                limpiarCampos();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                controller.eliminarLocalidad(fila);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná una localidad.");
            }
        });
    }

    private void agregarCampo(JPanel p, GridBagConstraints gbc,
                               int fila, String label, JTextField campo) {
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = fila;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        p.add(campo, gbc);
    }

    private void limpiarCampos() {
        campoNombre.setText("");
        campoProvincia.setText("");
        campoLat.setText("");
        campoLon.setText("");
    }

    // Llamado por VentanaPrincipal cuando el modelo notifica cambios
    public void actualizarTabla(List<Localidad> localidades) {
        tableModel.setRowCount(0);
        for (Localidad l : localidades) {
            tableModel.addRow(new Object[]{
                l.getNombre(),
                l.getProvincia(),
                l.getLatitud(),
                l.getLongitud()
            });
        }
    }
}