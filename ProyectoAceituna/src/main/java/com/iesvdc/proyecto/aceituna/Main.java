package com.iesvdc.proyecto.aceituna;

import com.iesvdc.proyecto.aceituna.conexion.FactoriaConexion;
import com.iesvdc.proyecto.aceituna.entidades.Almazara;
import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;
import com.iesvdc.proyecto.aceituna.entidades.Olivar;
import com.iesvdc.proyecto.aceituna.entidades.Produccion;
import com.iesvdc.proyecto.aceituna.entidades.Trabajador;
import com.iesvdc.proyecto.aceituna.interfaz.Menu;
import com.iesvdc.proyecto.aceituna.serviciosimpl.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Obtener la conexión de la base de datos
        Connection conn = FactoriaConexion.getConnection();

//        // Crear trabajadores con sus datos
//        Trabajador trabajador1 = new Trabajador(1, "Juan Perez", 30, "Supervisor", 3000.0);
//        Trabajador trabajador2 = new Trabajador(2, "Ana Gomez", 25, "Recolector", 2000.0);
//        Trabajador trabajador3 = new Trabajador(3, "Luis Martinez", 28, "Recolector", 2100.0);
//        Trabajador trabajador4 = new Trabajador(4, "Maria Sanchez", 35, "Supervisor", 3200.0);
//        Trabajador trabajador5 = new Trabajador(5, "Pedro Rodriguez", 40, "Recolector", 2200.0);
//        Trabajador trabajador6 = new Trabajador(6, "Sara Fernandez", 22, "Recolector", 1900.0);
//        Trabajador trabajador7 = new Trabajador(7, "Javier Lopez", 33, "Supervisor", 3100.0);
//
//        // Crear olivares con sus datos
//        Olivar olivar1 = new Olivar(1, "Ubicación A", 10.5, 5000.0);
//        Olivar olivar2 = new Olivar(2, "Ubicación B", 15.0, 7000.0);
//        Olivar olivar3 = new Olivar(3, "Ubicación C", 20.0, 10000.0);
//
//        // Crear almazaras con sus datos
//        Almazara almazara1 = new Almazara(1, "Almazara Central", "Ubicación Z", 15000.0);
//        Almazara almazara2 = new Almazara(2, "Almazara Norte", "Ubicación Y", 12000.0);
//        Almazara almazara3 = new Almazara(3, "Almazara Sur", "Ubicación X", 10000.0);
//
//        // Crear cuadrillas con sus datos
//        List<Trabajador> cuadrilla1Trabajadores = Arrays.asList(trabajador2, trabajador3);
//        List<Trabajador> cuadrilla2Trabajadores = Arrays.asList(trabajador5, trabajador6);
//        List<Trabajador> cuadrilla3Trabajadores = Arrays.asList(trabajador7, trabajador1, trabajador4);
//
//        List<Olivar> cuadrilla1Olivares = Arrays.asList(olivar1);
//        List<Olivar> cuadrilla2Olivares = Arrays.asList(olivar2, olivar3);
//
//        Cuadrilla cuadrilla1 = new Cuadrilla(1, "Cuadrilla Norte", trabajador1, cuadrilla1Trabajadores, cuadrilla1Olivares);
//        Cuadrilla cuadrilla2 = new Cuadrilla(2, "Cuadrilla Sur", trabajador4, cuadrilla2Trabajadores, cuadrilla2Olivares);
//        Cuadrilla cuadrilla3 = new Cuadrilla(3, "Cuadrilla Central", trabajador7, cuadrilla3Trabajadores, cuadrilla1Olivares);
//
//        // Crear producción con sus datos
//        Produccion produccion1 = new Produccion(1, cuadrilla1, olivar1, almazara1, LocalDate.of(2024, 11, 15), 4500.0);
//        Produccion produccion2 = new Produccion(2, cuadrilla2, olivar2, almazara2, LocalDate.of(2024, 11, 16), 6000.0);
//        Produccion produccion3 = new Produccion(3, cuadrilla2, olivar3, almazara2, LocalDate.of(2024, 11, 17), 4000.0);
//        Produccion produccion4 = new Produccion(4, cuadrilla1, olivar1, almazara1, LocalDate.of(2024, 11, 18), 5000.0);
//        Produccion produccion5 = new Produccion(5, cuadrilla2, olivar2, almazara3, LocalDate.of(2024, 11, 19), 7000.0);
//
//        // Insertar los datos en la base de datos
//        TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(conn);
//        trabajadorDAO.crearTrabajador(trabajador1);
//        trabajadorDAO.crearTrabajador(trabajador2);
//        trabajadorDAO.crearTrabajador(trabajador3);
//        trabajadorDAO.crearTrabajador(trabajador4);
//        trabajadorDAO.crearTrabajador(trabajador5);
//        trabajadorDAO.crearTrabajador(trabajador6);
//        trabajadorDAO.crearTrabajador(trabajador7);
//
//        OlivarDAOimpl olivarDAO = new OlivarDAOimpl(conn);
//        olivarDAO.crearOlivar(olivar1);
//        olivarDAO.crearOlivar(olivar2);
//        olivarDAO.crearOlivar(olivar3);
//
//        AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(conn);
//        almazaraDAO.crearAlmazara(almazara1);
//        almazaraDAO.crearAlmazara(almazara2);
//        almazaraDAO.crearAlmazara(almazara3);
//
//        CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(conn);
//        cuadrillaDAO.crearCuadrilla(cuadrilla1);
//        cuadrillaDAO.crearCuadrilla(cuadrilla2);
//        cuadrillaDAO.crearCuadrilla(cuadrilla3);
//
//        ProduccionDAOimpl produccionDAO = new ProduccionDAOimpl(conn);
//        produccionDAO.crearProduccion(produccion1);
//        produccionDAO.crearProduccion(produccion2);
//        produccionDAO.crearProduccion(produccion3);
//        produccionDAO.crearProduccion(produccion4);
//        produccionDAO.crearProduccion(produccion5);

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
