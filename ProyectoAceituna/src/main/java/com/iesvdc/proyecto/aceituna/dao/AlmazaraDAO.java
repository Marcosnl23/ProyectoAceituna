package com.iesvdc.proyecto.aceituna.dao;

import com.iesvdc.proyecto.aceituna.entidades.Almazara;

import java.util.List;

public interface AlmazaraDAO {
    //Crea una nueva almazara en la base de datos.
    void crearAlmazara(Almazara almazara);

    //Obtiene una almazara por su ID.
    Almazara obtenerAlmazaraPorId(int id);

    //Obtiene todas las almazaras
    List<Almazara> obtenerTodasAlmazaras();

    //Actualiza los detalles de una almazara existente.
    boolean actualizarAlmazara(Almazara almazara);

    //Elimina una almazara por su ID.
    boolean eliminarAlmazara(int id);

    //Obtiene todas las almazaras que llevan aceituna de una determinada cuadrilla.
    List<Almazara> obtenerAlmazarasPorCuadrilla(int cuadrillaId);
}

