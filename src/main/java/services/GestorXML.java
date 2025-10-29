package services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Concesionario;
import utils.DB_Exception;

import java.io.File; // Trabaja con rutas y archivos
import java.io.IOException;


/**
 * @GestorXML ->> clase que gestiona la lectura y escritura del archivo XML que actúa como base de datos.
 * Usa JAXB para convertir entre objetos Java y XML.
 */
public class GestorXML {

   //Accedemos a los mét-dos públicos que me devuelven la ruta destino y pantilla
    String rutaOriginal = GestorProperties.getOriginalDb();
    String rutaPantilla = GestorProperties.getPantillaDb();

    //Comprobaciones
    /**
     * Comprueba si el archivo XML principal existe.
     * Si no existe, crea un nuevo archivo vacío en la ruta configurada.
     */
    public void comprobarFicheroOriginalBb(){

        File originalBd = new File(rutaOriginal);

        if(!originalBd.exists()){
            crearRepositorioGeneral(originalBd, new File(rutaPantilla));
            System.out.println("Se ha creado la base de datos " +originalBd );

        }else {

            System.out.println("BBDD --> " +originalBd );
        }

    }

    private static void crearRepositorioGeneral(File destino, File plantilla){

        // destino.getParentFile().mkdirs();
        //  La pantilla
        //  Files.copy(plantilla.toPath(), destino.toPath()); //Pensé crear fichero con datos pero cambie de idea.

        try {
            destino.getParentFile().mkdirs();
            // Crear archivo vacío
            destino.createNewFile(); //Si el fichero no existe lo crea

        } catch (IOException e) {
            throw new RuntimeException("Error creando archivo vacío", e);
        }


    }



    // DESERIALIZACIÓN
    //----------------------------------------------------------------------------
    // MÉT-DO QUE LEE ->> Base de datos original [XML -> OBJETO] - Unmarshaller
    //----------------------------------------------------------------------------

    public Concesionario leerBaseDeDatos() throws DB_Exception {
        try {
            // Primero nos aseguramos de que el archivo existe
            File archivoBD = new File(rutaOriginal);
            if (!archivoBD.exists()) {
                comprobarFicheroOriginalBb(); // Lo crea si no existe
            }


            // Verificar si el archivo está vacío
            if (archivoBD.length() == 0) {
                System.err.println("No existen registros para mostrar");
                return null;
            }

            // JAXB: XML → Objeto Java
            JAXBContext context = JAXBContext.newInstance(Concesionario.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            Concesionario concesionario = (Concesionario) unmarshaller.unmarshal(archivoBD);
            return concesionario;

        } catch (JAXBException e) {
            e.printStackTrace();
            throw new DB_Exception("Error leyendo el fichero XML con JAXB");
        }
    }




    //----------------------------------------------------------------------------
    // MÉT-DO QUE ESCRIBE ->> Toma un objeto @Concesionario y lo convierte a XML
    //----------------------------------------------------------------------------
    public void guardarBaseDeDatos(Concesionario concesionario) throws DB_Exception {
        try {
            File archivoBD = new File(rutaOriginal);

            JAXBContext context = JAXBContext.newInstance(Concesionario.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Guardar el objeto en el archivo
            marshaller.marshal(concesionario, archivoBD);

            System.out.println("Base de datos guardada correctamente en XML.");

        } catch (JAXBException e) {
            e.printStackTrace();
            throw new DB_Exception("Error guardando el fichero XML con JAXB");
        }
    }


}
