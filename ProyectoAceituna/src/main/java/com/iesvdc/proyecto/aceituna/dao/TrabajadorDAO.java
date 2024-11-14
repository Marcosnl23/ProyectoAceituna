package com.iesvdc.proyecto.aceituna.dao;

import com.iesvdc.proyecto.aceituna.entidades.Trabajador;

import java.sql.SQLException;
import java.util.List;

public interface TrabajadorDAO {
    //Crea un nuevo trabajador en la base de datos.
    boolean crearTrabajador(Trabajador trabajador);

    //Obtiene un trabajador por su ID.
    Trabajador obtenerTrabajadorPorId(int id);

    //Obtiene una lista de todos los trabajadores.
    List<Trabajador> obtenerTodosLosTrabajadores();

    //Actualiza los detalles de un trabajador existente.
    boolean actualizarTrabajador(Trabajador trabajador);

    //Elimina un trabajador por su ID.
    boolean eliminarTrabajador(int id);

    //Obtiene una lista de trabajadores por cuadrilla.
    List<Trabajador> obtenerTrabajadoresPorCuadrilla(int idCuadrilla) throws SQLException;

    //Obtiene una lista de trabajadores por puesto.
    List<Trabajador> obtenerTrabajadoresPorPuesto(String puesto);

    void eliminarTrabajadoresPorCuadrilla(int id);

    void actualizarTrabajadores(int id, List<Trabajador> trabajadores);

    void insertarTrabajadores(int cuadrillaId, List<Trabajador> trabajadores);
}
