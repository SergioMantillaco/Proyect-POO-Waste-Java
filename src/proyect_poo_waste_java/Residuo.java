package proyect_poo_waste_java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Clase abstracta base para todos los tipos de residuos.
 * Aplica ABSTRACCION y HERENCIA (clases hijas: Papel, Plastico, Vidrio).
 */
public abstract class Residuo {

    // --- Atributos protegidos (accesibles para subclases) ---
    protected double posicionx;
    protected double posiciony;
    protected double ancho  = 38;
    protected double alto   = 38;
    protected double velocidadBase = 2.0;
    protected boolean activo = true;

    // --- Constructor ---
    public Residuo(double posicionx, double posiciony) {
        this.posicionx = posicionx;
        this.posiciony = posiciony;
    }

    // -------------------------------------------------------
    // Metodo ABSTRACTO: cada subclase define su propio dibujo
    // -------------------------------------------------------
    public abstract void dibujar(Graphics2D g2d);

    /**
     * Metodo de caida polimorfico (puede ser sobreescrito por subclases).
     * Aplica POLIMORFISMO.
     */
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }

    /**
     * Devuelve el nombre del tipo de residuo para mostrar instruccion al jugador.
     */
    public abstract String getTipoNombre();

    /**
     * Devuelve el color representativo del residuo.
     */
    public abstract Color getColor();

    // --- Getters ---
    public double getX()       { return posicionx; }
    public double getY()       { return posiciony; }
    public double getAncho()   { return ancho; }
    public double getAlto()    { return alto; }
    public boolean isActivo()  { return activo; }

    // --- Setter ---
    public void setActivo(boolean activo) { this.activo = activo; }

    /**
     * Calcula si este residuo colisiono con la canasta del jugador.
     * Logica de AABB (Axis-Aligned Bounding Box).
     */
    public boolean colisionaCon(Canasta canasta) {
        return (posicionx < canasta.getX() + canasta.getAncho()  &&
                posicionx + ancho > canasta.getX()               &&
                posiciony < canasta.getY() + canasta.getAlto()   &&
                posiciony + alto > canasta.getY());
    }

    /**
     * Dibuja el label del tipo sobre el residuo (helper compartido).
     */
    protected void dibujarLabel(Graphics2D g2d, String texto, Color color) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 9));
        int tx = (int)(posicionx + ancho / 2 - g2d.getFontMetrics().stringWidth(texto) / 2);
        g2d.drawString(texto, tx, (int)(posiciony + alto + 12));
    }
}
