package services;

import model.Coche;
import model.Concesionario;
import utils.DB_Exception;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



/**
 * @GestorCSV se encarga de gestionar la importación de datos desde
 * archivos CSV al sistema de concesionarios.
 * Permite leer un archivo CSV definido en el fichero de configuración
 * @config.properties ->> convertir cada línea en un objeto @Coche
 * y cargar dichos coches dentro de un @Concesionario.
 */
public class GestorCSV {

    private String rutaCSV;

    public GestorCSV() {
        // Leer la ruta desde config.properties
        this.rutaCSV = GestorProperties.get("fichero_importado");

    }

    /**
     * Lee el archivo CSV y carga los coches en un concesionario
     */
    public Concesionario leerCSV() throws DB_Exception {
        System.out.println("Leyendo CSV desde: " + rutaCSV);

        // Verificar si el archivo existe
        File archivo = new File(rutaCSV);
        if (!archivo.exists()) {
            throw new DB_Exception("El archivo CSV no existe: " + rutaCSV);
        }

        Concesionario concesionario = new Concesionario();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {

            String linea;
            boolean primeraLinea = true;


            while ((linea = br.readLine()) != null) {
                // Saltar la primera línea (encabezados)
                if (primeraLinea) {
                    primeraLinea = false;
                    System.out.println("Se omite encabezado: ");
                    continue;
                }

                // Procesar cada línea
                Coche coche = procesarLineaCSV(linea);
                if (coche != null) {
                    concesionario.agregarCoche(coche); //Se agrega el coche
                }
            }

            System.out.println("Total en concesionario: " + concesionario.totalCoches());

        } catch (IOException e) {
            throw new DB_Exception("Error al leer el archivo CSV: " + e.getMessage());
        }

        return concesionario;
    }

    /**
     * Convierte una línea del CSV en un objeto Coche
     */
    private Coche procesarLineaCSV(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return null;
        }


        String[] datos = linea.split(";");


        if (datos.length < 3) {
            System.out.println("Línea incompleta: " + linea);
            return null;
        }

        try {
            String matricula = datos[0].trim();
            String marca = datos[1].trim();
            String modelo = datos[2].trim();

            // Validar campos obligatorios
            if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
                System.out.println("Campos vacíos en: " + linea);
                return null;
            }

            // Crear el coche
            Coche coche = new Coche(matricula, marca, modelo);

            // Procesar extras si existen (campo 4)
            if (datos.length > 3 && !datos[3].isEmpty()) {
                String[] extras = datos[3].split("\\|");
                for (String extra : extras) {
                    String extraLimpio = extra.trim();
                    if (!extraLimpio.isEmpty()) {
                        coche.addExtra(extraLimpio);
                    }
                }
            }

            return coche;

        } catch (Exception e) {

            return null;
        }
    }



    public void importarSinDuplicados(Concesionario concesionarioExistente) throws DB_Exception {
        Concesionario nuevos = leerCSV(); // REUTILIZAMOS EL CÓDIGO

        for (Coche coche : nuevos.getCoches()) {
            if (concesionarioExistente.buscarPorMatricula(coche.getMatricula()) == null) {
                concesionarioExistente.agregarCoche(coche);

            } else {
                System.out.println("Duplicado (omitido): " + coche.getMatricula());
            }
        }
    }



}