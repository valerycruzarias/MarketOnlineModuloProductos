package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/marketonline";
    private static final String USER = "root";   
    private static final String PASSWORD = "1022950546Vv*"; 

    public static Connection getConnection() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            return conexion;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}
