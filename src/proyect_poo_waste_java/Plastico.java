package proyect_poo_waste_java;
public class Plastico extends Residuo{

    public Plastico(double posicionx, double posiciony) {
        super(posicionx, posiciony);
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }
    
}
