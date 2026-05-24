package proyect_poo_waste_java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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

        // Sombra
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillRoundRect((int)posicionx + 3, (int)posiciony + 3, (int)ancho, (int)alto, 4, 4);

        // Cuerpo del papel
        g2d.setColor(COLOR_PAPEL);
        g2d.fillRoundRect((int)posicionx, (int)posiciony, (int)ancho, (int)alto, 4, 4);

        // Borde
        g2d.setColor(new Color(200, 170, 50));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect((int)posicionx, (int)posiciony, (int)ancho, (int)alto, 4, 4);

        // Lineas de texto simuladas
        g2d.setColor(new Color(180, 140, 30));
        g2d.setStroke(new BasicStroke(1f));
        for (int i = 0; i < 4; i++) {
            int ly = (int)posiciony + 8 + i * 7;
            g2d.drawLine((int)posicionx + 5, ly, (int)posicionx + (int)ancho - 5, ly);
        }

        dibujarLabel(g2d, "PAPEL", COLOR_PAPEL);
    }

    @Override
    public String getTipoNombre() { return "PAPEL"; }

    @Override
    public Color getColor() { return COLOR_PAPEL; }
}
