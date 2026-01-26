package com.mycompany.biblioteca_digital.base_datos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    
    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";
    
    
    public static Connection obtenerConexion() throws SQLException {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("✓ Conexión a base de datos exitosa");
            return conexion;
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Driver MySQL no encontrado");
            System.err.println("Asegúrate de tener mysql-connector-java en las dependencias");
            throw new SQLException("Driver no encontrado", e);
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar con la base de datos");
            System.err.println("Verifica que XAMPP esté corriendo (MySQL)");
            System.err.println("URL: " + URL);
            throw e;
        }
    }
    
    /**
     * Cierra una conexión a la base de datos
     * @param conexion la conexión a cerrar
     */
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✓ Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("✗ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Método de prueba para verificar la conexión
     */
    public static void main(String[] args) {
        try {
            Connection conn = obtenerConexion();
            System.out.println("=================================");
            System.out.println("PRUEBA DE CONEXIÓN EXITOSA");
            System.out.println("=================================");
            cerrarConexion(conn);
        } catch (SQLException e) {
            System.out.println("=================================");
            System.out.println("ERROR EN LA CONEXIÓN");
            System.out.println("=================================");
            e.printStackTrace();
        }
    }
}