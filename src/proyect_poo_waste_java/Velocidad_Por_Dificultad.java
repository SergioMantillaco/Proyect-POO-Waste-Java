package proyect_poo_waste_java;
public enum Velocidad_Por_Dificultad {
    
    Facil(1),
    Medio(1.5),
    dificil(3);
    private double Velocidad;
    private final double Valor;

    private Velocidad_Por_Dificultad(double valor) {
        this.Valor = valor;
    }

    public double getMultiplicadorVelocidad(double Velocidad) {
        this.Velocidad = Velocidad;
        return Velocidad = Velocidad * Valor;
    }
    
    
    
    
}
