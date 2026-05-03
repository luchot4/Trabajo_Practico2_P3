package Trabajo_Practico2;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

public class Mapa implements Observador{

	private JFrame frame;
	private JMapViewer mapa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	       EventQueue.invokeLater(() -> {
	            try {
	                Modelo modelo = new Modelo();
	                Controlador controlador = new Controlador(modelo);

	                Mapa window = new Mapa();
	                modelo.addObserver(window); // Vista se suscribe al Modelo

	                window.frame.setVisible(true);

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    }

	/**
	 * Create the application.
	 */
	public Mapa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JMapViewer");
		
		
		mapa = new JMapViewer();
		frame.getContentPane().add(mapa);
	}

	@Override
	public void actualizar(String mensaje) {
		System.out.println("[Mapa] " + mensaje);
		
	}
	
	
}
