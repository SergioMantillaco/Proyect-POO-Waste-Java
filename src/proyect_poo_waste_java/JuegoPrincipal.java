package proyect_poo_waste_java;

import javax.swing.*;

/**
 * Clase principal del juego WasteCat.
 * Punto de entrada del programa. Inicializa la ventana y el panel de juego.
 *
 * Conceptos de POO aplicados en el proyecto:
 *  - ABSTRACCION:   Clase Residuo abstracta con metodo dibujar() abstracto.
 *  - HERENCIA:      Papel, Plastico, Vidrio extienden Residuo.
 *  - POLIMORFISMO:  r.caer() y r.dibujar() se comportan diferente segun el tipo real.
 *  - ENCAPSULAMIENTO: Atributos privados/protected con getters y setters.
 *  - ENUMERACIONES: Velocidad_Por_Dificultad y EstadoJuego.
 */
public class JuegoPrincipal {

    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread de Swing (buena practica)
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("WasteCat - El reto de la Economia Circular");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setResizable(false);

            PantallaJuego pantallaJuego = new PantallaJuego();
            ventana.add(pantallaJuego);
            ventana.pack();
            ventana.setLocationRelativeTo(null); // Centrar en pantalla
            ventana.setVisible(true);

            // Dar foco al panel para capturar teclas
            pantallaJuego.requestFocusInWindow();
        });
    }
}
