package proyect_poo_waste_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que gestiona la generacion y actualizacion de residuos en pantalla.
 * Aplica ENCAPSULAMIENTO y uso de colecciones con polimorfismo.
 */
public class GestorResiduos {

    private List<Residuo>              residuosActivos;
    private Velocidad_Por_Dificultad   dificultad;
    private Random                     random;
    private int                        anchoPantalla;

    // Control de generacion
    private int contadorFrames        = 0;
    private int intervaloGeneracion   = 90; // cada cuantos frames cae un nuevo residuo
    private int nivel                 = 1;

    public GestorResiduos(int anchoPantalla, Velocidad_Por_Dificultad dificultad) {
        this.anchoPantalla   = anchoPantalla;
        this.dificultad      = dificultad;
        this.residuosActivos = new ArrayList<>();
        this.random          = new Random();
    }

    /**
     * Actualiza todos los residuos (los hace caer).
     * Genera nuevos residuos segun el intervalo.
     */
    public void actualizar(int altoPantalla) {
        contadorFrames++;

        // Ajustar dificultad progresivamente
        if (contadorFrames % 600 == 0 && nivel < 5) {
            nivel++;
            if (intervaloGeneracion > 40) {
                intervaloGeneracion -= 10;
            }
        }

        // Generar nuevo residuo
        if (contadorFrames % intervaloGeneracion == 0) {
            generarResiduo();
        }

        // Mover residuos hacia abajo usando polimorfismo
        for (Residuo r : residuosActivos) {
            r.caer(dificultad); // POLIMORFISMO: cada clase implementa su propia logica
        }

        // Marcar como inactivos los que salen de pantalla
        for (Residuo r : residuosActivos) {
            if (r.getY() > altoPantalla + 10) {
                r.setActivo(false);
            }
        }
    }

    /**
     * Genera aleatoriamente un residuo en la parte superior de la pantalla.
     * Usa polimorfismo: se crea la subclase adecuada segun un numero aleatorio.
     */
    private void generarResiduo() {
        double x = random.nextInt(anchoPantalla - 50) + 10;
        int tipo = random.nextInt(3);

        Residuo nuevoResiduo;
        // Polimorfismo en accion: se crea el tipo concreto
        switch (tipo) {
            case 0:  nuevoResiduo = new Papel(x, -40);   break;
            case 1:  nuevoResiduo = new Plastico(x, -40); break;
            default: nuevoResiduo = new Vidrio(x, -40);   break;
        }
        residuosActivos.add(nuevoResiduo);
    }

    /**
     * Devuelve el tipo de residuo que el jugador debe atrapar ahora.
     * (El primer residuo activo visible indica el objetivo).
     */
    public Residuo obtenerObjetivoActual() {
        for (Residuo r : residuosActivos) {
            if (r.isActivo()) return r;
        }
        return null;
    }

    public List<Residuo>  getResiduosActivos()  { return residuosActivos; }
    public void           setDificultad(Velocidad_Por_Dificultad d) { this.dificultad = d; }
    public Velocidad_Por_Dificultad getDificultad() { return dificultad; }
}
