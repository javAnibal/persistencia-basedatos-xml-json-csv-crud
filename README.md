# Gestor de Concesionario de Coches

Aplicación Java que gestiona un concesionario de coches, permitiendo **importar, exportar y visualizar datos** desde distintos formatos (`CSV`, `XML`, `JSON`) y generar informes automáticos.

---

## Descripción del Proyecto

Este proyecto permite administrar la base de datos de un concesionario de coches.  
Los datos se pueden almacenar y recuperar en formato **XML** mediante la librería **JAXB**, así como importar/exportar información en **JSON** y **CSV**.

Incluye además un generador de informes en formato de texto con información resumida del concesionario.

---

## Funcionalidades Principales

### Gestión de coches (`model`)
- **Coche.java**: Define las propiedades de un coche (ID, matrícula, marca, modelo, equipamiento).  
- **Concesionario.java**: Contiene la lista de coches y métodos para agregar, eliminar, buscar o contar coches.

### Persistencia de datos (`services`)
- **GestorXML.java**  
  - Guarda y lee la base de datos principal en formato XML.  
  - Usa `JAXB` para la serialización y deserialización de objetos Java.  

- **GestorCSV.java**  
  - Permite importar coches desde un fichero CSV.  
  - Evita duplicados basándose en la matrícula.  

- **GestorJSON.java**  
  - Exporta un coche individual o todo el concesionario a un fichero JSON.  
  - Usa la librería `Jackson` para la conversión.  

- **GestorImportJson.java**  
  - Importa desde JSON, ya sea un coche individual o un concesionario completo.  
  - Puede reemplazar la base de datos XML actual o añadir nuevos coches.  

- **GeneradorInforme.java**  
  - Genera un informe en texto con:
    - Total de coches.  
    - Coches agrupados por marca.  
    - Extra más repetido.  

### Visualización (`view`)
- **ViewBd.java**  
  - Muestra por consola los datos del concesionario y sus coches.

---

## Arquitectura del Proyecto

``` lua

src/  
├── model/  
│ ├── Coche.java  
│ └── Concesionario.java  
│  
├── services/  
│ ├── GestorXML.java  
│ ├── GestorCSV.java  
│ ├── GestorJSON.java  
│ ├── GestorImportJson.java  
│ └── GeneradorInforme.java  
│  
├── utils/  
│ └── DB_Exception.java  
│  
└── view/  
└── ViewBd.java

```
