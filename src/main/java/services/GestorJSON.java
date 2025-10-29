package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Concesionario;
import model.Coche;
import utils.DB_Exception;

import java.io.File;
import java.io.IOException;

/**
 * Clase que gestiona la exportación de datos del concesionario a formato JSON
 * Usa la librería Jackson para convertir objetos Java en archivos JSON
 */
public class GestorJSON {

    private final String rutaJSON;

    public GestorJSON() {
        this.rutaJSON = GestorProperties.get("fichero_json");
    }

    /**
     * Exporta to-do el concesionario a un fichero JSON
     */
    public void exportarConcesionarioAJSON(Concesionario concesionario) throws DB_Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(rutaJSON), concesionario);
            System.out.println("Concesionario exportado correctamente a JSON.");
        } catch (IOException e) {
            throw new DB_Exception("Error al exportar concesionario a JSON: " + e.getMessage());
        }
    }

    /**
     * Exporta un coche específico a un fichero JSON
     */
    public void exportarCocheAJSON(Coche coche) throws DB_Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(rutaJSON), coche);
            System.out.println("Coche exportado correctamente a JSON.");
        } catch (IOException e) {
            throw new DB_Exception("Error al exportar coche a JSON: " + e.getMessage());
        }
    }
}

