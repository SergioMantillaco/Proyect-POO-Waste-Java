

package juego;

public enum Dificultad {
    FACIL(0.5),
    MEDIO(1.0),
    DIFICIL(2.0);

    private final double velocidadObjeto;

    private Dificultad(double dificultad){
        this.velocidadObjeto = dificultad;
    }

    public double getVelocidadObjeto() {
        return this.velocidadObjeto;
    }


}
