package proyect_poo_waste_java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Clase que representa la canasta del gato (controlada por el jugador).
 * Encapsula la posicion, dimensiones y logica de movimiento.
 */
public class Canasta {

    private double posicionx;
    private double posiciony;
    private final double ancho  = 80;
    private final double alto   = 50;
    private final double velocidadMovimiento = 7.0;
    private final int anchoPantalla;

    // Animacion del gato
    private int frameAnimacion = 0;
    private int contadorFrame  = 0;

    // Tipo objetivo actual (que tipo de residuo debe atrapar)
    private String tipoObjetivo = "PAPEL";
    private Color  colorObjetivo = new Color(255, 230, 100);

    public Canasta(int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.posicionx = anchoPantalla / 2.0 - ancho / 2.0;
        this.posiciony = altoPantalla - 120;
    }

    public void moverIzquierda() {
        posicionx = Math.max(0, posicionx - velocidadMovimiento);
    }

    public void moverDerecha() {
        posicionx = Math.min(anchoPantalla - ancho, posicionx + velocidadMovimiento);
    }

    public void actualizar() {
        contadorFrame++;
        if (contadorFrame >= 8) {
            contadorFrame = 0;
            frameAnimacion = (frameAnimacion + 1) % 2;
        }
    }

    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int bx = (int) posicionx;
        int by = (int) posiciony;

        // ---- Sombra del gato ----
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillOval(bx + 5, by + (int)alto + 32, (int)ancho - 10, 12);

        // ---- Cuerpo del gato ----
        GradientPaint bodyGrad = new GradientPaint(
            bx, by + 20, new Color(240, 230, 200),
            bx, by + 70, new Color(200, 185, 155)
        );
        g2d.setPaint(bodyGrad);
        g2d.fillRoundRect(bx + 5, by + 20, (int)ancho - 10, 50, 20, 20);

        // ---- Patitas ----
        g2d.setColor(new Color(230, 215, 185));
        // Pata izquierda
        int offsetPata = frameAnimacion == 0 ? 0 : 3;
        g2d.fillRoundRect(bx + 8, by + 55 + offsetPata, 16, 20, 8, 8);
        g2d.fillRoundRect(bx + (int)ancho - 24, by + 55 - offsetPata, 16, 20, 8, 8);

        // Garras
        g2d.setColor(new Color(180, 160, 130));
        g2d.setStroke(new BasicStroke(1f));
        for (int i = 0; i < 3; i++) {
            g2d.drawLine(bx + 10 + i*4, by + 72 + offsetPata, bx + 10 + i*4, by + 76 + offsetPata);
            g2d.drawLine(bx + (int)ancho - 22 + i*4, by + 72 - offsetPata, bx + (int)ancho - 22 + i*4, by + 76 - offsetPata);
        }

        // ---- Cola ----
        g2d.setColor(new Color(230, 215, 185));
        g2d.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawArc(bx + (int)ancho - 10, by + 30, 25, 40, -60, 180);

        // ---- Cabeza del gato ----
        GradientPaint headGrad = new GradientPaint(
            bx + 15, by, new Color(245, 235, 210),
            bx + 15, by + 25, new Color(210, 195, 165)
        );
        g2d.setPaint(headGrad);
        g2d.fillOval(bx + 15, by - 5, (int)ancho - 30, 35);

        // Orejas
        g2d.setColor(new Color(230, 215, 185));
        int[] exOreja = {bx + 18, bx + 26, bx + 22};
        int[] eyOreja = {by + 2, by + 2, by - 14};
        g2d.fillPolygon(exOreja, eyOreja, 3);
        int[] dxOreja = {bx + (int)ancho - 28, bx + (int)ancho - 20, bx + (int)ancho - 24};
        int[] dyOreja = {by + 2, by + 2, by - 14};
        g2d.fillPolygon(dxOreja, dyOreja, 3);

        // Interior orejas
        g2d.setColor(new Color(255, 180, 180));
        int[] exOrejaI = {bx + 20, bx + 25, bx + 22};
        int[] eyOrejaI = {by + 1, by + 1, by - 9};
        g2d.fillPolygon(exOrejaI, eyOrejaI, 3);
        int[] dxOrejaI = {bx + (int)ancho - 27, bx + (int)ancho - 22, bx + (int)ancho - 24};
        int[] dyOrejaI = {by + 1, by + 1, by - 9};
        g2d.fillPolygon(dxOrejaI, dyOrejaI, 3);

        // Ojos
        g2d.setColor(Color.WHITE);
        g2d.fillOval(bx + 21, by + 7, 12, 10);
        g2d.fillOval(bx + (int)ancho - 33, by + 7, 12, 10);

        g2d.setColor(new Color(60, 30, 0));
        g2d.fillOval(bx + 24, by + 9, 6, 7);
        g2d.fillOval(bx + (int)ancho - 30, by + 9, 6, 7);

        // Brillo ojos
        g2d.setColor(Color.WHITE);
        g2d.fillOval(bx + 25, by + 10, 2, 2);
        g2d.fillOval(bx + (int)ancho - 29, by + 10, 2, 2);

        // Nariz
        g2d.setColor(new Color(255, 140, 150));
        int[] nx = {bx + 38, bx + 42, bx + 40};
        int[] ny = {by + 17, by + 17, by + 20};
        g2d.fillPolygon(nx, ny, 3);

        // Bigotes
        g2d.setColor(new Color(100, 90, 70));
        g2d.setStroke(new BasicStroke(0.8f));
        g2d.drawLine(bx + 22, by + 19, bx + 36, by + 18);
        g2d.drawLine(bx + 22, by + 22, bx + 36, by + 21);
        g2d.drawLine(bx + 44, by + 18, bx + 58, by + 19);
        g2d.drawLine(bx + 44, by + 21, bx + 58, by + 22);

        // Boca
        g2d.setColor(new Color(180, 100, 100));
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.drawArc(bx + 36, by + 20, 8, 6, 200, 140);

        // ---- CANASTA ----
        drawCanasta(g2d, bx, by);

        // ---- Etiqueta del objetivo actual ----
        g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
        g2d.setColor(colorObjetivo);
        String label = "ATRAPAR: " + tipoObjetivo;
        int lw = g2d.getFontMetrics().stringWidth(label);
        g2d.drawString(label, bx + (int)(ancho/2) - lw/2, by - 18);
    }

    private void drawCanasta(Graphics2D g2d, int bx, int by) {
        // Canasta que sostiene el gato
        int cx = bx - 5;
        int cy = by + 28;
        int cw = (int)ancho + 10;
        int ch = 28;

        // Sombra canasta
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRoundRect(cx + 3, cy + 3, cw, ch, 8, 8);

        // Fondo canasta (color segun objetivo)
        g2d.setColor(new Color(colorObjetivo.getRed(), colorObjetivo.getGreen(), colorObjetivo.getBlue(), 60));
        g2d.fillRoundRect(cx, cy, cw, ch, 8, 8);

        // Borde canasta
        g2d.setColor(colorObjetivo);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawRoundRect(cx, cy, cw, ch, 8, 8);

        // Tejido de canasta (lineas cruzadas)
        g2d.setStroke(new BasicStroke(0.8f));
        g2d.setColor(new Color(colorObjetivo.getRed(), colorObjetivo.getGreen(), colorObjetivo.getBlue(), 100));
        for (int i = 0; i < cw; i += 10) {
            g2d.drawLine(cx + i, cy, cx + i, cy + ch);
        }
        for (int j = 0; j < ch; j += 8) {
            g2d.drawLine(cx, cy + j, cx + cw, cy + j);
        }

        // Simbolo de reciclaje en la canasta
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(colorObjetivo);
        g2d.drawString("R", cx + cw/2 - 5, cy + ch/2 + 5);
    }

    // --- Getters ---
    public double getX()      { return posicionx; }
    public double getY()      { return posiciony; }
    public double getAncho()  { return ancho; }
    public double getAlto()   { return alto + 28; } // incluye la canasta

    public void setTipoObjetivo(String tipo, Color color) {
        this.tipoObjetivo = tipo;
        this.colorObjetivo = color;
    }

    public String getTipoObjetivo() { return tipoObjetivo; }
}
