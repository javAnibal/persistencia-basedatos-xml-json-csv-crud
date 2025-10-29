package utils;

import model.Coche;

import java.util.Scanner;

//==============================================================================================

// MÉT-DO -> SOLICITAR DATOS & EQUIPAMIENTO

//==============================================================================================

public class SolicitarDatos {

    public static final Scanner sc = new Scanner(System.in);


    public static Coche solicitarDatos() {

        try {
            System.out.println("Ingrese la matrícula: ");
            String matricula = sc.nextLine().trim().toUpperCase();
            System.out.println("Ingrese la marca: ");
            String marca = sc.nextLine().trim().toLowerCase();
            System.out.println("Ingrese la modelo: ");
            String modelo = sc.nextLine().trim().toLowerCase();

            Coche coche = new Coche(matricula, marca, modelo);
            return coche;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void solicitarEquipamiento(Coche coche) {

        boolean continuar = true;
        while (continuar) {
            System.out.print("¿Desea ingresar equipamiento? (S/N): ");
            String respuesta = sc.nextLine().trim();

            if (respuesta.equalsIgnoreCase("S")) {

                System.out.println("Ingrese el equipamiento");
                String extra = sc.nextLine().trim().toLowerCase();
                coche.addExtra(extra);
                System.out.println("Se ha agregado el equipamiento del coche");

            } else if (respuesta.equalsIgnoreCase("N")) {
                continuar = false;

            } else {
                System.out.println("Opción incorrecta, ingrese (S/N)");
            }

        }

    }
}
