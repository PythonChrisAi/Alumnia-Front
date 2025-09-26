package com.csb.unit1.javafxalumnia.controller;

import com.csb.unit1.javafxalumnia.model.Alumno;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.InputStream;
import java.util.List;

/**
 * Controlador que carga alumnos en una tabla desde el backend.
 */
public class AlumnoController {

    @FXML
    private TableView<Alumno> alumnoTable;

    @FXML
    private TableColumn<Alumno, Long> idColumn;

    @FXML
    private TableColumn<Alumno, String> nombreColumn;

    @FXML
    private TableColumn<Alumno, String> correoColumn;

    private final ObservableList<Alumno> alumnos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correo"));

        alumnoTable.setItems(alumnos);

        cargarAlumnos();
    }

    private void cargarAlumnos() {
        try {
            // Llamada manual con HttpClient (porque Jackson necesita lista)
            var client = HttpClients.createDefault();
            var request = new HttpGet("http://localhost:8080/api/alumnos");
            var response = client.executeOpen(null, request, null);

            if (response.getCode() == 200) {
                try (InputStream body = response.getEntity().getContent()) {
                    ObjectMapper mapper = new ObjectMapper();
                    List<Alumno> lista = mapper.readValue(body, new TypeReference<List<Alumno>>() {});
                    alumnos.setAll(lista);
                }
            } else {
                System.err.println("Error al cargar alumnos: " + response.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
