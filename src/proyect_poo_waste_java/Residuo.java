package proyect_poo_waste_java;
public abstract class  Residuo {
    
    protected double posicionx;
    protected double posiciony;
    protected double velocidadBase = 1.0;      
    public Residuo(double posicionx, double posiciony) {
        this.posicionx = posicionx;
        this.posiciony = posiciony;
    }
    
    public abstract void caer(Velocidad_Por_Dificultad dificultadActual);
    
        // public abstract imagen a mostrar
   
    
    //para que juan ardilla testee
    @Override
    public String toString() {
        return "Residuo tipo: " + this.getClass().getSimpleName() + 
               " | Posicion [X: " + posicionx + ", y: " + posiciony + "]";
    }
    
}
