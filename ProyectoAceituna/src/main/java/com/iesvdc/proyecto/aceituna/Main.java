package com.iesvdc.proyecto.aceituna;

import com.iesvdc.proyecto.aceituna.conexion.FactoriaConexion;
import com.iesvdc.proyecto.aceituna.interfaz.Menu;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Obtener la conexión de la base de datos
        Connection conn = FactoriaConexion.getConnection();

        if (conn != null) {
            // Crear el menú
            Menu menu = new Menu();
            // Mostrar el menú interactivo
            menu.iniciar();
            // Cerrar la conexión
            FactoriaConexion.destroy();
        } else {
            System.out.println("No se pudo establecer la conexión");
        }
    }
}
