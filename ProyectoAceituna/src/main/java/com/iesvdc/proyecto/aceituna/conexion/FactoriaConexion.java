package com.iesvdc.proyecto.aceituna.conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FactoriaConexion {
    private static Connection conn;

    private FactoriaConexion() {
        // Constructor privado para evitar instancias
    }

    public static Connection getConnection() {
        conn = null;
        File fichero = new File("G:\\Mi unidad\\2DAM\\Acceso a datos\\ProyectoAceituna\\ProyectoAceituna\\db.properties");
        try (FileInputStream fis = new FileInputStream(fichero)) {
            Properties prop = new Properties();
            prop.load(fis);
            if ("MySQL".equals(prop.getProperty("driver"))) {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://" + prop.getProperty("host") + ":" +
                                prop.getProperty("port") +
                                "/" + prop.getProperty("database"),
                        prop);
            } else {
                System.out.println("Driver no soportado");
            }
        } catch (SQLException e) {
            System.out.println("FactoriaConexion::Error de sintaxis en el código SQL");
        } catch (ClassCastException e) {
            System.out.println("FactoriaConexion::Driver no encontrado");
        } catch (IOException e) {
            System.out.println("FactoriaConexion::Archivo de configuración no encontrado");
        }
        return conn;
    }

    /**
     * Cierra la conexión
     */
    public static void destroy() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}