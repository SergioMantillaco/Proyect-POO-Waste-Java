package proyect_poo_waste_java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Subclase concreta de Residuo que representa VIDRIO.
 * Aplica HERENCIA y POLIMORFISMO.
 */
public class Vidrio extends Residuo {

    private static final Color COLOR_VIDRIO = new Color(100, 220, 140);

    public Vidrio(double posicionx, double posiciony) {
        super(posicionx, posiciony);
        this.velocidadBase = 2.8; // El vidrio es mas pesado, cae mas rapido
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }

    @Override
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sombra
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillRoundRect((int)posicionx + 3, (int)posiciony + 3, (int)ancho - 4, (int)alto, 12, 12);

        // Cuerpo botella de vidrio con transparencia
        GradientPaint gp = new GradientPaint(
            (float)posicionx, (float)posiciony, new Color(140, 230, 160, 220),
            (float)(posicionx + ancho), (float)(posiciony + alto), new Color(50, 150, 80, 200)
        );
        g2d.setPaint(gp);
        g2d.fillRoundRect((int)posicionx, (int)posiciony + 5, (int)ancho - 6, (int)alto - 5, 12, 12);

        // Cuello de botella
        g2d.setColor(new Color(80, 180, 100, 220));
        g2d.fillRoundRect((int)posicionx + 9, (int)posiciony, (int)ancho - 24, 10, 4, 4);

        // Tapa metalica
        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRoundRect((int)posicionx + 8, (int)posiciony - 4, (int)ancho - 22, 7, 3, 3);

        // Brillo vidrio (efecto transparencia)
        g2d.setColor(new Color(255, 255, 255, 80));
        g2d.fillRoundRect((int)posicionx + 4, (int)posiciony + 10, 5, 18, 3, 3);

        // Borde
        g2d.setColor(new Color(60, 160, 80));
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.drawRoundRect((int)posicionx, (int)posiciony + 5, (int)ancho - 6, (int)alto - 5, 12, 12);

        dibujarLabel(g2d, "VIDRIO", COLOR_VIDRIO);
    }

    @Override
    public String getTipoNombre() { return "VIDRIO"; }

    @Override
    public Color getColor() { return COLOR_VIDRIO; }
}
