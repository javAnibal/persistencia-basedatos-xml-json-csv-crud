package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Coche;
import model.Concesionario;
import utils.DB_Exception;

import java.io.File;
import java.io.IOException;

public class GestorImportJson {

    private final String rutaJSON;

    public GestorImportJson() {
        this.rutaJSON = GestorProperties.get("fichero_json");
    }

    /**
     * Importa un concesionario completo desde un fichero JSON
     * y lo guarda como nueva base de datos XML
     */
    public void importarConcesionarioDesdeJSON(GestorXML gestorXML) throws DB_Exception {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Lee el archivo JSON y convierte su contenido en un objeto Concesionario
            Concesionario concesionario = mapper.readValue(new File(rutaJSON), Concesionario.class);
            // Sobrescribe la base de datos XML con el nuevo concesionario
            gestorXML.guardarBaseDeDatos(concesionario);
            System.out.println("Concesionario importado desde JSON y guardado como nueva base de datos");
        } catch (IOException e) {
            throw new DB_Exception("Error al importar concesionario desde JSON: " + e.getMessage());
        }
    }

    /**
     * Importa un coche desde un fichero JSON y lo inserta en la base de datos actual
     */
    public void importarCocheDesdeJSON(GestorXML gestorXML) throws DB_Exception {
        // Creamos un objeto ObjectMapper para manejar la conversión entre JSON y objetos Java
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Lee el archivo JSON y convierte su contenido en un objeto Coche
            Coche coche = mapper.readValue(new File(rutaJSON), Coche.class);

            // Carga el concesionario actual desde el XML
            Concesionario concesionario = gestorXML.leerBaseDeDatos();
            if (concesionario == null) {
                concesionario = new Concesionario();
            }
            concesionario.agregarCoche(coche);

            // Guarda el XML actualizado
            gestorXML.guardarBaseDeDatos(concesionario);

            System.out.println("Coche importado desde JSON");
        } catch (IOException e) {
            throw new DB_Exception("Error al importar coche desde JSON: " + e.getMessage());
        }
    }
}



