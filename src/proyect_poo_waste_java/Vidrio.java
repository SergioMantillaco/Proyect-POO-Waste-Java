
package proyect_poo_waste_java;
public class Vidrio extends Residuo{

    public Vidrio(double posicionx, double posiciony) {
        super(posicionx, posiciony);
    }

    @Override
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(this.velocidadBase);
    }
    
    
    
}
