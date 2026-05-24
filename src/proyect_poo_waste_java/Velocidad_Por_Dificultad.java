package proyect_poo_waste_java;

/**
 * Enum que define los niveles de dificultad y su multiplicador de velocidad.
 * Aplica el principio de encapsulamiento y uso de enumeraciones tipadas.
 */
public enum Velocidad_Por_Dificultad {

    FACIL(1.0),
    MEDIO(1.8),
    DIFICIL(3.2);

    private final double multiplicador;

    Velocidad_Por_Dificultad(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    /**
     * Calcula la velocidad de caida de un residuo segun la dificultad actual.
     * @param velocidadBase velocidad base del residuo
     * @return velocidad resultante
     */
    public double calcularVelocidadCaida(double velocidadBase) {
        return velocidadBase * this.multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }
}
