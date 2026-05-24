package proyect_poo_waste_java;
public class Papel extends Residuo{

    public Papel(double posicionx, double posiciony) {
        super(posicionx, posiciony);
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }
    
    
}
