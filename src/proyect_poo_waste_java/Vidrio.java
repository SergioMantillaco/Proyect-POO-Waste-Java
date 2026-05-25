package proyect_poo_waste_java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import java.net.URL;

public class Vidrio extends Residuo {

    private static final Color COLOR_VIDRIO = new Color(100, 220, 140);

    public Vidrio(double posicionx, double posiciony) {
        super(posicionx, posiciony);
        this.velocidadBase = 2.8; 
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

        URL rutaImagen = getClass().getResource("/Imagenes/vidrio.png");
        
        if (rutaImagen != null) {
            ImageIcon spriteVidrio = new ImageIcon(rutaImagen);
            g2d.drawImage(spriteVidrio.getImage(), rx, ry, (int)ancho, (int)alto, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(rx, ry, (int)ancho, (int)alto);
            System.err.println("Error de Recursos: No se localizó /Imagenes/vidrio.png");
        }

        
    }

    @Override
    public String getTipoNombre() { 
        return "VIDRIO"; 
    }

    @Override
    public Color getColor() { 
        return COLOR_VIDRIO; 
    }
}