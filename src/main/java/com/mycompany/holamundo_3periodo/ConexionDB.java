package com.mycompany.holamundo_3periodo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nayeli 
 */
public class ConexionDB {
   private static final String URL = "jdbc:sqlserver://localhost:14330;" +
        "databaseName=AvanzadaII;" +
        "encrypt=true;" +
        "trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "nayelita";

    private static Connection conexion = null;
    private ConexionDB() {}

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //establecer la conexion
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("->Conexión exitosa a Sql");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error no se encontró el Driver de mssql-jdbc " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con SQL " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("->La conexión con SQL fue Cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion " + e.getMessage());
        }
    }
}
