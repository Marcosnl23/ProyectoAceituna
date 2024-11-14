package com.iesvdc.proyecto.aceituna.serviciosimpl;

import com.iesvdc.proyecto.aceituna.dao.OlivarDAO;
import com.iesvdc.proyecto.aceituna.entidades.Olivar;
import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OlivarDAOimpl implements OlivarDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public OlivarDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crearOlivar(Olivar olivar) {
        String query = "INSERT INTO olivares (ubicacion, hectareas, produccion_anual) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, olivar.getUbicacion());
            stmt.setDouble(2, olivar.getHectareas());
            stmt.setDouble(3, olivar.getProduccionAnual());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int olivarId = rs.getInt(1);
                        // Insertar las cuadrillas asociadas al olivar en la tabla intermedia cuadrilla_olivares
                        insertarCuadrillas(olivarId, olivar.getCuadrillas());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insertarCuadrillas(int olivarId, List<Cuadrilla> cuadrillas) throws SQLException {
        String query = "INSERT INTO cuadrilla_olivares (id_cuadrilla, id_olivar) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Cuadrilla cuadrilla : cuadrillas) {
                stmt.setInt(1, cuadrilla.getId());
                stmt.setInt(2, olivarId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public Olivar obtenerOlivarPorId(int id) {
        String query = "SELECT * FROM olivar WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String ubicacion = rs.getString("ubicacion");
                Double hectareas = rs.getDouble("hectareas");
                Double produccionAnual = rs.getDouble("produccionAnual");

                // Obtener las cuadrillas asociadas al olivar
                List<Cuadrilla> cuadrillas = obtenerCuadrillasPorOlivar(id);

                return new Olivar(id, ubicacion, hectareas, produccionAnual, cuadrillas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cuadrilla> obtenerCuadrillasPorOlivar(int olivarId) throws SQLException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        String query = "SELECT c.* FROM cuadrilla c " +
                "JOIN cuadrilla_olivar co ON c.id = co.cuadrilla_id " +
                "WHERE co.olivar_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, olivarId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cuadrillas.add(new Cuadrilla(rs.getInt("id"), rs.getString("nombre"), null));
            }
        }
        return cuadrillas;
    }

    @Override
    public List<Olivar> obtenerTodosLosOlivares() {
        List<Olivar> olivares = new ArrayList<>();
        String query = "SELECT * FROM olivares";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String ubicacion = rs.getString("ubicacion");
                Double hectareas = rs.getDouble("hectareas");
                Double produccionAnual = rs.getDouble("produccion_anual");

                // Obtener las cuadrillas asociadas al olivar
                List<Cuadrilla> cuadrillas = obtenerCuadrillasPorOlivar(id);

                olivares.add(new Olivar(id, ubicacion, hectareas, produccionAnual, cuadrillas));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return olivares;
    }

    @Override
    public boolean actualizarOlivar(Olivar olivar) {
        String query = "UPDATE olivares SET ubicacion = ?, hectareas = ?, produccion_anual = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, olivar.getUbicacion());
            stmt.setDouble(2, olivar.getHectareas());
            stmt.setDouble(3, olivar.getProduccionAnual());
            stmt.setInt(4, olivar.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Actualizar las cuadrillas asociadas
                actualizarCuadrillas(olivar.getId(), olivar.getCuadrillas());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void actualizarCuadrillas(int olivarId, List<Cuadrilla> cuadrillas) throws SQLException {
        // Eliminar las cuadrillas actuales y agregar las nuevas
        String deleteQuery = "DELETE FROM cuadrilla_olivares WHERE id_olivar = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, olivarId);
            stmt.executeUpdate();
        }

        // Insertar las nuevas cuadrillas
        insertarCuadrillas(olivarId, cuadrillas);
    }

    @Override
    public boolean eliminarOlivar(int id) {
        String query = "DELETE FROM olivares WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Eliminar las relaciones con las cuadrillas
                eliminarCuadrillasPorOlivar(id);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void eliminarCuadrillasPorOlivar(int id) throws SQLException {
        String query = "DELETE FROM cuadrilla_olivares WHERE id_olivar = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Olivar> obtenerOlivaresPorCuadrilla(int cuadrillaId) throws SQLException {
        List<Olivar> olivares = new ArrayList<>();
        String query = "SELECT o.* FROM olivar o " +
                "JOIN cuadrilla_olivar co ON o.id = co.olivar_id " +
                "WHERE co.cuadrilla_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cuadrillaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                olivares.add(new Olivar(rs.getInt("id"), rs.getString("ubicacion"), rs.getDouble("hectareas"), rs.getDouble("produccionAnual")));
            }
        }
        return olivares;
    }

    @Override
    public void eliminarOlivaresPorCuadrilla(int id) {
        String query = "DELETE FROM cuadrilla_olivares WHERE cuadrilla_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarOlivares(int id, List<Olivar> olivares) {
        String query = "UPDATE olivar SET ubicacion = ?, hectareas = ?, produccion_anual = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Olivar olivar : olivares) {
                stmt.setString(1, olivar.getUbicacion());
                stmt.setDouble(2, olivar.getHectareas());
                stmt.setDouble(3, olivar.getProduccionAnual());
                stmt.setInt(4, olivar.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertarOlivares(int cuadrillaId, List<Olivar> olivares) {
        String query = "INSERT INTO cuadrilla_olivar (cuadrilla_id, olivar_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Olivar olivar : olivares) {
                stmt.setInt(1, cuadrillaId);
                stmt.setInt(2, olivar.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
