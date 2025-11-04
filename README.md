# MarketOnlineModuloProductos
Configuración de la base de datos

Abra MySQL o MySQL Workbench.

Cree la base de datos ejecutando:

CREATE DATABASE marketonline;

Importe el archivo marketonline.sql incluido en el proyecto.

Esto creará las tablas necesarias para el módulo de productos.

Abra el archivo:

src/config/conexionDB.java

y verifique que las credenciales sean correctas:

private static final String URL = "jdbc:mysql://localhost:3306/marketonline";

private static final String USER = "root";

private static final String PASSWORD = "1022950546Vv*";

Ejecución del programa
Opción A — Desde un IDE

Abra el proyecto en su IDE (por ejemplo, NetBeans o IntelliJ).

Asegúrese de agregar la librería:

lib/mysql-connector-j-9.5.0.jar

Ejecute la clase principal:

src/main/app.java

En la consola del IDE aparecerá el menú principal del programa, donde podrá:

Crear productos

Listar productos

Actualizar productos

Eliminar productos

Opción B — Desde la terminal (sin IDE)

Abra una terminal en la carpeta del proyecto.

Compile los archivos:

javac -cp ".;lib/mysql-connector-j-9.5.0.jar" src/config/*.java src/models/*.java src/main/*.java -d bin

Ejecute el programa:
java -cp ".;bin;lib/mysql-connector-j-9.5.0.jar" main.app

En la consola se mostrará el menú con las opciones del CRUD.


COSAS A TENER EN CUENTA

-cuando pide el ID del vendedor poner "1" 

-si el precio es con decimal con "," 
