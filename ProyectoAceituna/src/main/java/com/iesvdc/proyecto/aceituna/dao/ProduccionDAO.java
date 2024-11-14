package com.iesvdc.proyecto.aceituna.dao;

import com.iesvdc.proyecto.aceituna.entidades.Almazara;
import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;
import com.iesvdc.proyecto.aceituna.entidades.Produccion;

import java.time.LocalDate;
import java.util.List;

public interface ProduccionDAO {
    //Crea una nueva producción en la base de datos.
    boolean crearProduccion(Produccion produccion);

    //Obtiene una producción por su ID.
    Produccion obtenerProduccionPorId(int id);

    //Obtiene una lista de todas las producciones.
    List<Produccion> obtenerTodasLasProducciones();

    //Actualiza los detalles de una producción existente.
    boolean actualizarProduccion(Produccion produccion);

    //Elimina una producción por su ID.
    boolean eliminarProduccion(int id);

    List<Produccion> obtenerProduccionEnFecha(int cuadrillaId, int almazaraId, LocalDate fecha);

    List<Produccion> obtenerProduccionHastaFechaAlmazara(int almazaraId, LocalDate fecha);

    List<Produccion> obtenerProduccionHastaFechaOlivar(int olivarId, LocalDate fecha);

    List<Produccion> obtenerProduccionHastaFechaCuadrilla(int cuadrillaId, LocalDate fecha);
}
