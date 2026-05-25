package proyect_poo_waste_java;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URL;

/**
 * Subclase concreta de Residuo que representa PAPEL.
 * Aplica HERENCIA y POLIMORFISMO (sobreescritura de dibujar y caer).
 */
public class Papel extends Residuo {

    private static final Color COLOR_PAPEL = new Color(255, 230, 100);

    public Papel(double posicionx, double posiciony) {
        super(posicionx, posiciony);
        this.velocidadBase = 2.0; // Papel es mas liviano, cae mas lento
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        // Polimorfismo: el papel cae con una ligera oscilacion lateral
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
        this.posicionx += Math.sin(this.posiciony * 0.05) * 0.5; // efecto hoja
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int rx = (int) posicionx;
        int ry = (int) posiciony;

        // Carga y desacoplamiento del componente visual (Sprite)
        URL rutaImagen = getClass().getResource("/Imagenes/papel.png");
        
        if (rutaImagen != null) {
            ImageIcon spritePapel = new ImageIcon(rutaImagen);
            g2d.drawImage(spritePapel.getImage(), rx, ry, (int)ancho, (int)alto, null);
        } else {
            // Mecanismo de contingencia visual ante ausencia del recurso en memoria
            g2d.setColor(Color.RED);
            g2d.fillRect(rx, ry, (int)ancho, (int)alto);
            System.err.println("Error de Recursos: No se localizó /Imagenes/papel.png");
        }

        // Renderizado de la etiqueta de texto flotante (HUD integrado)
       // dibujarLabel(g2d, "PAPEL", COLOR_PAPEL);
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
