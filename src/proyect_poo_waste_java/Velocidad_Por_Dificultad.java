package proyect_poo_waste_java;
public enum Velocidad_Por_Dificultad {
    
    FACIL(1),
    MEDIO(1.5),
    DIFICIL(3);
    private double Velocidad;
    private final double ValorMultiplicador;

    private Velocidad_Por_Dificultad(double valor) {
        this.ValorMultiplicador = valor;
    }

    public double calcularVelocidadCaida(double velocidadBase) {
        return velocidadBase * this.ValorMultiplicador;
    }
    
    }
    
    
    
