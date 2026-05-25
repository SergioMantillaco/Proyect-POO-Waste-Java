package proyect_poo_waste_java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Clase que representa la entidad del jugador en el entorno del juego (Avatar/Canasta).
 * Encapsula la lógica de posicionamiento espacial, límites de movimiento y 
 * el motor de renderizado de su sprite principal.
 */
public class Canasta {

    // --- Atributos de Posicionamiento y Dimensiones (AABB) ---
    private double posicionx;
    private double posiciony;
    private final double ancho = 100;
    private final double alto = 80;
    
    // --- Atributos de Físicas y Entorno ---
    private final double velocidadMovimiento = 7.0;
    private final int anchoPantalla;

    // --- Control de Estado de Animación ---
    private int frameAnimacion = 0;
    private int contadorFrame = 0;

    // --- Parámetros de Lógica de Juego (Objetivo) ---
    private String tipoObjetivo = "PAPEL";
    private Color colorObjetivo = new Color(255, 230, 100);

    /**
     * Constructor de la clase Canasta.
     * Inicializa la posición del jugador centrándolo horizontalmente y 
     * fijándolo en el plano inferior de la pantalla.
     * * @param anchoPantalla Límite horizontal máximo de la ventana.
     * @param altoPantalla  Límite vertical máximo de la ventana.
     */
    public Canasta(int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.posicionx = anchoPantalla / 2.0 - ancho / 2.0;
        this.posiciony = altoPantalla - 120;
    }

    /**
     * Aplica un vector de movimiento negativo en el eje X.
     * Incorpora validación Math.max para evitar que la entidad salga del área visible izquierda.
     */
    public void moverIzquierda() {
        posicionx = Math.max(0, posicionx - velocidadMovimiento);
    }

    /**
     * Aplica un vector de movimiento positivo en el eje X.
     * Incorpora validación Math.min para respetar la colisión con el límite derecho de la ventana.
     */
    public void moverDerecha() {
        posicionx = Math.min(anchoPantalla - ancho, posicionx + velocidadMovimiento);
    }

    /**
     * Actualiza el estado lógico de la entidad por cada frame del Game Loop.
     * Gestiona el temporizador para la futura interpolación de frames de animación.
     */
    public void actualizar() {
        contadorFrame++;
        if (contadorFrame >= 8) {
            contadorFrame = 0;
            frameAnimacion = (frameAnimacion + 1) % 2;
        }
    }

    /**
     * Renderiza el sprite del jugador y el HUD dinámico asociado a la entidad.
     * Utiliza algoritmos de Antialiasing para suavizar la tipografía.
     * * @param g2d Contexto gráfico 2D inyectado por el motor de renderizado principal.
     */
    public void dibujar(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int bx = (int) posicionx;
        int by = (int) posiciony;

        // Renderizado del Asset Gráfico (Sprite)
        URL rutaImagen = getClass().getResource("/Imagenes/verde.png");
        
        if (rutaImagen != null) {
            ImageIcon spriteJugador = new ImageIcon(rutaImagen);
            g2d.drawImage(spriteJugador.getImage(), bx, by, (int)ancho, (int)alto, null);
        } else {
            // Fallback de renderizado en caso de ausencia de recursos en memoria
            g2d.setColor(Color.RED);
            g2d.fillRect(bx, by, (int)ancho, (int)alto);
            System.err.println("Error de carga de texturas: No se localizó /Imagenes/verde.png");
        }

        // Renderizado del Indicador de Objetivo (HUD Integrado)
        g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2d.setColor(colorObjetivo);
        String label = "ATRAPAR: " + tipoObjetivo;
        int lw = g2d.getFontMetrics().stringWidth(label);
        
        // Posicionamiento dinámico del texto sobre el sprite
        g2d.drawString(label, bx + (int)(ancho / 2) - lw / 2, by - 10);
    }

    // =====================================================================
    //  MÉTODOS ACCESORES (GETTERS & SETTERS)
    // =====================================================================

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

    /**
     * Actualiza dinámicamente la directriz de recolección para el jugador.
     * * @param tipo  Nombre descriptivo del residuo objetivo.
     * @param color Color hexadecimal para el feedback visual en el HUD.
     */
    public void setTipoObjetivo(String tipo, Color color) {
        this.tipoObjetivo = tipo;
        this.colorObjetivo = color;
    }

    public String getTipoObjetivo() { 
        return tipoObjetivo; 
    }
}