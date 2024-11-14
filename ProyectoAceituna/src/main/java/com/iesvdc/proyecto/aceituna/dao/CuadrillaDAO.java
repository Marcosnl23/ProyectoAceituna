package com.iesvdc.proyecto.aceituna.dao;

import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;
import com.iesvdc.proyecto.aceituna.entidades.Trabajador;
import com.iesvdc.proyecto.aceituna.entidades.Olivar;

import java.sql.SQLException;
import java.util.List;

public interface CuadrillaDAO {
    boolean crearCuadrilla(Cuadrilla cuadrilla);
    Cuadrilla obtenerCuadrillaPorId(int id);
    List<Cuadrilla> obtenerTodasCuadrillas();
    List<Cuadrilla> obtenerCuadrillasPorSupervisor(int supervisorId) throws SQLException;
    boolean actualizarCuadrilla(Cuadrilla cuadrilla);
    boolean eliminarCuadrilla(int id);
}