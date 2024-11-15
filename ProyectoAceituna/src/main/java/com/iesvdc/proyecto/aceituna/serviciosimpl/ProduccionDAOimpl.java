package com.iesvdc.proyecto.aceituna.serviciosimpl;

import com.iesvdc.proyecto.aceituna.dao.ProduccionDAO;
import com.iesvdc.proyecto.aceituna.entidades.Produccion;
import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;
import com.iesvdc.proyecto.aceituna.entidades.Olivar;
import com.iesvdc.proyecto.aceituna.entidades.Almazara;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProduccionDAOimpl implements ProduccionDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public ProduccionDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crearProduccion(Produccion produccion) {
        String query = "INSERT INTO produccion (cuadrilla_id, olivar_id, almazara_id, fecha, cantidadRecolectada) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, produccion.getCuadrilla_id().getId());
            stmt.setInt(2, produccion.getOlivar_id().getId());
            stmt.setInt(3, produccion.getAlmazara_id().getId());
            stmt.setDate(4, Date.valueOf(produccion.getFecha()));
            stmt.setDouble(5, produccion.getCantidadRecogida());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produccion obtenerProduccionPorId(int id) {
        String query = "SELECT * FROM produccion WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(connection);
            if (rs.next()) {
                Cuadrilla cuadrilla = cuadrillaDAO.obtenerCuadrillaPorId(rs.getInt("id_cuadrilla"));
                Olivar olivar = olivarDAO.obtenerOlivarPorId(rs.getInt("id_olivar"));
                Almazara almazara = almazaraDAO.obtenerAlmazaraPorId(rs.getInt("id_almazara"));
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                Double cantidadRecogida = rs.getDouble("cantidad_recogida");

                return new Produccion(id, cuadrilla, olivar, almazara, fecha, cantidadRecogida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Produccion> obtenerTodasLasProducciones() {
        List<Produccion> producciones = new ArrayList<>();
        String query = "SELECT * FROM produccion";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(connection);
            while (rs.next()) {
                Cuadrilla cuadrilla = cuadrillaDAO.obtenerCuadrillaPorId(rs.getInt("id_cuadrilla"));
                Olivar olivar = olivarDAO.obtenerOlivarPorId(rs.getInt("id_olivar"));
                Almazara almazara = almazaraDAO.obtenerAlmazaraPorId(rs.getInt("id_almazara"));
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                Double cantidadRecogida = rs.getDouble("cantidad_recogida");

                producciones.add(new Produccion(rs.getInt("id"), cuadrilla, olivar, almazara, fecha, cantidadRecogida));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producciones;
    }

    @Override
    public boolean actualizarProduccion(Produccion produccion) {
        String query = "UPDATE produccion SET id_cuadrilla = ?, id_olivar = ?, id_almazara = ?, fecha = ?, cantidad_recogida = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, produccion.getCuadrilla_id().getId());
            stmt.setInt(2, produccion.getOlivar_id().getId());
            stmt.setInt(3, produccion.getAlmazara_id().getId());
            stmt.setDate(4, Date.valueOf(produccion.getFecha()));
            stmt.setDouble(5, produccion.getCantidadRecogida());
            stmt.setInt(6, produccion.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarProduccion(int id) {
        String query = "DELETE FROM produccion WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Produccion> obtenerProduccionEnFecha(int cuadrillaId, int almazaraId, LocalDate fecha) {
        List<Produccion> producciones = new ArrayList<>();
        // Modificamos la consulta para obtener la producción para una cuadrilla, una almazara y una fecha específica
        String query = "SELECT * FROM produccion WHERE cuadrilla_id = ? AND almazara_id = ? AND fecha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Establecemos los parámetros de la consulta
            stmt.setInt(1, cuadrillaId);  // ID de la cuadrilla
            stmt.setInt(2, almazaraId);   // ID de la almazara
            stmt.setDate(3, Date.valueOf(fecha));  // Fecha exacta

            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(connection);
            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Obtenemos los datos de la producción
                Cuadrilla cuadrilla = cuadrillaDAO.obtenerCuadrillaPorId(rs.getInt("cuadrilla_id"));
                Olivar olivar = olivarDAO.obtenerOlivarPorId(rs.getInt("olivar_id"));
                Almazara almazara = almazaraDAO.obtenerAlmazaraPorId(rs.getInt("almazara_id"));
                LocalDate fechaProduccion = rs.getDate("fecha").toLocalDate();
                double cantidadRecogida = rs.getDouble("cantidadRecolectada");

                // Creamos un objeto Produccion y lo añadimos a la lista
                Produccion produccion = new Produccion(
                        rs.getInt("id"),
                        cuadrilla,
                        olivar,
                        almazara,
                        fechaProduccion,
                        cantidadRecogida
                );
                producciones.add(produccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producciones;
    }

    @Override
    public List<Produccion> obtenerProduccionHastaFechaAlmazara(int almazaraId, LocalDate fecha){
        List<Produccion> producciones = new ArrayList<>();
        // Modificamos la consulta para obtener la producción para una almazara y una fecha específica
        String query = "SELECT * FROM produccion WHERE almazara_id = ? AND fecha <= ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Establecemos los parámetros de la consulta
            stmt.setInt(1, almazaraId);   // ID de la almazara
            stmt.setDate(2, Date.valueOf(fecha));  // Fecha exacta

            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(connection);
            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Obtenemos los datos de la producción
                Cuadrilla cuadrilla = cuadrillaDAO.obtenerCuadrillaPorId(rs.getInt("cuadrilla_id"));
                Olivar olivar = olivarDAO.obtenerOlivarPorId(rs.getInt("olivar_id"));
                Almazara almazara = almazaraDAO.obtenerAlmazaraPorId(rs.getInt("almazara_id"));
                LocalDate fechaProduccion = rs.getDate("fecha").toLocalDate();
                double cantidadRecogida = rs.getDouble("cantidadRecolectada");

                // Creamos un objeto Produccion y lo añadimos a la lista
                Produccion produccion = new Produccion(
                        rs.getInt("id"),
                        cuadrilla,
                        olivar,
                        almazara,
                        fechaProduccion,
                        cantidadRecogida
                );
                producciones.add(produccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producciones;
    }

    @Override
    public List<Produccion> obtenerProduccionHastaFechaOlivar(int olivarId, LocalDate fecha){
        List<Produccion> producciones = new ArrayList<>();
        // Modificamos la consulta para obtener la producción para un olivar y una fecha específica
        String query = "SELECT * FROM produccion WHERE olivar_id = ? AND fecha <= ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Establecemos los parámetros de la consulta
            stmt.setInt(1, olivarId);   // ID del olivar
            stmt.setDate(2, Date.valueOf(fecha));  // Fecha exacta

            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(connection);
            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Obtenemos los datos de la producción
                Cuadrilla cuadrilla = cuadrillaDAO.obtenerCuadrillaPorId(rs.getInt("cuadrilla_id"));
                Olivar olivar = olivarDAO.obtenerOlivarPorId(rs.getInt("olivar_id"));
                Almazara almazara = almazaraDAO.obtenerAlmazaraPorId(rs.getInt("almazara_id"));
                LocalDate fechaProduccion = rs.getDate("fecha").toLocalDate();
                double cantidadRecogida = rs.getDouble("cantidadRecolectada");

                // Creamos un objeto Produccion y lo añadimos a la lista
                Produccion produccion = new Produccion(
                        rs.getInt("id"),
                        cuadrilla,
                        olivar,
                        almazara,
                        fechaProduccion,
                        cantidadRecogida
                );
                producciones.add(produccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producciones;
    }

    @Override
    public List<Produccion> obtenerProduccionHastaFechaCuadrilla(int cuadrillaId, LocalDate fecha){
        List<Produccion> producciones = new ArrayList<>();
        // Modificamos la consulta para obtener la producción para una cuadrilla y una fecha específica
        String query = "SELECT * FROM produccion WHERE cuadrilla_id = ? AND fecha <= ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Establecemos los parámetros de la consulta
            stmt.setInt(1, cuadrillaId);   // ID de la cuadrilla
            stmt.setDate(2, Date.valueOf(fecha));  // Fecha exacta

            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            CuadrillaDAOimpl cuadrillaDAO = new CuadrillaDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            AlmazaraDAOimpl almazaraDAO = new AlmazaraDAOimpl(connection);
            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Obtenemos los datos de la producción
                Cuadrilla cuadrilla = cuadrillaDAO.obtenerCuadrillaPorId(rs.getInt("cuadrilla_id"));
                Olivar olivar = olivarDAO.obtenerOlivarPorId(rs.getInt("olivar_id"));
                Almazara almazara = almazaraDAO.obtenerAlmazaraPorId(rs.getInt("almazara_id"));
                LocalDate fechaProduccion = rs.getDate("fecha").toLocalDate();
                double cantidadRecogida = rs.getDouble("cantidadRecolectada");

                // Creamos un objeto Produccion y lo añadimos a la lista
                Produccion produccion = new Produccion(
                        rs.getInt("id"),
                        cuadrilla,
                        olivar,
                        almazara,
                        fechaProduccion,
                        cantidadRecogida
                );
                producciones.add(produccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producciones;
    }

}
