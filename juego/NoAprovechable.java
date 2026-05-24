
package juego;


public class NoAprovechable extends Residuo {

    @Override
    public boolean verificarBoton(int b) {
        return b == 3;
    }

}
