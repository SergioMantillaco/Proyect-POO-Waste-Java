
package juego;
import java.util.ArrayList;


public class Juego {
    private Personaje personaje;
    private ArrayList<Residuo> residuos;
    private int puntuacion;
    private Dificultad dificultadActual;

    public Juego(){
        this.personaje = new Personaje();
        this.residuos = new ArrayList<>();
        this.puntuacion = 0;
        this.dificultadActual = Dificultad.FACIL;
    }

    public void actualizar(){
        evaluarDificultad();

        double velocidadActual = dificultadActual.getVelocidadObjeto();
        for(Residuo r : residuos){
            r.caer(velocidadActual);
        }
        personaje.actualizar();
    }

    private void evaluarDificultad(){
        if (puntuacion >= 100) {
            this.dificultadActual = Dificultad.DIFICIL;
        } else if (puntuacion >=50) {
            this.dificultadActual = Dificultad.MEDIO;
        } else{
            this.dificultadActual = Dificultad.FACIL;
        }
    }

    public void añadirResiduo(Residuo nuevoResiduo){
        this.residuos.add(nuevoResiduo);
    }

    public Personaje getrPersonaje(){
        return this.personaje;
    }
}
