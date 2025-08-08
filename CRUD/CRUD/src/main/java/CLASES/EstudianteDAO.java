/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLASES;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author Armando
 */
public class EstudianteDAO {
    private Connection conexion;
    private Conexion conex = new Conexion();

    public EstudianteDAO() {
        conexion = conex.estableceConexion();
    }

    public void cerrarConexion() {
        conex.cerrarConexion();
    }

    // Insertar un nuevo estudiante
    public boolean insertarEstudiante(Estudiante est) {
        String sql = "INSERT INTO usuarios (nombre, apellidos, fksexo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, est.getNombre());
            ps.setString(2, est.getApellidos());
            ps.setInt(3, est.getIdSexo());
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al insertar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Actualizar un estudiante existente
    public boolean actualizarEstudiante(Estudiante est) {
        String sql = "UPDATE usuarios SET nombre = ?, apellidos = ?, fksexo = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, est.getNombre());
            ps.setString(2, est.getApellidos());
            ps.setInt(3, est.getIdSexo());
            ps.setInt(4, est.getId());
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un estudiante
    public boolean eliminarEstudiante(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int res = ps.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Obtener lista de estudiantes completos con nombre del sexo (JOIN)
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.nombre, u.apellidos, u.fksexo, s.sexo FROM usuarios u INNER JOIN sexo s ON u.fksexo = s.id";

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setId(rs.getInt("id"));
                est.setNombre(rs.getString("nombre"));
                est.setApellidos(rs.getString("apellidos"));
                est.setIdSexo(rs.getInt("fksexo"));
                est.setNombreSexo(rs.getString("sexo"));
                lista.add(est);
            }

        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al obtener estudiantes: " + e.getMessage());
        }
        return lista;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
