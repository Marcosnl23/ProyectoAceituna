package com.iesvdc.proyecto.aceituna.serviciosimpl;

import com.iesvdc.proyecto.aceituna.dao.AlmazaraDAO;
import com.iesvdc.proyecto.aceituna.entidades.Almazara;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmazaraDAOimpl implements AlmazaraDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public AlmazaraDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crearAlmazara(Almazara almazara) {
        String query = "INSERT INTO almazara (nombre, ubicacion, capacidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, almazara.getNombre());
            stmt.setString(2, almazara.getUbicacion());
            stmt.setDouble(3, almazara.getCapacidad());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Almazara obtenerAlmazaraPorId(int id) {
        String query = "SELECT * FROM almazara WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Almazara(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ubicacion"), rs.getDouble("capacidad"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Almazara> obtenerTodasAlmazaras() {
        List<Almazara> almazaras = new ArrayList<>();
        String query = "SELECT * FROM almazaras";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Almazara almazara = new Almazara(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ubicacion"), rs.getDouble("capacidad"));
                almazaras.add(almazara);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almazaras;
    }

    @Override
    public boolean actualizarAlmazara(Almazara almazara) {
        String query = "UPDATE almazaras SET nombre = ?, ubicacion = ?, capacidad = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, almazara.getNombre());
            stmt.setString(2, almazara.getUbicacion());
            stmt.setDouble(3, almazara.getCapacidad());
            stmt.setInt(4, almazara.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarAlmazara(int id) {
        String query = "DELETE FROM almazaras WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Almazara> obtenerAlmazarasPorCuadrilla(int cuadrillaId) {
        List<Almazara> almazaras = new ArrayList<>();
        String query = "SELECT almazara.id, almazara.nombre, almazara.ubicacion, almazara.capacidad " +
                "FROM almazara " +
                "JOIN produccion ON almazara.id = produccion.almazara_id " +
                "JOIN cuadrilla ON produccion.cuadrilla_id = cuadrilla.id " +
                "WHERE cuadrilla.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cuadrillaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Almazara almazara = new Almazara(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("ubicacion"), rs.getDouble("capacidad"));
                almazaras.add(almazara);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almazaras;
    }
}
