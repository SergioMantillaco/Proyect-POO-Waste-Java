package proyect_poo_waste_java;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URL;


public class Papel extends Residuo {

    private static final Color COLOR_PAPEL = new Color(255, 230, 100);

    public Papel(double posicionx, double posiciony) {
        super(posicionx, posiciony);
        this.velocidadBase = 2.0; 
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
        this.posicionx += Math.sin(this.posiciony * 0.05) * 0.5; // efecto hoja
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int rx = (int) posicionx;
        int ry = (int) posiciony;

        URL rutaImagen = getClass().getResource("/Imagenes/papel.png");
        
        if (rutaImagen != null) {
            ImageIcon spritePapel = new ImageIcon(rutaImagen);
            g2d.drawImage(spritePapel.getImage(), rx, ry, (int)ancho, (int)alto, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(rx, ry, (int)ancho, (int)alto);
            System.err.println("Error de Recursos: No se localizó /Imagenes/papel.png");
        }

    }

    @Override
    public String getTipoNombre() { 
        return "PAPEL"; 
    }

    @Override
    public Color getColor() { 
        return COLOR_PAPEL; 
    }
}
