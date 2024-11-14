package com.iesvdc.proyecto.aceituna.serviciosimpl;

import com.iesvdc.proyecto.aceituna.dao.TrabajadorDAO;
import com.iesvdc.proyecto.aceituna.entidades.Trabajador;
import com.iesvdc.proyecto.aceituna.entidades.Cuadrilla;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAOimpl implements TrabajadorDAO {

    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public TrabajadorDAOimpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean crearTrabajador(Trabajador trabajador) {
        String query = "INSERT INTO trabajador (nombre, edad, puesto, salario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trabajador.getNombre());
            stmt.setInt(2, trabajador.getEdad());
            stmt.setString(3, trabajador.getPuesto());
            stmt.setDouble(4, trabajador.getSalario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Trabajador obtenerTrabajadorPorId(int id) {
        String query = "SELECT * FROM trabajador WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("puesto"),
                        rs.getDouble("salario"),
                        new ArrayList<Cuadrilla>()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Trabajador> obtenerTodosLosTrabajadores() {
        List<Trabajador> trabajadores = new ArrayList<>();
        String query = "SELECT * FROM trabajador";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajadores.add(new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("puesto"),
                        rs.getDouble("salario"),
                        new ArrayList<Cuadrilla>()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajadores;
    }

    @Override
    public boolean actualizarTrabajador(Trabajador trabajador) {
        String query = "UPDATE trabajador SET nombre = ?, edad = ?, puesto = ?, salario = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, trabajador.getNombre());
            stmt.setInt(2, trabajador.getEdad());
            stmt.setString(3, trabajador.getPuesto());
            stmt.setDouble(4, trabajador.getSalario());
            stmt.setInt(5, trabajador.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarTrabajador(int id) {
        String query = "DELETE FROM trabajador WHERE id = ?";
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
    public List<Trabajador> obtenerTrabajadoresPorCuadrilla(int cuadrillaId) throws SQLException {
        List<Trabajador> trabajadores = new ArrayList<>();
        String query = "SELECT t.* FROM trabajador t " +
                "JOIN cuadrilla_trabajador ct ON t.id = ct.trabajador_id " +
                "WHERE ct.cuadrilla_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cuadrillaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajadores.add(new Trabajador(rs.getDouble("salario"), rs.getString("puesto"), rs.getInt("edad"), rs.getString("nombre"), rs.getInt("id")));
            }
        }
        return trabajadores;
    }


    @Override
    public List<Trabajador> obtenerTrabajadoresPorPuesto(String puesto) {
        List<Trabajador> trabajadores = new ArrayList<>();
        String query = "SELECT * FROM trabajador WHERE puesto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, puesto);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trabajadores.add(new Trabajador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("puesto"),
                        rs.getDouble("salario"),
                        new ArrayList<Cuadrilla>()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajadores;
    }

    @Override
    public void eliminarTrabajadoresPorCuadrilla(int id) {
        String query = "DELETE FROM cuadrilla_trabajador WHERE cuadrilla_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarTrabajadores(int id, List<Trabajador> trabajadores) {
        eliminarTrabajadoresPorCuadrilla(id);
        insertarTrabajadores(id, trabajadores);
    }

    @Override
    public void insertarTrabajadores(int cuadrillaId, List<Trabajador> trabajadores) {
        String query = "INSERT INTO cuadrilla_trabajador (cuadrilla_id, trabajador_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Trabajador trabajador : trabajadores) {
                stmt.setInt(1, cuadrillaId);
                stmt.setInt(2, trabajador.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
