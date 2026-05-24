package proyect_poo_waste_java;
public enum Velocidad_Por_Dificultad {
    
    Facil(1),
    Medio(1.5),
    dificil(3);
    
    private final double valor;

    private Velocidad_Por_Dificultad(double valor) {
        this.valor = valor;
    }

    public double getVelocidad() {
        return valor;
    }
    
    
    
    
}
