package com.iesvdc.proyecto.aceituna.serviciosimpl;

import com.iesvdc.proyecto.aceituna.dao.CuadrillaDAO;
import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;
import com.iesvdc.proyecto.aceituna.entidades.Trabajador;
import com.iesvdc.proyecto.aceituna.entidades.Olivar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuadrillaDAOimpl implements CuadrillaDAO {

    private Connection connection;

    public CuadrillaDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crearCuadrilla(Cuadrilla cuadrilla) {
        String query = "INSERT INTO cuadrillas (nombre, supervisor_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cuadrilla.getNombre());
            stmt.setInt(2, cuadrilla.getSupervisor_id().getId());
            int rowsAffected = stmt.executeUpdate();
            TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int cuadrillaId = rs.getInt(1);
                        trabajadorDAO.insertarTrabajadores(cuadrillaId, cuadrilla.getTrabajadores());
                        olivarDAO.insertarOlivares(cuadrillaId, cuadrilla.getOlivares());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cuadrilla obtenerCuadrillaPorId(int id) {
        String query = "SELECT * FROM cuadrilla WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int supervisorId = rs.getInt("supervisor_id");

                Trabajador supervisor = trabajadorDAO.obtenerTrabajadorPorId(supervisorId);
                List<Trabajador> trabajadores = trabajadorDAO.obtenerTrabajadoresPorCuadrilla(id);
                List<Olivar> olivares = olivarDAO.obtenerOlivaresPorCuadrilla(id);

                return new Cuadrilla(id, nombre, supervisor, trabajadores, olivares);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Cuadrilla> obtenerTodasCuadrillas() {
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        String query = "SELECT * FROM cuadrillas";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(connection);
            OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int supervisorId = rs.getInt("supervisor_id");

                Trabajador supervisor = trabajadorDAO.obtenerTrabajadorPorId(supervisorId);
                List<Trabajador> trabajadores = trabajadorDAO.obtenerTrabajadoresPorCuadrilla(id);
                List<Olivar> olivares = olivarDAO.obtenerOlivaresPorCuadrilla(id);

                cuadrillas.add(new Cuadrilla(id, nombre, supervisor, trabajadores, olivares));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuadrillas;
    }

    @Override
    public boolean actualizarCuadrilla(Cuadrilla cuadrilla) {
        String query = "UPDATE cuadrillas SET nombre = ?, supervisor_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cuadrilla.getNombre());
            stmt.setInt(2, cuadrilla.getSupervisor_id().getId());
            stmt.setInt(3, cuadrilla.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(connection);
                OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
                trabajadorDAO.actualizarTrabajadores(cuadrilla.getId(), cuadrilla.getTrabajadores());
                olivarDAO.actualizarOlivares(cuadrilla.getId(), cuadrilla.getOlivares());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean eliminarCuadrilla(int id) {
        String query = "DELETE FROM cuadrillas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(connection);
                OlivarDAOimpl olivarDAO = new OlivarDAOimpl(connection);
                trabajadorDAO.eliminarTrabajadoresPorCuadrilla(id);
                olivarDAO.eliminarOlivaresPorCuadrilla(id);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Cuadrilla> obtenerCuadrillasPorSupervisor(int supervisorId) throws SQLException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        String query = "SELECT * FROM cuadrilla WHERE supervisor_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, supervisorId);
            ResultSet rs = stmt.executeQuery();
            TrabajadorDAOimpl trabajadorDAO = new TrabajadorDAOimpl(connection);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Trabajador supervisor = trabajadorDAO.obtenerTrabajadorPorId(supervisorId);
                cuadrillas.add(new Cuadrilla(id, nombre, supervisor));
            }
        }
        return cuadrillas;
    }

}