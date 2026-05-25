package proyect_poo_waste_java;

import javax.swing.*;
public class JuegoPrincipal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("WasteCat - El reto de la Economia Circular");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setResizable(false);

            PantallaJuego pantallaJuego = new PantallaJuego();
            ventana.add(pantallaJuego);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            pantallaJuego.requestFocusInWindow();
        });
    }
}
