package com.iesvdc.proyecto.aceituna.dao;

import com.iesvdc.proyecto.aceituna.entidades.Olivar;

import java.sql.SQLException;
import java.util.List;

public interface OlivarDAO {
    //Crea un nuevo olivar.
    boolean crearOlivar(Olivar olivar);

    //Obtiene un olivar por su ID.
    Olivar obtenerOlivarPorId(int id);

    //Obtiene todos los olivares
    List<Olivar> obtenerTodosLosOlivares();

    //Actualiza los detalles de un olivar existente.
    boolean actualizarOlivar(Olivar olivar);

    //Elimina un olivar por su ID.
    boolean eliminarOlivar(int id);
    List<Olivar> obtenerOlivaresPorCuadrilla(int cuadrillaId) throws SQLException;

    void eliminarOlivaresPorCuadrilla(int id);

    void actualizarOlivares(int id, List<Olivar> olivares);

    void insertarOlivares(int cuadrillaId, List<Olivar> olivares);
}
