
package juego;


public abstract class Residuo implements Actualizable{
    protected int posicionX;
    protected int posicionY;

    public void caer(double dificultadActual){

    }

    public abstract boolean verificarBoton(int b);

    @Override
    public void actualizar() {
        this.posicionY += 2;
    }
}
