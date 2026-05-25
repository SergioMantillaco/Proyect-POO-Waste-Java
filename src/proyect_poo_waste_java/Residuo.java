package proyect_poo_waste_java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public abstract class Residuo {

    protected double posicionx;
    protected double posiciony;
    protected double ancho  = 38;
    protected double alto   = 38;
    protected double velocidadBase = 2.0;
    protected boolean activo = true;

    public Residuo(double posicionx, double posiciony) {
        this.posicionx = posicionx;
        this.posiciony = posiciony;
    }

    public abstract void dibujar(Graphics2D g2d);

    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }

   
    public abstract String getTipoNombre();

    
    public abstract Color getColor();

    public double getX()       { return posicionx; }
    public double getY()       { return posiciony; }
    public double getAncho()   { return ancho; }
    public double getAlto()    { return alto; }
    public boolean isActivo()  { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }


    public boolean colisionaCon(Canasta canasta) {
        return (posicionx < canasta.getX() + canasta.getAncho()  &&
                posicionx + ancho > canasta.getX()               &&
                posiciony < canasta.getY() + canasta.getAlto()   &&
                posiciony + alto > canasta.getY());
    }

    protected void dibujarLabel(Graphics2D g2d, String texto, Color color) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 9));
        int tx = (int)(posicionx + ancho / 2 - g2d.getFontMetrics().stringWidth(texto) / 2);
        g2d.drawString(texto, tx, (int)(posiciony + alto + 12));
    }
}
