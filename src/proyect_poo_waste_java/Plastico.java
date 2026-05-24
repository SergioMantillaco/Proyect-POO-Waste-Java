package proyect_poo_waste_java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
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

        // Sombra
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillOval((int)posicionx + 3, (int)posiciony + 3, (int)ancho, (int)alto);

        // Cuerpo (botella plastica - forma ovalada con gradiente)
        GradientPaint gp = new GradientPaint(
            (float)posicionx, (float)posiciony, new Color(120, 210, 255),
            (float)(posicionx + ancho), (float)(posiciony + alto), new Color(40, 120, 200)
        );
        g2d.setPaint(gp);
        g2d.fillRoundRect((int)posicionx, (int)posiciony, (int)ancho - 6, (int)alto, 10, 10);

        // Cuello de botella
        g2d.setColor(new Color(60, 160, 230));
        g2d.fillRoundRect((int)posicionx + 8, (int)posiciony - 7, (int)ancho - 22, 10, 4, 4);

        // Tapita
        g2d.setColor(new Color(255, 80, 80));
        g2d.fillRoundRect((int)posicionx + 9, (int)posiciony - 10, (int)ancho - 24, 6, 3, 3);

        // Brillo
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawLine((int)posicionx + 5, (int)posiciony + 5, (int)posicionx + 5, (int)posiciony + 20);

        dibujarLabel(g2d, "PLASTICO", COLOR_PLASTICO);
    }

    @Override
    public String getTipoNombre() { return "PLASTICO"; }

    @Override
    public Color getColor() { return COLOR_PLASTICO; }
}
