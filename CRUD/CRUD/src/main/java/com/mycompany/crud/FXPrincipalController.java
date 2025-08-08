/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.crud;

import CLASES.Estudiante;
import CLASES.EstudianteDAO;
import CLASES.Usuarios;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Armando
 */
public class FXPrincipalController implements Initializable {

    @FXML private ComboBox<String> cbsexo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TableView<Estudiante> tvEstudiantes;
    @FXML private TableColumn<Estudiante, Integer> colId;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colApellidos;
    @FXML private TableColumn<Estudiante, String> colSexo;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;

    private Map<String, Integer> mapaSexo = new HashMap<>(); // Para mapear nombre a idSexo
    private EstudianteDAO estudianteDao;
    private ObservableList<Estudiante> listaEstudiantes;
    private Estudiante estudianteSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estudianteDao = new EstudianteDAO();

        cargarComboSexo();
        configurarTabla();
        cargarEstudiantes();

        btnGuardar.setOnAction(e -> guardarEstudiante());
        btnEditar.setOnAction(e -> editarEstudiante());
        btnEliminar.setOnAction(e -> eliminarEstudiante());

        // Selecciona un estudiante de la tabla para editar
        tvEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                estudianteSeleccionado = newSel;
                txtNombre.setText(newSel.getNombre());
                txtApellidos.setText(newSel.getApellidos());

                // Buscar el nombre correspondiente de sexo para seleccionarlo en el ComboBox
                cbsexo.setValue(newSel.getNombreSexo());
            }
        });
    }

    private void cargarComboSexo() {
        Usuarios usuarios = new Usuarios();
        cbsexo.getItems().clear();
        cbsexo.setValue("Seleccione sexo");
        mapaSexo.clear();

        // Usamos la versión modificada de mostrarSexoCombo que devuelva la información
        // Pero aquí, para simplicidad, repetimos la consulta y llenamos el mapa

        try {
            var con = new CLASES.Conexion().estableceConexion();
            var st = con.createStatement();
            var rs = st.executeQuery("SELECT * FROM sexo");
            while (rs.next()) {
                String nombre = rs.getString("sexo");
                int id = rs.getInt("id");
                cbsexo.getItems().add(nombre);
                mapaSexo.put(nombre, id);
            }
            con.close();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo cargar los sexos: " + e.getMessage());
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colApellidos.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidos()));
        colSexo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombreSexo()));
    }

    private void cargarEstudiantes() {
        List<Estudiante> estudiantes = estudianteDao.obtenerEstudiantes();
        listaEstudiantes = FXCollections.observableArrayList(estudiantes);
        tvEstudiantes.setItems(listaEstudiantes);
    }

    private void guardarEstudiante() {
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String sexoSeleccionado = cbsexo.getValue();

        if (nombre.isEmpty() || apellidos.isEmpty() || sexoSeleccionado.equals("Seleccione sexo")) {
            mostrarAlerta("Validación", "Por favor llena todos los campos.");
            return;
        }

        int idSexo = mapaSexo.get(sexoSeleccionado);
        Estudiante est = new Estudiante(nombre, apellidos, idSexo);

        if (estudianteDao.insertarEstudiante(est)) {
            mostrarAlerta("Éxito", "Estudiante guardado correctamente.");
            limpiarCampos();
            cargarEstudiantes();
        }
    }

    private void editarEstudiante() {
        if (estudianteSeleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un estudiante de la tabla.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String sexoSeleccionado = cbsexo.getValue();

        if (nombre.isEmpty() || apellidos.isEmpty() || sexoSeleccionado.equals("Seleccione sexo")) {
            mostrarAlerta("Validación", "Por favor llena todos los campos.");
            return;
        }

        int idSexo = mapaSexo.get(sexoSeleccionado);

        estudianteSeleccionado.setNombre(nombre);
        estudianteSeleccionado.setApellidos(apellidos);
        estudianteSeleccionado.setIdSexo(idSexo);

        if (estudianteDao.actualizarEstudiante(estudianteSeleccionado)) {
            mostrarAlerta("Éxito", "Estudiante actualizado correctamente.");
            limpiarCampos();
            cargarEstudiantes();
            estudianteSeleccionado = null;
        }
    }

    private void eliminarEstudiante() {
        if (estudianteSeleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un estudiante de la tabla.");
            return;
        }

        if (estudianteDao.eliminarEstudiante(estudianteSeleccionado.getId())) {
            mostrarAlerta("Éxito", "Estudiante eliminado correctamente.");
            limpiarCampos();
            cargarEstudiantes();
            estudianteSeleccionado = null;
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidos.clear();
        cbsexo.setValue("Seleccione sexo");
        tvEstudiantes.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
