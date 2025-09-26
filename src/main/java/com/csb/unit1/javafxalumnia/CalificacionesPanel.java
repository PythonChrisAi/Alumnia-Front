package com.csb.unit1.javafxalumnia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.IntegerStringConverter;

public class CalificacionesPanel extends BasePanel {

    private TableView<DataStore.Calificacion> tablaCalificaciones;
    private String materiaSeleccionada = "Programación Orientada a Objetos";

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #f5f5f5;");

        Label header = new Label("SISTEMA DE CALIFICACIONES - GRUPO A");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #2c3e50;");

        VBox infoDocente = new VBox(10);
        infoDocente.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblDocente = new Label("Docente: Jordi Azael Martinez Jimenez");
        Label lblNombre = new Label("JORDI AZAEL MARTÍNEZ JIMENEZ");
        lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label lblMatricula = new Label("[Matrícula: 01928321]");
        Label lblCampus = new Label("[Campus: SN]");

        infoDocente.getChildren().addAll(lblDocente, lblNombre, lblMatricula, lblCampus);

        HBox controles = new HBox(20);
        controles.setAlignment(Pos.CENTER_LEFT);

        Label lblMateria = new Label("Materia:");
        ComboBox<String> cmbMaterias = new ComboBox<>();
        cmbMaterias.getItems().addAll("Programación Orientada a Objetos", "Base de Datos", "Redes", "Matemáticas");
        cmbMaterias.setValue(materiaSeleccionada);

        cmbMaterias.setOnAction(e -> {
            materiaSeleccionada = cmbMaterias.getValue();
            actualizarTabla();
        });

        Button btnGuardar = new Button("GUARDAR CALIFICACIONES");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardar.setOnAction(e -> guardarCalificaciones());

        controles.getChildren().addAll(lblMateria, cmbMaterias, btnGuardar);

        tablaCalificaciones = new TableView<>();
        tablaCalificaciones.setEditable(true);
        configurarTabla();

        tablaCalificaciones.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");

        content.getChildren().addAll(header, infoDocente, controles, tablaCalificaciones);
        return content;
    }

    private void configurarTabla() {
        tablaCalificaciones.getColumns().clear();
        tablaCalificaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<DataStore.Calificacion, String> colMatricula = new TableColumn<>("MATRÍCULA");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMatricula.setStyle("-fx-alignment: CENTER;");

        TableColumn<DataStore.Calificacion, String> colAlumno = new TableColumn<>("ALUMNO");
        colAlumno.setCellValueFactory(cellData -> {
            String matricula = cellData.getValue().getMatricula();
            for (DataStore.Alumno alumno : DataStore.alumnos) {
                if (alumno.getMatricula().equals(matricula)) {
                    return new javafx.beans.property.SimpleStringProperty(alumno.getNombre());
                }
            }
            return new javafx.beans.property.SimpleStringProperty("No encontrado");
        });
        colAlumno.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<DataStore.Calificacion, Integer> colAct1 = new TableColumn<>("ACTIVIDAD 1");
        colAct1.setCellValueFactory(new PropertyValueFactory<>("puntaje"));
        colAct1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAct1.setOnEditCommit(e -> {
            DataStore.Calificacion calificacion = e.getRowValue();
            if (e.getNewValue() >= 0 && e.getNewValue() <= 100) {
                calificacion.setPuntaje(e.getNewValue());
            }
        });
        colAct1.setStyle("-fx-alignment: CENTER;");

        TableColumn<DataStore.Calificacion, Integer> colAct2 = new TableColumn<>("ACTIVIDAD 2");
        colAct2.setCellValueFactory(cellData -> {
            String matricula = cellData.getValue().getMatricula();
            for (DataStore.Calificacion cal : DataStore.calificaciones) {
                if (cal.getMatricula().equals(matricula) &&
                        cal.getMateria().equals(materiaSeleccionada) &&
                        cal.getActividad().equals("Actividad 2")) {
                    return new javafx.beans.property.SimpleIntegerProperty(cal.getPuntaje()).asObject();
                }
            }
            return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
        });
        colAct2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAct2.setOnEditCommit(e -> {
            String matricula = tablaCalificaciones.getItems().get(e.getTablePosition().getRow()).getMatricula();
            for (DataStore.Calificacion cal : DataStore.calificaciones) {
                if (cal.getMatricula().equals(matricula) &&
                        cal.getMateria().equals(materiaSeleccionada) &&
                        cal.getActividad().equals("Actividad 2")) {
                    if (e.getNewValue() >= 0 && e.getNewValue() <= 100) {
                        cal.setPuntaje(e.getNewValue());
                    }
                    break;
                }
            }
        });
        colAct2.setStyle("-fx-alignment: CENTER;");

        tablaCalificaciones.getColumns().addAll(colMatricula, colAlumno, colAct1, colAct2);
        actualizarTabla();
    }

    private void actualizarTabla() {
        ObservableList<DataStore.Calificacion> calificacionesFiltradas = FXCollections.observableArrayList();

        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMateria().equals(materiaSeleccionada) &&
                    cal.getActividad().equals("Actividad 1")) {
                calificacionesFiltradas.add(cal);
            }
        }

        tablaCalificaciones.setItems(calificacionesFiltradas);
    }

    private void guardarCalificaciones() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardado exitoso");
        alert.setHeaderText(null);
        alert.setContentText("Las calificaciones han sido guardadas correctamente.\nLos cambios se reflejarán en el kárdex de los alumnos.");
        alert.showAndWait();

        System.out.println("Calificaciones guardadas para la materia: " + materiaSeleccionada);
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {

        return null;

    }
}
