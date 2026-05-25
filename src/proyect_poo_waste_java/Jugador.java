package proyect_poo_waste_java;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Jugador que encapsula el estado del jugador en la partida.
 * Aplica ENCAPSULAMIENTO con atributos privados y metodos publicos.
 */
public class Jugador {

    private String  nombreJugador;
    private int     puntaje;
    private int     vidas;
    private int     residuosAtrapados;
    private int     residuosFallados;

    // Historial de puntajes (para el ranking)
    private static List<EntradaRanking> rankingGlobal = new ArrayList<>();

    public static class EntradaRanking {
        public final String nombre;
        public final int    puntaje;

        public EntradaRanking(String nombre, int puntaje) {
            this.nombre  = nombre;
            this.puntaje = puntaje;
        }
    }

    // --- Constructor ---
    public Jugador(String nombreJugador) {
        this.nombreJugador     = nombreJugador;
        this.puntaje           = 0;
        this.vidas             = 3;
        this.residuosAtrapados = 0;
        this.residuosFallados  = 0;
    }

    // --- Logica de puntuacion ---

    /**
     * Suma puntos por atrapar el residuo correcto.
     */
    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
        this.residuosAtrapados++;
    }

    /**
     * Resta una vida cuando falla (deja caer un residuo sin atrapar).
     */
    public void perderVida() {
        if (this.vidas > 0) {
            this.vidas--;
        }
        this.residuosFallados++;
    }
    
    /**
     * Verifica si el jugador sigue vivo.
     */
    public boolean estaVivo() {
        return this.vidas > 0;
    }

    /**
     * Guarda el puntaje en el ranking global al finalizar la partida.
     */
    public void guardarEnRanking() {
        rankingGlobal.add(new EntradaRanking(nombreJugador, puntaje));
        // Ordenar de mayor a menor puntaje
        rankingGlobal.sort((a, b) -> b.puntaje - a.puntaje);
        // Mantener solo top 5
        if (rankingGlobal.size() > 5) {
            rankingGlobal = rankingGlobal.subList(0, 5);
        }
    }

    // --- Getters y Setters ---
    public String getNombreJugador()    { return nombreJugador; }
    public int    getPuntaje()          { return puntaje; }
    public int    getVidas()            { return vidas; }
    public int    getResiduosAtrapados(){ return residuosAtrapados; }
    public int    getResiduosFallados() { return residuosFallados; }

    public static List<EntradaRanking> getRankingGlobal() { return rankingGlobal; }

    public void setNombreJugador(String nombre) { this.nombreJugador = nombre; }
}
