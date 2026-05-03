package Trabajo_Practico2;

import java.awt.EventQueue;
import modelo.PlanificadorModelo;
import controller.PlanificadorController;
import vista.VentanaPrincipal;

public class Mapa {

    public static void main(String[] args) {
    	System.out.println("Arrancando app MVC...");
        EventQueue.invokeLater(() -> {
            PlanificadorModelo modelo         = new PlanificadorModelo();
            PlanificadorController controller = new PlanificadorController(modelo);
            VentanaPrincipal ventana          = new VentanaPrincipal(modelo, controller);
            ventana.setVisible(true);
        });
    }
}