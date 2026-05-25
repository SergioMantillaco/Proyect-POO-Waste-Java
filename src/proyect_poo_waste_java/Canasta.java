package proyect_poo_waste_java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.ImageIcon;

public class Canasta {

    private double posicionx;
    private double posiciony;
    private final double ancho = 100;
    private final double alto = 80;
    
    private final double velocidadMovimiento = 7.0;
    private final int anchoPantalla;

    private int frameAnimacion = 0;
    private int contadorFrame = 0;

    private String tipoObjetivo = "PAPEL";
    private Color colorObjetivo = new Color(255, 230, 100);


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

        URL rutaImagen = getClass().getResource("/Imagenes/verde.png");
        
        if (rutaImagen != null) {
            ImageIcon spriteJugador = new ImageIcon(rutaImagen);
            g2d.drawImage(spriteJugador.getImage(), bx, by, (int)ancho, (int)alto, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(bx, by, (int)ancho, (int)alto);
            System.err.println("Error de carga de texturas: No se localizó /Imagenes/verde.png");
        }

        g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2d.setColor(colorObjetivo);
        String label = "ATRAPAR: " + tipoObjetivo;
        int lw = g2d.getFontMetrics().stringWidth(label);
        
        g2d.drawString(label, bx + (int)(ancho / 2) - lw / 2, by - 10);
    }

    public double getX() { 
        return posicionx; 
    }
    
    public double getY() { 
        return posiciony; 
    }
    
    public double getAncho() { 
        return ancho; 
    }
    
    public double getAlto() { 
        return alto; 
    } 

        public void setTipoObjetivo(String tipo, Color color) {
        this.tipoObjetivo = tipo;
        this.colorObjetivo = color;
    }

    public String getTipoObjetivo() { 
        return tipoObjetivo; 
    }
}