package proyect_poo_waste_java;

import java.util.ArrayList;

public class Motor implements PatronMotor {
    
    private ArrayList<Residuo> residuosEnPantalla;

    public Motor() {
        residuosEnPantalla = new ArrayList<>();
    }

    @Override
    public void generarResiduo(Residuo r) {
        residuosEnPantalla.add(r);
    }

    @Override
    public void actualizarFisicas(Velocidad_Por_Dificultad dificultad) {
        for (int i = 0; i < residuosEnPantalla.size(); i++) {
            Residuo actual = residuosEnPantalla.get(i);
            actual.caer(dificultad);
            System.out.println(actual); 
        }
    }
}