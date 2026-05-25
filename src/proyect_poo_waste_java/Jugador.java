package proyect_poo_waste_java;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String  nombreJugador;
    private int     puntaje;
    private int     vidas;
    private int     residuosAtrapados;
    private int     residuosFallados;

    private static List<EntradaRanking> rankingGlobal = new ArrayList<>();

    public static class EntradaRanking {
        public final String nombre;
        public final int    puntaje;

        public EntradaRanking(String nombre, int puntaje) {
            this.nombre  = nombre;
            this.puntaje = puntaje;
        }
    }

    public Jugador(String nombreJugador) {
        this.nombreJugador     = nombreJugador;
        this.puntaje           = 0;
        this.vidas             = 3;
        this.residuosAtrapados = 0;
        this.residuosFallados  = 0;
    }

    public void sumarPuntos(int puntos) {
        this.puntaje += puntos;
        this.residuosAtrapados++;
    }

    public void perderVida() {
        if (this.vidas > 0) {
            this.vidas--;
        }
        this.residuosFallados++;
    }
  
    public boolean estaVivo() {
        return this.vidas > 0;
    }

    public void guardarEnRanking() {
        rankingGlobal.add(new EntradaRanking(nombreJugador, puntaje));
        rankingGlobal.sort((a, b) -> b.puntaje - a.puntaje);
        if (rankingGlobal.size() > 5) {
            rankingGlobal = rankingGlobal.subList(0, 5);
        }
    }

    public String getNombreJugador()    { return nombreJugador; }
    public int    getPuntaje()          { return puntaje; }
    public int    getVidas()            { return vidas; }
    public int    getResiduosAtrapados(){ return residuosAtrapados; }
    public int    getResiduosFallados() { return residuosFallados; }

    public static List<EntradaRanking> getRankingGlobal() { return rankingGlobal; }

    public void setNombreJugador(String nombre) { this.nombreJugador = nombre; }
}
