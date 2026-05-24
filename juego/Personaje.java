
package juego;

public class Personaje implements Actualizable {
    private int posicionX;

    public void mover(int direccion){
        
    }

    @Override
    public void actualizar() {
        //Para q no se salga por la izquierda
       if (this.posicionX < 0) {
           this.posicionX = 0;
       }

    //Para q no se salga por la derecha
       if (this.posicionX > 750) {
           this.posicionX = 750;
       }
    }
}
