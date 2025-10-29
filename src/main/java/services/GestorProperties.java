package services;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GestorProperties {
    private static Properties properties = new Properties();

    // Bloque de inicialización estática, lo que significa que
    // este código se ejecuta AL CARGAR la clase
    // una sola vez, cuando la JVM carga GestorProperties


    // -> Cabe indicar que el fichero lo hemos creado en la carpeta -> [resources] ./config.properties



    static {
        try (InputStream input =
                     //--------------------------------------------------
                     // BUSCA -> el fichero en el classpath
                     GestorProperties.class.getClassLoader()
                             .getResourceAsStream("config.properties"))
        //--------------------------------------------------
        {
            properties.load(input); //GUARDA en el objeto [properties] para usarlos después
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo cargar el fichero de configuración", ex);
        }
    }

    public static String getOriginalDb() {
        return properties.getProperty("original_db");
    }

    public static String getPantillaDb() {
        return properties.getProperty("pantilla_db");
    }

    //Permite acceder dinámicamente a cualquier propiedad definida en [conf.properties]
    public static String get(String key) {
        return properties.getProperty(key);
    }


}