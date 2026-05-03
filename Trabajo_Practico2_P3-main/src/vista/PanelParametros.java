package vista;

import controller.PlanificadorController;
import javax.swing.*;
import java.awt.*;

public class PanelParametros extends JPanel {

    private JTextField campoCostoPorKm    = new JTextField(10);
    private JTextField campoPorcentaje    = new JTextField(10);
    private JTextField campoCostoFijo     = new JTextField(10);
    private JButton    btnAplicar         = new JButton("Aplicar parámetros");

    public PanelParametros(PlanificadorController controller) {
        setBorder(BorderFactory.createTitledBorder("Parámetros de red"));
        setLayout(new GridLayout(4, 2, 6, 6));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        add(new JLabel("Costo por km ($):"));
        add(campoCostoPorKm);
        add(new JLabel("% extra si > 300 km:"));
        add(campoPorcentaje);
        add(new JLabel("Costo fijo interprovincial ($):"));
        add(campoCostoFijo);
        add(new JLabel(""));
        add(btnAplicar);

        btnAplicar.addActionListener(e -> {
            try {
                controller.actualizarParametros(
                    campoCostoPorKm.getText(),
                    campoPorcentaje.getText(),
                    campoCostoFijo.getText()
                );
                JOptionPane.showMessageDialog(this, "Parámetros guardados.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}