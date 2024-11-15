package com.iesvdc.proyecto.aceituna.dao;

import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;
import com.iesvdc.proyecto.aceituna.entidades.Trabajador;
import com.iesvdc.proyecto.aceituna.entidades.Olivar;

import java.sql.SQLException;
import java.util.List;

public interface CuadrillaDAO {
    // Crea una nueva cuadrilla.
    boolean crearCuadrilla(Cuadrilla cuadrilla);

    // Obtiene una cuadrilla por su ID.
    Cuadrilla obtenerCuadrillaPorId(int id);

    // Obtiene una lista de todas las cuadrillas.
    List<Cuadrilla> obtenerTodasCuadrillas();

    // Actualiza los detalles de una cuadrilla existente.
    boolean actualizarCuadrilla(Cuadrilla cuadrilla);

    // Elimina una cuadrilla por su ID.
    boolean eliminarCuadrilla(int id);

    List<Cuadrilla> obtenerCuadrillasPorSupervisor(int supervisorId) throws SQLException;
}