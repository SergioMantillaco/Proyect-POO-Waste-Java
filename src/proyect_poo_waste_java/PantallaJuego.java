package proyect_poo_waste_java;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * Panel principal del juego. Maneja el Game Loop y el renderizado.
 * Integra todas las clases del sistema bajo el patron MVC simplificado.
 */
public class PantallaJuego extends JPanel implements ActionListener, KeyListener {

    // --- Constantes ---
    private static final int ANCHO  = 700;
    private static final int ALTO   = 600;
    private static final int FPS    = 60;

    // --- Estado del juego ---
    private EstadoJuego estadoActual = EstadoJuego.MENU;

    // --- Objetos de juego ---
    private Jugador        jugador;
    private Canasta        canasta;
    private GestorResiduos gestor;

    // --- Control de teclas ---
    private boolean teclaIzquierda = false;
    private boolean teclaDerecha   = false;

    // --- Timer del game loop ---
    private Timer gameLoop;

    // --- Input del menu ---
    private String inputAlias = "";
    private boolean cursorVisible = true;
    private int     contadorCursor = 0;
    private int     dificultadSeleccionada = 0; // 0=FACIL, 1=MEDIO, 2=DIFICIL
    

    // --- Feedback visual ---
    private String  mensajeFeedback    = "";
    private Color   colorFeedback      = Color.WHITE;
    private int     contadorFeedback   = 0;
    private boolean mostrarFeedback    = false;

    // --- Partículas de efecto ---
    private List<Particula> particulas = new ArrayList<>();

    // --- Colores del tema ---
    private static final Color COLOR_BG_DARK    = new Color(10, 18, 30);
    private static final Color COLOR_BG_MID     = new Color(15, 28, 45);
    private static final Color COLOR_VERDE       = new Color(60, 220, 120);
    private static final Color COLOR_VERDE_OSC   = new Color(30, 140, 70);
    private static final Color COLOR_ROJO        = new Color(255, 80, 80);
    private static final Color COLOR_AMARILLO    = new Color(255, 220, 60);
    private static final Color COLOR_ACENTO      = new Color(80, 180, 255);

    // Clase interna para particulas de efecto
    private static class Particula {
        double x, y, vx, vy;
        Color  color;
        int    vida, maxVida;

        Particula(double x, double y, Color color) {
            this.x    = x; this.y = y;
            this.vx   = (Math.random() - 0.5) * 5;
            this.vy   = (Math.random() - 1.5) * 4;
            this.color   = color;
            this.vida    = 0;
            this.maxVida = 40 + (int)(Math.random() * 20);
        }

        void actualizar() { x += vx; y += vy; vy += 0.15; vida++; }
        boolean estaViva() { return vida < maxVida; }
        float   alpha()    { return 1f - (float)vida / maxVida; }
    }

    // --- Constructor ---
    public PantallaJuego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(COLOR_BG_DARK);
        setFocusable(true);
        addKeyListener(this);

        gameLoop = new Timer(1000 / FPS, this);
        gameLoop.start();
    }

    // =====================================================================
    //  GAME LOOP
    // =====================================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        actualizar();
        repaint();
    }

    private void actualizar() {
        // Parpadeo cursor en menu
        contadorCursor++;
        if (contadorCursor >= 30) { contadorCursor = 0; cursorVisible = !cursorVisible; }

        if (estadoActual == EstadoJuego.JUGANDO) {
            // Mover canasta
            if (teclaIzquierda) canasta.moverIzquierda();
            if (teclaDerecha)   canasta.moverDerecha();
            canasta.actualizar();

            // Actualizar residuos
            gestor.actualizar(ALTO);

            // Actualizar objetivo en canasta
            Residuo objetivo = gestor.obtenerObjetivoActual();
            if (objetivo != null) {
                canasta.setTipoObjetivo(objetivo.getTipoNombre(), objetivo.getColor());
            }

            // Verificar colisiones
            List<Residuo> aEliminar = new ArrayList<>();
            for (Residuo r : gestor.getResiduosActivos()) {
                if (!r.isActivo()) {
                    aEliminar.add(r);
                    continue;
                }

                // Colision con canasta
                if (r.colisionaCon(canasta)) {
                    // Atrapo el residuo que le indicaban?
                    if (r.getTipoNombre().equals(canasta.getTipoObjetivo())) {
                        jugador.sumarPuntos(10);
                        mostrarMensaje("+10 PUNTOS!", COLOR_VERDE);
                        generarParticulas((int)r.getX(), (int)r.getY(), COLOR_VERDE, 12);
                    } else {
                        // Atrapo el tipo equivocado: no le suma pero tampoco le resta
                        mostrarMensaje("Tipo incorrecto...", COLOR_AMARILLO);
                        generarParticulas((int)r.getX(), (int)r.getY(), COLOR_AMARILLO, 6);
                    }
                    r.setActivo(false);
                    aEliminar.add(r);
                }

                // Residuo objetivo cayo al suelo sin ser atrapado
                if (r.getY() > ALTO - 40 && !r.isActivo() == false) {
                    if (r.getTipoNombre().equals(canasta.getTipoObjetivo())) {
                        jugador.perderVida();
                        mostrarMensaje("-1 VIDA!", COLOR_ROJO);
                        generarParticulas(ANCHO / 2, ALTO - 60, COLOR_ROJO, 15);
                    }
                }
            }
            gestor.getResiduosActivos().removeAll(aEliminar);

            // Verificar game over
            if (!jugador.estaVivo()) {
                jugador.guardarEnRanking();
                estadoActual = EstadoJuego.GAME_OVER;
            }

            // Actualizar feedback
            if (mostrarFeedback) {
                contadorFeedback++;
                if (contadorFeedback > 60) { mostrarFeedback = false; contadorFeedback = 0; }
            }
        }

        // Actualizar particulas
        particulas.removeIf(p -> !p.estaViva());
        for (Particula p : particulas) p.actualizar();
    }

    // =====================================================================
    //  RENDERIZADO
    // =====================================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        switch (estadoActual) {
            case MENU:      dibujarMenu(g2d);    break;
            case JUGANDO:   dibujarJuego(g2d);   break;
            case GAME_OVER: dibujarGameOver(g2d); break;
        }
    }

    // -------------------------------------------------------
    // PANTALLA: MENU
    // -------------------------------------------------------
    private void dibujarMenu(Graphics2D g2d) {
        // Fondo con gradiente
        GradientPaint bgGrad = new GradientPaint(0, 0, COLOR_BG_DARK, 0, ALTO, new Color(5, 40, 20));
        g2d.setPaint(bgGrad);
        g2d.fillRect(0, 0, ANCHO, ALTO);

        // Estrellas de fondo
        dibujarEstrellas(g2d);

        // Panel central
        int pw = 380, ph = 320;
        int px = ANCHO/2 - pw/2, py = ALTO/2 - ph/2 - 20;

        g2d.setColor(new Color(255, 255, 255, 8));
        g2d.fill(new RoundRectangle2D.Double(px, py, pw, ph, 20, 20));
        g2d.setColor(COLOR_VERDE_OSC);
        g2d.setStroke(new BasicStroke(2f));
        g2d.draw(new RoundRectangle2D.Double(px, py, pw, ph, 20, 20));

        // Titulo WasteCat
        g2d.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 52));
        String titulo = "WasteCat";
        int tw = g2d.getFontMetrics().stringWidth(titulo);
        // Sombra del titulo
        g2d.setColor(new Color(30, 120, 60, 100));
        g2d.drawString(titulo, ANCHO/2 - tw/2 + 3, py + 68);
        // Titulo con gradiente
        GradientPaint titleGrad = new GradientPaint(
            ANCHO/2 - tw/2, py + 20, COLOR_VERDE,
            ANCHO/2 + tw/2, py + 70, new Color(160, 255, 200)
        );
        g2d.setPaint(titleGrad);
        g2d.drawString(titulo, ANCHO/2 - tw/2, py + 65);

        // Subtitulo
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 13));
        g2d.setColor(new Color(150, 200, 160));
        String sub = "El reto de la Economia Circular";
        g2d.drawString(sub, ANCHO/2 - g2d.getFontMetrics().stringWidth(sub)/2, py + 90);

        // Separador
        g2d.setColor(new Color(60, 180, 80, 80));
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawLine(px + 30, py + 105, px + pw - 30, py + 105);

        // Label alias
        g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
        g2d.setColor(new Color(180, 220, 190));
        String labelAlias = "INGRESE SU ALIAS (e.g., EcoPlayer):";
        g2d.drawString(labelAlias, ANCHO/2 - g2d.getFontMetrics().stringWidth(labelAlias)/2, py + 135);

        // Campo de texto
        int fx = px + 40, fy = py + 148, fw = pw - 80, fh = 36;
        g2d.setColor(new Color(255, 255, 255, 15));
        g2d.fillRoundRect(fx, fy, fw, fh, 8, 8);
        g2d.setColor(COLOR_VERDE_OSC);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(fx, fy, fw, fh, 8, 8);

        g2d.setFont(new Font("Monospaced", Font.PLAIN, 16));
        g2d.setColor(Color.WHITE);
        String displayAlias = inputAlias + (cursorVisible ? "|" : " ");
        g2d.drawString(displayAlias, fx + 10, fy + 24);

        // Selector de dificultad
        g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
        g2d.setColor(new Color(150, 200, 160));
        g2d.drawString("DIFICULTAD (Usa <- y -> para cambiar):", ANCHO/2 - 140, py + 215);

        String[] difs = {"FACIL", "MEDIO", "DIFICIL"};
        Color[]  cols = {COLOR_VERDE, COLOR_AMARILLO, COLOR_ROJO};
        
        for (int i = 0; i < difs.length; i++) {
            int bx = px + 40 + i * 100, by2 = py + 220, bw2 = 85, bh2 = 26;
            
            if (i == dificultadSeleccionada) {
                g2d.setColor(new Color(cols[i].getRed(), cols[i].getGreen(), cols[i].getBlue(), 180));
            } else {
                g2d.setColor(new Color(cols[i].getRed(), cols[i].getGreen(), cols[i].getBlue(), 30));
            }
            
            g2d.fillRoundRect(bx, by2, bw2, bh2, 6, 6);
            g2d.setColor(cols[i]);
            g2d.setStroke(new BasicStroke(i == dificultadSeleccionada ? 2.5f : 1.5f));
            g2d.drawRoundRect(bx, by2, bw2, bh2, 6, 6);
            
            // Texto del botón
            g2d.setColor(i == dificultadSeleccionada ? Color.WHITE : cols[i]);
            g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
            int tw2 = g2d.getFontMetrics().stringWidth(difs[i]);
            g2d.drawString(difs[i], bx + bw2/2 - tw2/2, by2 + 17);
        }

        // Boton JUGAR
        int btnX = px + 70, btnY = py + 260, btnW = pw - 140, btnH = 42;
        GradientPaint btnGrad = new GradientPaint(btnX, btnY, COLOR_VERDE_OSC, btnX, btnY + btnH, new Color(20, 100, 50));
        g2d.setPaint(btnGrad);
        g2d.fillRoundRect(btnX, btnY, btnW, btnH, 10, 10);
        g2d.setColor(COLOR_VERDE);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(btnX, btnY, btnW, btnH, 10, 10);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        String btnTxt = "JUGAR PARTIDA";
        g2d.drawString(btnTxt, ANCHO/2 - g2d.getFontMetrics().stringWidth(btnTxt)/2, btnY + 27);

        // Instrucciones
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g2d.setColor(new Color(100, 140, 110));
        g2d.drawString("Escribe tu alias y presiona ENTER para jugar", ANCHO/2 - 140, ALTO - 25);
        g2d.drawString("Usa las flechas <- -> para mover la canasta", ANCHO/2 - 135, ALTO - 12);
    }

    // -------------------------------------------------------
    // PANTALLA: JUEGO ACTIVO
    // -------------------------------------------------------
    private void dibujarJuego(Graphics2D g2d) {
        // Fondo
        GradientPaint bgGrad = new GradientPaint(0, 0, COLOR_BG_DARK, 0, ALTO, new Color(5, 20, 40));
        g2d.setPaint(bgGrad);
        g2d.fillRect(0, 0, ANCHO, ALTO);

        // Cuadricula de fondo sutil
        g2d.setColor(new Color(255, 255, 255, 5));
        g2d.setStroke(new BasicStroke(0.5f));
        for (int x = 0; x < ANCHO; x += 40) g2d.drawLine(x, 0, x, ALTO);
        for (int y = 0; y < ALTO; y += 40) g2d.drawLine(0, y, ANCHO, y);

        // Dibujar residuos usando polimorfismo
        for (Residuo r : gestor.getResiduosActivos()) {
            if (r.isActivo()) {
                r.dibujar(g2d); // POLIMORFISMO: cada tipo se dibuja diferente
            }
        }

        // Dibujar canasta + gato
        canasta.dibujar(g2d);

        // Dibujar particulas
        for (Particula p : particulas) {
            g2d.setColor(new Color(
                p.color.getRed(), p.color.getGreen(), p.color.getBlue(),
                (int)(p.alpha() * 220)
            ));
            g2d.fillOval((int)p.x - 3, (int)p.y - 3, 6, 6);
        }

        // HUD - Header
        dibujarHUD(g2d);

        // Feedback flotante
        if (mostrarFeedback) {
            float alpha = contadorFeedback < 20 ? contadorFeedback / 20f :
                          contadorFeedback > 45 ? 1f - (contadorFeedback - 45) / 15f : 1f;
            g2d.setColor(new Color(
                colorFeedback.getRed(), colorFeedback.getGreen(), colorFeedback.getBlue(),
                (int)(alpha * 255)
            ));
            g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
            int mw = g2d.getFontMetrics().stringWidth(mensajeFeedback);
            g2d.drawString(mensajeFeedback, ANCHO/2 - mw/2, ALTO/2 - 40);
        }

        // Linea del suelo
        g2d.setColor(new Color(255, 80, 80, 80));
        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{8, 4}, 0));
        g2d.drawLine(0, ALTO - 35, ANCHO, ALTO - 35);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
        g2d.setColor(new Color(255, 80, 80, 100));
        g2d.drawString("ZONA DE PELIGRO", 10, ALTO - 22);
    }

    private void dibujarHUD(Graphics2D g2d) {
        // Barra superior
        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRect(0, 0, ANCHO, 55);
        g2d.setColor(new Color(60, 200, 100, 60));
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawLine(0, 55, ANCHO, 55);

        // Puntaje
        g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
        g2d.setColor(new Color(150, 200, 160));
        g2d.drawString("PUNTAJE", 20, 18);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 26));
        GradientPaint scoreGrad = new GradientPaint(20, 20, COLOR_VERDE, 20, 48, new Color(100, 255, 150));
        g2d.setPaint(scoreGrad);
        g2d.drawString(String.valueOf(jugador.getPuntaje()), 20, 44);

        // Nombre jugador (centro)
        g2d.setFont(new Font("Monospaced", Font.BOLD, 13));
        g2d.setColor(new Color(180, 220, 200));
        String nombre = jugador.getNombreJugador();
        g2d.drawString(nombre, ANCHO/2 - g2d.getFontMetrics().stringWidth(nombre)/2, 32);

        // Dificultad
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
        g2d.setColor(new Color(120, 160, 130));
        String dif = "DIF: " + gestor.getDificultad().name();
        g2d.drawString(dif, ANCHO/2 - g2d.getFontMetrics().stringWidth(dif)/2, 46);

        // Vidas (corazones)
        g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
        g2d.setColor(new Color(150, 200, 160));
        g2d.drawString("VIDAS", ANCHO - 100, 18);
        for (int i = 0; i < 3; i++) {
            Color hColor = i < jugador.getVidas() ? COLOR_ROJO : new Color(80, 80, 80);
            dibujarCorazon(g2d, ANCHO - 95 + i * 28, 22, 18, hColor);
        }
    }

    private void dibujarCorazon(Graphics2D g2d, int x, int y, int size, Color color) {
        g2d.setColor(color);
        g2d.fillOval(x, y, size/2, size/2);
        g2d.fillOval(x + size/2, y, size/2, size/2);
        int[] xp = {x, x + size/2, x + size};
        int[] yp = {y + size/3, y + size, y + size/3};
        g2d.fillPolygon(xp, yp, 3);
    }

    // -------------------------------------------------------
    // PANTALLA: GAME OVER / RANKING
    // -------------------------------------------------------
    private void dibujarGameOver(Graphics2D g2d) {
        GradientPaint bgGrad = new GradientPaint(0, 0, new Color(30, 5, 5), 0, ALTO, COLOR_BG_DARK);
        g2d.setPaint(bgGrad);
        g2d.fillRect(0, 0, ANCHO, ALTO);

        dibujarEstrellas(g2d);

        // Titulo game over
        g2d.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 62));
        String go = "GAME OVER";
        int gw = g2d.getFontMetrics().stringWidth(go);
        // Sombra
        g2d.setColor(new Color(150, 0, 0, 80));
        g2d.drawString(go, ANCHO/2 - gw/2 + 4, 90);
        // Texto
        GradientPaint goGrad = new GradientPaint(ANCHO/2 - gw/2, 40, COLOR_ROJO, ANCHO/2 + gw/2, 90, new Color(255, 150, 150));
        g2d.setPaint(goGrad);
        g2d.drawString(go, ANCHO/2 - gw/2, 86);

        // Puntaje final del jugador
        g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
        g2d.setColor(new Color(200, 200, 200));
        String resumen = jugador.getNombreJugador() + " - Puntaje Final: " + jugador.getPuntaje();
        g2d.drawString(resumen, ANCHO/2 - g2d.getFontMetrics().stringWidth(resumen)/2, 120);

        // Panel de ranking
        int pw = 420, ph = 310;
        int px = ANCHO/2 - pw/2, py = 140;

        g2d.setColor(new Color(255, 255, 255, 8));
        g2d.fill(new RoundRectangle2D.Double(px, py, pw, ph, 16, 16));
        g2d.setColor(new Color(180, 60, 60, 120));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(new RoundRectangle2D.Double(px, py, pw, ph, 16, 16));

        // Titulo ranking
        g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
        GradientPaint rankGrad = new GradientPaint(px, py + 20, COLOR_AMARILLO, px + pw, py + 40, new Color(255, 180, 60));
        g2d.setPaint(rankGrad);
        String rankTit = "RANKING GLOBAL";
        g2d.drawString(rankTit, ANCHO/2 - g2d.getFontMetrics().stringWidth(rankTit)/2, py + 28);

        // Separador
        g2d.setColor(new Color(180, 140, 40, 80));
        g2d.drawLine(px + 20, py + 36, px + pw - 20, py + 36);

        // Headers tabla
        g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString("POS", px + 20, py + 56);
        g2d.drawString("ALIAS", px + 80, py + 56);
        g2d.drawString("PUNTAJE", px + pw - 100, py + 56);
        g2d.drawLine(px + 20, py + 62, px + pw - 20, py + 62);

        // Filas de ranking
        List<Jugador.EntradaRanking> ranking = Jugador.getRankingGlobal();
        Color[] posColors = {COLOR_AMARILLO, new Color(200, 200, 200), new Color(180, 120, 60), Color.WHITE, Color.WHITE};

        for (int i = 0; i < Math.min(5, ranking.size()); i++) {
            Jugador.EntradaRanking entrada = ranking.get(i);
            int rowY = py + 82 + i * 42;

            // Resaltar la entrada del jugador actual
            if (entrada.nombre.equals(jugador.getNombreJugador()) && entrada.puntaje == jugador.getPuntaje()) {
                g2d.setColor(new Color(60, 200, 100, 25));
                g2d.fillRoundRect(px + 15, rowY - 18, pw - 30, 34, 6, 6);
                g2d.setColor(COLOR_VERDE);
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(px + 15, rowY - 18, pw - 30, 34, 6, 6);
            }

            g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
            g2d.setColor(i < posColors.length ? posColors[i] : Color.WHITE);
            g2d.drawString((i + 1) + ".", px + 22, rowY);

            g2d.setFont(new Font("Monospaced", Font.PLAIN, 13));
            g2d.setColor(Color.WHITE);
            g2d.drawString(entrada.nombre, px + 80, rowY);

            g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
            g2d.setColor(COLOR_ACENTO);
            String pts = String.valueOf(entrada.puntaje);
            g2d.drawString(pts, px + pw - 100 + (50 - g2d.getFontMetrics().stringWidth(pts))/2, rowY);
        }

        if (ranking.isEmpty()) {
            g2d.setFont(new Font("Monospaced", Font.ITALIC, 13));
            g2d.setColor(new Color(120, 120, 120));
            g2d.drawString("Sin registros aun", ANCHO/2 - 70, py + 100);
        }

        // Boton volver al menu
        int btnX = ANCHO/2 - 110, btnY = py + ph + 20, btnW = 220, btnH = 44;
        GradientPaint btnGrad = new GradientPaint(btnX, btnY, new Color(60, 20, 20), btnX, btnY + btnH, new Color(120, 30, 30));
        g2d.setPaint(btnGrad);
        g2d.fillRoundRect(btnX, btnY, btnW, btnH, 10, 10);
        g2d.setColor(COLOR_ROJO);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(btnX, btnY, btnW, btnH, 10, 10);
        g2d.setFont(new Font("Monospaced", Font.BOLD, 15));
        g2d.setColor(Color.WHITE);
        String btnTxt = "VOLVER AL MENU";
        g2d.drawString(btnTxt, ANCHO/2 - g2d.getFontMetrics().stringWidth(btnTxt)/2, btnY + 28);
    }

    // -------------------------------------------------------
    // HELPER: Estrellas decorativas de fondo
    // -------------------------------------------------------
    private void dibujarEstrellas(Graphics2D g2d) {
        Random rng = new Random(42);
        for (int i = 0; i < 80; i++) {
            int sx = rng.nextInt(ANCHO), sy = rng.nextInt(ALTO);
            int br = 80 + rng.nextInt(120);
            g2d.setColor(new Color(br, br, br, 80 + rng.nextInt(100)));
            g2d.fillOval(sx, sy, rng.nextInt(2) + 1, rng.nextInt(2) + 1);
        }
    }

    // =====================================================================
    //  INICIO DE PARTIDA
    // =====================================================================
    private void iniciarPartida(Velocidad_Por_Dificultad dificultad) {
        String alias = inputAlias.trim().isEmpty() ? "EcoPlayer" : inputAlias.trim();
        jugador = new Jugador(alias);
        canasta = new Canasta(ANCHO, ALTO);
        gestor  = new GestorResiduos(ANCHO, dificultad);
        estadoActual = EstadoJuego.JUGANDO;
        particulas.clear();
    }

    // =====================================================================
    //  HELPERS
    // =====================================================================
    private void mostrarMensaje(String msg, Color color) {
        mensajeFeedback  = msg;
        colorFeedback    = color;
        mostrarFeedback  = true;
        contadorFeedback = 0;
    }

    private void generarParticulas(int x, int y, Color color, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            particulas.add(new Particula(x, y, color));
        }
    }

    // =====================================================================
    //  TECLADO
    // =====================================================================
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (estadoActual == EstadoJuego.MENU) {
            if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                // Mover selección a la izquierda
                dificultadSeleccionada = Math.max(0, dificultadSeleccionada - 1);
            } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                // Mover selección a la derecha
                dificultadSeleccionada = Math.min(2, dificultadSeleccionada + 1);
            } else if (code == KeyEvent.VK_ENTER) {
                // Iniciar partida con la dificultad seleccionada visualmente
                Velocidad_Por_Dificultad dif = Velocidad_Por_Dificultad.values()[dificultadSeleccionada];
                iniciarPartida(dif);
            } else if (code == KeyEvent.VK_BACK_SPACE) {
                if (!inputAlias.isEmpty())
                    inputAlias = inputAlias.substring(0, inputAlias.length() - 1);
            }
        }

        if (estadoActual == EstadoJuego.JUGANDO) {
            if (code == KeyEvent.VK_LEFT  || code == KeyEvent.VK_A) teclaIzquierda = true;
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) teclaDerecha   = true;
        }

        if (estadoActual == EstadoJuego.GAME_OVER) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_M) {
                estadoActual = EstadoJuego.MENU;
                inputAlias   = "";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT  || code == KeyEvent.VK_A) teclaIzquierda = false;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) teclaDerecha   = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (estadoActual == EstadoJuego.MENU) {
            char c = e.getKeyChar();
            if (Character.isLetterOrDigit(c) || c == '_' || c == '-') {
                if (inputAlias.length() < 15) {
                    inputAlias += c;
                }
            }
        }
    }
}
