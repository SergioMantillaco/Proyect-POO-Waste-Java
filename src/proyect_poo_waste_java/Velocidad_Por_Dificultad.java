package proyect_poo_waste_java;


public enum Velocidad_Por_Dificultad {

    FACIL(1.0),
    MEDIO(1.8),
    DIFICIL(3.2);

    private final double multiplicador;

    Velocidad_Por_Dificultad(double multiplicador) {
        this.multiplicador = multiplicador;
    }

   
    public double calcularVelocidadCaida(double velocidadBase) {
        return velocidadBase * this.multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }
}
