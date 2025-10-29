package services;

import model.Coche;
import model.Concesionario;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de generar un informe de texto con los datos del concesionario.
 * El archivo se guarda en la ruta configurada en ->> [informe_concesionario]
 */
public class GeneradorInforme {

    private final String rutaInforme;

    /**
     * Constructor que obtiene la ruta del informe desde el archivo de configuración.
     */
    public GeneradorInforme() {
        this.rutaInforme = GestorProperties.get("informe_concesionario");
    }

    /**
     * Genera un informe con los datos del concesionario y lo guarda en un archivo de texto.
     */
    public void generarInforme(Concesionario concesionario) {
        if (concesionario == null || concesionario.getCoches().isEmpty()) {
            System.err.println("No hay coches en el concesionario para generar el informe.");
            return;
        }

        try (FileWriter writer = new FileWriter(rutaInforme)) {

            // --- TOTAL DE COCHES ---
            writer.write("Número total de coches: " + concesionario.totalCoches() + "\n\n");

            // --- AGRUPAR COCHES POR MARCA
            writer.write("COCHES AGRUPADOS POR MARCA\n");

            // Lista auxiliar para llevar control de las marcas ya procesadas
            List<String> marcasProcesadas = new ArrayList<>();

            // Recorremos todos los coches
            for (Coche coche : concesionario.getCoches()) {
                String marca = coche.getMarca();

                // Si la marca aún no se ha listado, la procesamos
                if (!marcasProcesadas.contains(marca)) {
                    writer.write("- " + marca.toUpperCase() + ":\n");

                    // Buscamos y mostramos todos los coches de esa marca
                    for (Coche c : concesionario.getCoches()) {
                        if (c.getMarca().equalsIgnoreCase(marca)) {
                            writer.write("    • " + c.getMatricula() + " - " + c.getModelo() + "\n");
                        }
                    }

                    // Marcamos la marca como ya listada
                    marcasProcesadas.add(marca);
                }
            }

            writer.write("\n");

            // --- ENCONTRAR EXTRA MÁS REPETIDO ---
            String extraMasRepetido = "Ninguno";
            int maxRepeticiones = 0;

            // Lista para evitar contar el mismo extra más de una vez
            List<String> extrasContados = new ArrayList<>();

            // Recorremos todos los coches y sus extras
            for (Coche coche : concesionario.getCoches()) {
                for (String extra : coche.getEquipamiento()) {
                    if (!extrasContados.contains(extra)) {
                        int contador = 0;

                        // Contar cuántas veces aparece el extra
                        for (Coche c : concesionario.getCoches()) {
                            if (c.getEquipamiento().contains(extra)) {
                                contador++;
                            }
                        }

                        // Verificar si es el más repetido
                        if (contador > maxRepeticiones) {
                            maxRepeticiones = contador;
                            extraMasRepetido = extra;
                        }

                        // Añadir a la lista de extras contados
                        extrasContados.add(extra);
                    }
                }
            }
            // Escribimos el extra más repetido
            writer.write("Extra más repetido: " + extraMasRepetido + "\n");

            System.out.println("Informe generado correctamente en: " + rutaInforme);

        } catch (IOException e) {
            System.err.println("Error al generar el informe: " + e.getMessage());
        }
    }
}



