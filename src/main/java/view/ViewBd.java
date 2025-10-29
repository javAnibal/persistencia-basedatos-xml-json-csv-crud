package view;

import model.Coche;
import model.Concesionario;

public class ViewBd {


    //
    public void imprimirConcesionario(Concesionario concesionario) {
        if (concesionario == null) {
            System.err.println("El registro del concesionario está vacío");
            return;
        }

        System.out.println("=== CONCESIONARIO ===");
        System.out.println("Total de coches: " + concesionario.getCoches().size());
        System.out.println();

        for (Coche coche : concesionario.getCoches()) {
            System.out.println("Coche ID: " + coche.getId());
            System.out.println("Matrícula: " + coche.getMatricula());
            System.out.println("Marca: " + coche.getMarca());
            System.out.println("Modelo: " + coche.getModelo());
            System.out.println("Extras: " + coche.getEquipamiento());
            System.out.println("-------------------");
        }
    }
}
