package model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @Coche es la clase que representa un coche dentro del @Concesionario
 * Parte principal del modelo de datos y se utiliza tanto para
 * la persistencia (XML, JSON, CSV) como para las operaciones en memoria.
 * Tiene anotaciones de JAXB para permitir su serialización y deserialización
 * desde / hacia archivos XML.
 */

@XmlRootElement(name = "coche")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coche implements Comparable<Coche> {

    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "matricula-coche")
    private String matricula;

    @XmlElement(name = "marca")
    private String marca;

    @XmlElement(name = "modelo")
    private String modelo;

    @XmlElementWrapper(name = "equipamiento")
    @XmlElement(name = "extra")
    private List<String> equipamiento;

    // Constructor vacío (requerido para JAXB)
    public Coche() {
        this.equipamiento = new ArrayList<>();
    }

    // Constructor para CSV
    public Coche(String matricula, String marca, String modelo) {
        this();
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public List<String> getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(List<String> equipamiento) {
        this.equipamiento = equipamiento;
    }


    /**
     * Añade un nuevo extra al equipamiento del coche
     * Si la lista no está inicializada, la crea
     */
    public void addExtra(String extra) {
        if (this.equipamiento == null) {
            this.equipamiento = new ArrayList<>();
        }
        this.equipamiento.add(extra);
    }


    /**
     * Compara dos coches según su matrícula (orden alfabético ascendente).
     */
    @Override
    public int compareTo(Coche otroCoche) {
        return this.matricula.compareTo(otroCoche.matricula);
    }
}