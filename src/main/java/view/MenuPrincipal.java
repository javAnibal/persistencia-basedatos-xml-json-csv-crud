package view;

import controller.ControllerBd;
import model.Coche;
import model.Concesionario;
import utils.SolicitarDatos;


import java.util.*;


public class MenuPrincipal {

    private static Concesionario concesionario = new Concesionario();
    private static final Scanner sc = new Scanner(System.in);
    ControllerBd controller = new ControllerBd();
    ViewBd view = new ViewBd();


    private void listaDeOpciones() {

        List<String> listaDeOpciones = List.of(
                " 1) IMPORTAR -> desde CSV",
                " 2) INSERTAR -> coche ",
                " 3) ORDENAR -> por matrícula ",
                " 4) BORRAR -> por matrícula ",
                " 5) MODIFICAR -> registro ",
                " 6) EXPORTAR A JSON ",
                " 7) IMPORTAR DESDE JSON ",
                " 8) GENERAR INFORME ",
                " 0) SALIR "
        );

        listaDeOpciones.forEach(System.out::println);
        System.out.print("Elija opción: -> ");

    }

    public void mostrarMenu() {

        try {
            while (true) {
                listaDeOpciones();

                int input;
                try {
                    input = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Debe ser un número entre [0-5]");
                    continue;
                }

                if (input < 0 || input > 8) {
                    System.out.println("Entrada fuera de rango [0-5]. Intente de nuevo.");
                    continue;
                }

                switch (input) {
                    case 1 -> {
                        controller.importarDesdeCSV();
                    }
                    case 2 -> {
                        controller.insertaCocheDesdeConsola();
                    }
                    case 3 -> {
                        controller.ordenarPorMatricula();
                        break;
                    }
                    case 4 -> {
                        System.out.print("Ingrese la matrícula del coche a eliminar: ");
                        String matricula = sc.nextLine().trim().toUpperCase();
                        controller.eliminarCochePorMatricula(matricula);
                    }
                    case 5 -> {
                        System.out.print("Ingrese la matrícula del coche a modificar: ");
                        String matricula = sc.nextLine().trim().toUpperCase();

                        Coche coche = controller.buscarCochePorMatricula(matricula);
                        if (coche == null) {
                            System.out.println("No se encontró ningún coche con esa matrícula.");
                            break;
                        }

                        System.out.println("Coche actual: " + coche);

                        System.out.print("Nueva marca (actual: " + coche.getMarca() + "): ");
                        String nuevaMarca = sc.nextLine().trim();
                        if (!nuevaMarca.isEmpty()) coche.setMarca(nuevaMarca.toLowerCase());

                        System.out.print("Nuevo modelo (actual: " + coche.getModelo() + "): ");
                        String nuevoModelo = sc.nextLine().trim();
                        if (!nuevoModelo.isEmpty()) coche.setModelo(nuevoModelo.toLowerCase());

                        controller.guardarCambios();
                        System.out.println("Coche modificado correctamente.");
                        break;

                    }

                    case 6 -> {
                        System.out.println("¿Qué desea exportar?");
                        System.out.println("1) Todo el concesionario");
                        System.out.println("2) Un coche por matrícula");
                        System.out.print("Seleccione opción: ");
                        String opcion = sc.nextLine().trim();

                        switch (opcion) {
                            case "1" -> controller.exportarConcesionarioAJSON();
                            case "2" -> {
                                System.out.print("Ingrese la matrícula del coche: ");
                                String matricula = sc.nextLine().trim().toUpperCase();
                                controller.exportarCochePorMatricula(matricula);
                            }
                            default -> System.out.println("Opción inválida.");
                        }
                    }
                    case 7 -> {

                            System.out.println("¿Qué desea importar?");
                            System.out.println("1) Concesionario completo desde JSON (sobrescribe la base de datos)");
                            System.out.println("2) Un coche desde JSON (se añade a la base de datos)");
                            System.out.print("Seleccione opción: ");
                            String opcion = sc.nextLine().trim();

                            switch (opcion) {
                                case "1" -> controller.importarConcesionarioDesdeJSON();
                                case "2" -> controller.importarCocheDesdeJSON();
                                default -> System.out.println("Opción inválida.");
                            }
                    }


                    case 8 -> {
                        controller.generarInformeResumen();
                    }
                    case 0 -> {
                        System.out.println("Saliendo del programa ...");
                        sc.close();
                        System.exit(0);
                    }
                }

                System.out.println();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}