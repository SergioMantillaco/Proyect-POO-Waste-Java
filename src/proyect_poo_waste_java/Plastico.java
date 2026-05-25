package proyect_poo_waste_java;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.net.URL;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Subclase concreta de Residuo que representa PLASTICO.
 * Aplica HERENCIA y POLIMORFISMO.
 */
public class Plastico extends Residuo {

    private static final Color COLOR_PLASTICO = new Color(80, 180, 255);

    public Plastico(double posicionx, double posiciony) {
        super(posicionx, posiciony);
        this.velocidadBase = 2.4;
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int rx = (int) posicionx;
        int ry = (int) posiciony;

        // Carga y desacoplamiento del componente visual (Sprite)
        URL rutaImagen = getClass().getResource("/Imagenes/PLASTICO.png");
        
        if (rutaImagen != null) {
            ImageIcon spritePlastico = new ImageIcon(rutaImagen);
            g2d.drawImage(spritePlastico.getImage(), rx, ry, (int)ancho, (int)alto, null);
        } else {
            // Mecanismo de contingencia visual ante ausencia del recurso en memoria
            g2d.setColor(Color.RED);
            g2d.fillRect(rx, ry, (int)ancho, (int)alto);
            System.err.println("Error de Recursos: No se localizó /Imagenes/PLASTICO.png");
        }

        // Renderizado de la etiqueta de texto flotante (HUD integrado)
        //dibujarLabel(g2d, "PLASTICO", COLOR_PLASTICO);
    }

    @Override
    public String getTipoNombre() { 
        return "PLASTICO"; 
    }

    @Override
    public Color getColor() { 
        return COLOR_PLASTICO; 
    }
}