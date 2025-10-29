import model.Concesionario;
import services.GestorXML;
import utils.DB_Exception;
import view.MenuPrincipal;
import view.ViewBd;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String continuar;
        GestorXML gestorXML = new GestorXML();
        ViewBd viewBd = new ViewBd();
        MenuPrincipal menuPrincipal = new MenuPrincipal();

        do {
            System.out.println("=== REGISTRO DE COCHES ===");
            gestorXML.comprobarFicheroOriginalBb();
            System.out.println("Desea ver los registros actuales (S/N)");
            String respuesta = sc.nextLine().trim().toLowerCase();

            if(respuesta.equals("s")) {
                try {
                    Concesionario concesionario = gestorXML.leerBaseDeDatos(); //Objeto en memoria -> Responderá a las operaciones pertinentes
                    viewBd.imprimirConcesionario(concesionario);//-> Se activa si la validación previa. indica que existe datos para mostrar
                } catch (DB_Exception e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Desea continuar con el menu (S/N) ");
            continuar = sc.nextLine().trim().toLowerCase();

            if(continuar.equals("s")) {
                menuPrincipal.mostrarMenu();
            }

        } while(continuar.equals("s"));

        System.out.println("Hasta Pronto");
    }
}