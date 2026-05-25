package proyect_poo_waste_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GestorResiduos {

    private List<Residuo>              residuosActivos;
    private Velocidad_Por_Dificultad   dificultad;
    private Random                     random;
    private int                        anchoPantalla;
    private Residuo residuoObjetivo;

    private int contadorFrames        = 0;
    private int intervaloGeneracion   = 60; 
    private int nivel                 = 1;

public GestorResiduos(int anchoPantalla, Velocidad_Por_Dificultad dificultad) {
        this.anchoPantalla   = anchoPantalla;
        this.dificultad      = dificultad;
        this.residuosActivos = new ArrayList<>();
        this.random          = new Random();
        
        cambiarObjetivoAleatorio();
    }
    
    public void cambiarObjetivoAleatorio() {
        int tipo = random.nextInt(3);
        switch (tipo) {
            case 0:  this.residuoObjetivo = new Papel(0, 0);    break;
            case 1:  this.residuoObjetivo = new Plastico(0, 0); break;
            default: this.residuoObjetivo = new Vidrio(0, 0);   break;
        }
    }

   
    public void actualizar(int altoPantalla) {
        contadorFrames++;

        if (contadorFrames % 600 == 0 && nivel < 5) {
            nivel++;
            if (intervaloGeneracion > 40) {
                intervaloGeneracion -= 10;
            }
        }

        if (contadorFrames % intervaloGeneracion == 0) {
            generarResiduo();
        }

        for (Residuo r : residuosActivos) {
            r.caer(dificultad);
        }

        for (Residuo r : residuosActivos) {
            if (r.getY() > altoPantalla + 10) {
                r.setActivo(false);
            }
        }
    }


    private void generarResiduo() {
        double x = random.nextInt(anchoPantalla - 50) + 10;
        int tipo = random.nextInt(3);

        Residuo nuevoResiduo;
        switch (tipo) {
            case 0:  nuevoResiduo = new Papel(x, -40);   break;
            case 1:  nuevoResiduo = new Plastico(x, -40); break;
            default: nuevoResiduo = new Vidrio(x, -40);   break;
        }
        residuosActivos.add(nuevoResiduo);
    }
    
    
    public Residuo obtenerObjetivoActual() {
        return this.residuoObjetivo; 
    }

    public List<Residuo>  getResiduosActivos()  { return residuosActivos; }
    public void           setDificultad(Velocidad_Por_Dificultad d) { this.dificultad = d; }
    public Velocidad_Por_Dificultad getDificultad() { return dificultad; }
}
