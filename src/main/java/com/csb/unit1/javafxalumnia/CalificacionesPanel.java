package com.csb.unit1.javafxalumnia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.IntegerStringConverter;

import java.util.List;
import java.util.Map;

public class CalificacionesPanel extends BasePanel {

    private TableView<DataStore.Calificacion> tablaCalificaciones;
    private String materiaSeleccionada = "";

    @Override
    protected VBox buildContent() {
        System.out.println("=== CARGANDO CALIFICACIONES PANEL ===");
        System.out.println("Usuario actual: " + user + " - Rol: " +
                (DataStore.currentUser != null ? DataStore.currentUser.getRole() : "No identificado"));

        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #f5f5f5;");

        // Header según el rol
        String headerText = "";
        if (DataStore.currentUser != null && "PROFESOR".equals(DataStore.currentUser.getRole())) {
            headerText = "SISTEMA DE CALIFICACIONES - PROFESOR";
        } else if (DataStore.currentUser != null && "ALUMNO".equals(DataStore.currentUser.getRole())) {
            headerText = "CONSULTA DE CALIFICACIONES - ALUMNO";
        } else {
            headerText = "SISTEMA DE CALIFICACIONES - ADMINISTRADOR";
        }

        Label header = new Label(headerText);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #2c3e50;");

        // Información del usuario
        VBox infoUsuario = new VBox(10);
        infoUsuario.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        if (DataStore.currentUser != null && "PROFESOR".equals(DataStore.currentUser.getRole())) {
            // Información del profesor
            Label lblDocente = new Label("Docente: " + obtenerNombreProfesor());
            Label lblNombre = new Label(obtenerNombreProfesor().toUpperCase());
            lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            Label lblMatricula = new Label("[Matrícula: " + user + "]");

            infoUsuario.getChildren().addAll(lblDocente, lblNombre, lblMatricula);

        } else if (DataStore.currentUser != null && "ALUMNO".equals(DataStore.currentUser.getRole())) {
            // Información del alumno
            String nombreAlumno = obtenerNombreAlumno();
            Label lblAlumno = new Label("Alumno: " + nombreAlumno);
            Label lblNombre = new Label(nombreAlumno.toUpperCase());
            lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            Label lblMatricula = new Label("[Matrícula: " + user + "]");

            infoUsuario.getChildren().addAll(lblAlumno, lblNombre, lblMatricula);
        }

        HBox controles = new HBox(20);
        controles.setAlignment(Pos.CENTER_LEFT);

        Label lblMateria = new Label("Materia:");
        ComboBox<String> cmbMaterias = new ComboBox<>();

        // Obtener materias según el rol
        ObservableList<String> materias = FXCollections.observableArrayList();

        if (DataStore.currentUser != null && "PROFESOR".equals(DataStore.currentUser.getRole())) {
            // Profesor: solo materias que imparte
            materias = obtenerMateriasDelProfesor();
        } else if (DataStore.currentUser != null && "ALUMNO".equals(DataStore.currentUser.getRole())) {
            // Alumno: solo materias en las que está inscrito
            materias = obtenerMateriasDelAlumno();
        } else {
            // Admin: todas las materias
            materias = FXCollections.observableArrayList(DataStore.getMateriasUnicas());
        }

        if (!materias.isEmpty()) {
            cmbMaterias.setItems(materias);
            materiaSeleccionada = materias.get(0);
            cmbMaterias.setValue(materiaSeleccionada);
        }

        cmbMaterias.setOnAction(e -> {
            materiaSeleccionada = cmbMaterias.getValue();
            actualizarTabla();
        });

        Button btnGuardar = new Button("GUARDAR CALIFICACIONES");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardar.setOnAction(e -> guardarCalificaciones());

        // Solo mostrar botón guardar para profesores y admin
        if (DataStore.currentUser != null && "ALUMNO".equals(DataStore.currentUser.getRole())) {
            btnGuardar.setVisible(false);
        }

        controles.getChildren().addAll(lblMateria, cmbMaterias, btnGuardar);

        tablaCalificaciones = new TableView<>();
        tablaCalificaciones.setEditable(DataStore.currentUser != null &&
                ("PROFESOR".equals(DataStore.currentUser.getRole()) || "ADMIN".equals(DataStore.currentUser.getRole())));
        configurarTabla();

        tablaCalificaciones.setStyle("-fx-background-color: white; -fx-border-radius: 5; -fx-background-radius: 5;");

        content.getChildren().addAll(header, infoUsuario, controles, tablaCalificaciones);

        // Mostrar mensaje si no hay materias
        if (materias.isEmpty()) {
            Label lblSinMaterias = new Label("No tienes materias asignadas.");
            lblSinMaterias.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
            content.getChildren().add(lblSinMaterias);
        }

        return content;
    }

    private String obtenerNombreProfesor() {
        for (DataStore.Alumno alumno : DataStore.alumnos) {
            if (alumno.getMatricula().equals(user)) {
                return alumno.getNombre();
            }
        }
        return "Profesor no identificado";
    }

    private String obtenerNombreAlumno() {
        for (DataStore.Alumno alumno : DataStore.alumnos) {
            if (alumno.getMatricula().equals(user)) {
                return alumno.getNombre();
            }
        }
        return "Alumno no identificado";
    }

    private ObservableList<String> obtenerMateriasDelProfesor() {
        ObservableList<String> materias = FXCollections.observableArrayList();
        for (DataStore.Grupo grupo : DataStore.grupos) {
            if (grupo.getDocente().contains(obtenerNombreProfesor()) &&
                    !materias.contains(grupo.getMateria())) {
                materias.add(grupo.getMateria());
            }
        }
        return materias;
    }

    private ObservableList<String> obtenerMateriasDelAlumno() {
        ObservableList<String> materias = FXCollections.observableArrayList();

        // Obtener materias inscritas
        List<Map<String, Object>> inscripciones = DataStore.obtenerMateriasInscritas(user);
        for (Map<String, Object> inscripcion : inscripciones) {
            String materia = (String) inscripcion.get("materia");
            if (!materias.contains(materia)) {
                materias.add(materia);
            }
        }

        // Si no hay inscripciones, mostrar materias con calificaciones
        if (materias.isEmpty()) {
            for (DataStore.Calificacion cal : DataStore.calificaciones) {
                if (cal.getMatricula().equals(user) && !materias.contains(cal.getMateria())) {
                    materias.add(cal.getMateria());
                }
            }
        }

        return materias;
    }

    private void configurarTabla() {
        tablaCalificaciones.getColumns().clear();
        tablaCalificaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (DataStore.currentUser != null && "ALUMNO".equals(DataStore.currentUser.getRole())) {
            // Vista para alumno
            configurarTablaAlumno();
        } else {
            // Vista para profesor/admin
            configurarTablaProfesor();
        }

        actualizarTabla();
    }

    private void configurarTablaAlumno() {
        // COLUMNA MATERIA
        TableColumn<DataStore.Calificacion, String> colMateria = new TableColumn<>("MATERIA");
        colMateria.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(calificacion.getMateria());
        });
        colMateria.setStyle("-fx-alignment: CENTER_LEFT;");

        // COLUMNA ACTIVIDAD
        TableColumn<DataStore.Calificacion, String> colActividad = new TableColumn<>("ACTIVIDAD");
        colActividad.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(calificacion.getActividad());
        });
        colActividad.setStyle("-fx-alignment: CENTER;");

        // COLUMNA PUNTAJE
        TableColumn<DataStore.Calificacion, String> colPuntaje = new TableColumn<>("CALIFICACIÓN");
        colPuntaje.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(calificacion.getPuntaje() + "/" + calificacion.getMaxPuntos());
        });
        colPuntaje.setStyle("-fx-alignment: CENTER;");

        // COLUMNA PORCENTAJE
        TableColumn<DataStore.Calificacion, String> colPorcentaje = new TableColumn<>("PORCENTAJE");
        colPorcentaje.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            double porcentaje = (calificacion.getPuntaje() * 100.0) / calificacion.getMaxPuntos();
            return new javafx.beans.property.SimpleStringProperty(String.format("%.1f%%", porcentaje));
        });
        colPorcentaje.setStyle("-fx-alignment: CENTER;");

        tablaCalificaciones.getColumns().addAll(colMateria, colActividad, colPuntaje, colPorcentaje);
    }

    private void configurarTablaProfesor() {
        // COLUMNA MATRÍCULA
        TableColumn<DataStore.Calificacion, String> colMatricula = new TableColumn<>("MATRÍCULA");
        colMatricula.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(calificacion.getMatricula());
        });
        colMatricula.setStyle("-fx-alignment: CENTER;");

        // COLUMNA ALUMNO
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

        // COLUMNA ACTIVIDAD
        TableColumn<DataStore.Calificacion, String> colActividad = new TableColumn<>("ACTIVIDAD");
        colActividad.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(calificacion.getActividad());
        });
        colActividad.setStyle("-fx-alignment: CENTER;");

        // COLUMNA PUNTAJE (editable para profesores)
        TableColumn<DataStore.Calificacion, Integer> colPuntaje = new TableColumn<>("CALIFICACIÓN");
        colPuntaje.setCellValueFactory(cellData -> {
            DataStore.Calificacion calificacion = cellData.getValue();
            return new javafx.beans.property.SimpleIntegerProperty(calificacion.getPuntaje()).asObject();
        });

        if (DataStore.currentUser != null &&
                ("PROFESOR".equals(DataStore.currentUser.getRole()) || "ADMIN".equals(DataStore.currentUser.getRole()))) {
            colPuntaje.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colPuntaje.setOnEditCommit(e -> {
                DataStore.Calificacion calificacion = e.getRowValue();
                if (e.getNewValue() >= 0 && e.getNewValue() <= 100) {
                    calificacion.setPuntaje(e.getNewValue());
                    System.out.println("Calificación actualizada: " + calificacion.getMatricula() + " - " + e.getNewValue());
                }
            });
        }
        colPuntaje.setStyle("-fx-alignment: CENTER;");

        tablaCalificaciones.getColumns().addAll(colMatricula, colAlumno, colActividad, colPuntaje);
    }

    private void actualizarTabla() {
        if (materiaSeleccionada == null || materiaSeleccionada.isEmpty()) {
            tablaCalificaciones.setItems(FXCollections.observableArrayList());
            return;
        }

        ObservableList<DataStore.Calificacion> calificacionesFiltradas = FXCollections.observableArrayList();

        if (DataStore.currentUser != null && "ALUMNO".equals(DataStore.currentUser.getRole())) {
            // Alumno: ver todas sus calificaciones de la materia
            for (DataStore.Calificacion cal : DataStore.calificaciones) {
                if (cal.getMatricula().equals(user) && cal.getMateria().equals(materiaSeleccionada)) {
                    calificacionesFiltradas.add(cal);
                }
            }
        } else {
            // Profesor/Admin: ver calificaciones de todos los alumnos en esa materia
            for (DataStore.Calificacion cal : DataStore.calificaciones) {
                if (cal.getMateria().equals(materiaSeleccionada)) {
                    calificacionesFiltradas.add(cal);
                }
            }
        }

        tablaCalificaciones.setItems(calificacionesFiltradas);
        System.out.println("📊 Tabla actualizada: " + calificacionesFiltradas.size() + " calificaciones para " + materiaSeleccionada);
    }

    private void guardarCalificaciones() {
        int calificacionesGuardadas = 0;

        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMateria().equals(materiaSeleccionada)) {
                boolean exito = DataStore.actualizarCalificacionBackend(cal);
                if (exito) {
                    calificacionesGuardadas++;
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardado exitoso");
        alert.setHeaderText(null);
        alert.setContentText(calificacionesGuardadas + " calificaciones han sido guardadas correctamente.");
        alert.showAndWait();

        System.out.println("💾 " + calificacionesGuardadas + " calificaciones guardadas para la materia: " + materiaSeleccionada);
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return btnCalificaciones;
    }
}