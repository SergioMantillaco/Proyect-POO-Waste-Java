package proyect_poo_waste_java;

public abstract class Residuo {
    // Coordenadas y dimensiones (Protected para que las hijas accedan)
    protected double posicionx;
    protected double posiciony;
    protected double ancho = 30; // Tamaño en píxeles del residuo
    protected double alto = 30;
    protected double velocidadBase = 2.0;
   
    public Residuo(double posicionx, double posiciony) {
        this.posicionx = posicionx;
        this.posiciony = posiciony;
    }
    
    // Método que actualiza la coordenada Y (simula la gravedad)
    public void caer(Velocidad_Por_Dificultad dificultadActual) {
        this.posiciony += dificultadActual.calcularVelocidadCaida(velocidadBase);
    }

    // Getters para calcular la colisión
    public double getX() { return posicionx; }
    public double getY() { return posiciony; }
    public double getAncho() { return ancho; }
    public double getAlto() { return alto; }
}