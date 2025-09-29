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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GruposPanel extends BasePanel {

    private TableView<AlumnoGrupo> tablaAlumnos;
    private String grupoSeleccionado = "";

    // Clase interna para representar alumnos en grupos
    static class AlumnoGrupo {
        private String matricula;
        private String nombre;
        private String materia;
        private String grupo;
        private int actividad1;
        private int actividad2;

        public AlumnoGrupo(String matricula, String nombre, String materia, String grupo, int actividad1, int actividad2) {
            this.matricula = matricula;
            this.nombre = nombre;
            this.materia = materia;
            this.grupo = grupo;
            this.actividad1 = actividad1;
            this.actividad2 = actividad2;
        }

        // Getters
        public String getMatricula() { return matricula; }
        public String getNombre() { return nombre; }
        public String getMateria() { return materia; }
        public String getGrupo() { return grupo; }
        public int getActividad1() { return actividad1; }
        public int getActividad2() { return actividad2; }

        // Setters
        public void setActividad1(int actividad1) { this.actividad1 = actividad1; }
        public void setActividad2(int actividad2) { this.actividad2 = actividad2; }
    }

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        Label header = new Label("CONSULTA DE GRUPOS - DOCENTE");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-text-fill: #2c3e50;");

        // Información del docente
        VBox infoDocente = new VBox(10);
        infoDocente.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        String nombreProfesor = obtenerNombreProfesor();
        Label lblNombre = new Label(nombreProfesor.toUpperCase());
        lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label lblMatricula = new Label("Matrícula: " + user);
        Label lblRol = new Label("Rol: PROFESOR");

        infoDocente.getChildren().addAll(lblNombre, lblMatricula, lblRol);

        // Panel de selección de grupo - SOLO GRUPOS DEL PROFESOR
        VBox seleccionGrupo = new VBox(10);
        seleccionGrupo.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblSeleccion = new Label("MIS GRUPOS ASIGNADOS");
        lblSeleccion.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Crear acordeón solo para grupos del profesor
        Accordion accordionGrupos = new Accordion();
        accordionGrupos.setPrefWidth(800);

        List<DataStore.Grupo> gruposProfesor = obtenerGruposDelProfesor();

        if (gruposProfesor.isEmpty()) {
            Label lblSinGrupos = new Label("No tienes grupos asignados.");
            lblSinGrupos.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
            seleccionGrupo.getChildren().addAll(lblSeleccion, lblSinGrupos);
        } else {
            for (DataStore.Grupo grupo : gruposProfesor) {
                TitledPane grupoPane = crearGrupoTitledPane(grupo);
                accordionGrupos.getPanes().add(grupoPane);
            }

            // Expandir el primer grupo por defecto
            if (!accordionGrupos.getPanes().isEmpty()) {
                accordionGrupos.setExpandedPane(accordionGrupos.getPanes().get(0));
                grupoSeleccionado = gruposProfesor.get(0).getNombre();
            }

            seleccionGrupo.getChildren().addAll(lblSeleccion, accordionGrupos);
        }

        // Tabla de alumnos del grupo seleccionado
        VBox tablaContainer = new VBox(10);
        tablaContainer.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        if (!gruposProfesor.isEmpty()) {
            Label lblAlumnos = new Label("ALUMNOS DEL " + grupoSeleccionado);
            lblAlumnos.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            tablaAlumnos = new TableView<>();
            tablaAlumnos.setEditable(true);
            tablaAlumnos.setPrefHeight(300);
            configurarTablaAlumnos();

            Button btnGuardar = new Button("GUARDAR CALIFICACIONES");
            btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
            btnGuardar.setOnAction(e -> guardarCalificaciones());

            tablaContainer.getChildren().addAll(lblAlumnos, tablaAlumnos, btnGuardar);
        } else {
            Label lblSinAlumnos = new Label("Selecciona un grupo para ver los alumnos.");
            lblSinAlumnos.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
            tablaContainer.getChildren().add(lblSinAlumnos);
        }

        content.getChildren().addAll(header, infoDocente, seleccionGrupo, tablaContainer);
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

    private List<DataStore.Grupo> obtenerGruposDelProfesor() {
        List<DataStore.Grupo> gruposProfesor = new ArrayList<>();
        String nombreProfesor = obtenerNombreProfesor();

        for (DataStore.Grupo grupo : DataStore.grupos) {
            if (grupo.getDocente().equals(nombreProfesor)) {
                gruposProfesor.add(grupo);
            }
        }
        return gruposProfesor;
    }

    private TitledPane crearGrupoTitledPane(DataStore.Grupo grupo) {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);

        // Encabezados
        grid.add(new Label("MATERIA"), 0, 0);
        grid.add(new Label("CLAVE"), 1, 0);
        grid.add(new Label("PERIODO"), 2, 0);
        grid.add(new Label("CUPO"), 3, 0);
        grid.add(new Label("INSCRITOS"), 4, 0);

        // Datos
        Label lblMateria = new Label(grupo.getMateria());
        lblMateria.setStyle("-fx-font-weight: bold;");
        Label lblClave = new Label(grupo.getClave());
        lblClave.setStyle("-fx-font-weight: bold;");
        Label lblPeriodo = new Label(grupo.getPeriodo());
        lblPeriodo.setStyle("-fx-font-weight: bold;");
        Label lblCupo = new Label(String.valueOf(grupo.getCupo()));
        lblCupo.setStyle("-fx-font-weight: bold;");
        Label lblInscritos = new Label(String.valueOf(grupo.getInscritos()));
        lblInscritos.setStyle("-fx-font-weight: bold;");

        grid.add(lblMateria, 0, 1);
        grid.add(lblClave, 1, 1);
        grid.add(lblPeriodo, 2, 1);
        grid.add(lblCupo, 3, 1);
        grid.add(lblInscritos, 4, 1);

        Button btnVerAlumnos = new Button("VER ALUMNOS DE ESTE GRUPO");
        btnVerAlumnos.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnVerAlumnos.setOnAction(e -> {
            grupoSeleccionado = grupo.getNombre();
            actualizarTablaAlumnos(grupo);
        });

        contenido.getChildren().addAll(grid, btnVerAlumnos);

        TitledPane pane = new TitledPane(grupo.getNombre() + " - " + grupo.getMateria(), contenido);
        pane.setStyle("-fx-font-weight: bold;");
        return pane;
    }

    private void configurarTablaAlumnos() {
        tablaAlumnos.getColumns().clear();
        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // COLUMNA MATRÍCULA
        TableColumn<AlumnoGrupo, String> colMatricula = new TableColumn<>("MATRÍCULA");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMatricula.setStyle("-fx-alignment: CENTER;");

        // COLUMNA NOMBRE
        TableColumn<AlumnoGrupo, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setStyle("-fx-alignment: CENTER_LEFT;");

        // COLUMNA ACTIVIDAD 1
        TableColumn<AlumnoGrupo, Integer> colAct1 = new TableColumn<>("ACTIVIDAD 1");
        colAct1.setCellValueFactory(new PropertyValueFactory<>("actividad1"));
        colAct1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAct1.setOnEditCommit(e -> {
            AlumnoGrupo alumno = e.getRowValue();
            if (e.getNewValue() >= 0 && e.getNewValue() <= 100) {
                alumno.setActividad1(e.getNewValue());
                actualizarCalificacionEnDataStore(alumno, "Actividad 1", e.getNewValue());
            }
        });
        colAct1.setStyle("-fx-alignment: CENTER;");

        // COLUMNA ACTIVIDAD 2
        TableColumn<AlumnoGrupo, Integer> colAct2 = new TableColumn<>("ACTIVIDAD 2");
        colAct2.setCellValueFactory(new PropertyValueFactory<>("actividad2"));
        colAct2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAct2.setOnEditCommit(e -> {
            AlumnoGrupo alumno = e.getRowValue();
            if (e.getNewValue() >= 0 && e.getNewValue() <= 100) {
                alumno.setActividad2(e.getNewValue());
                actualizarCalificacionEnDataStore(alumno, "Actividad 2", e.getNewValue());
            }
        });
        colAct2.setStyle("-fx-alignment: CENTER;");

        // COLUMNA PROMEDIO
        TableColumn<AlumnoGrupo, String> colPromedio = new TableColumn<>("PROMEDIO");
        colPromedio.setCellValueFactory(cellData -> {
            AlumnoGrupo alumno = cellData.getValue();
            double promedio = (alumno.getActividad1() + alumno.getActividad2()) / 2.0;
            return new javafx.beans.property.SimpleStringProperty(String.format("%.1f", promedio));
        });
        colPromedio.setStyle("-fx-alignment: CENTER;");

        // COLUMNA ESTADO
        TableColumn<AlumnoGrupo, String> colEstado = new TableColumn<>("ESTADO");
        colEstado.setCellValueFactory(cellData -> {
            AlumnoGrupo alumno = cellData.getValue();
            double promedio = (alumno.getActividad1() + alumno.getActividad2()) / 2.0;
            String estado = promedio >= 70 ? "APROBADO" : "REPROBADO";
            return new javafx.beans.property.SimpleStringProperty(estado);
        });
        colEstado.setCellFactory(column -> new TableCell<AlumnoGrupo, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("APROBADO".equals(item)) {
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    }
                }
            }
        });
        colEstado.setStyle("-fx-alignment: CENTER;");

        tablaAlumnos.getColumns().addAll(colMatricula, colNombre, colAct1, colAct2, colPromedio, colEstado);
    }

    private void actualizarTablaAlumnos(DataStore.Grupo grupo) {
        ObservableList<AlumnoGrupo> alumnosGrupo = FXCollections.observableArrayList();

        // Obtener alumnos inscritos en este grupo
        List<Map<String, Object>> inscripciones = DataStore.obtenerInscripcionesPorGrupo(grupo.getId());

        for (Map<String, Object> inscripcion : inscripciones) {
            String matricula = (String) inscripcion.get("matricula");
            String nombreAlumno = obtenerNombreAlumno(matricula);

            // Obtener calificaciones del alumno en esta materia
            int act1 = obtenerCalificacionActividad(matricula, grupo.getMateria(), "Actividad 1");
            int act2 = obtenerCalificacionActividad(matricula, grupo.getMateria(), "Actividad 2");

            alumnosGrupo.add(new AlumnoGrupo(matricula, nombreAlumno, grupo.getMateria(), grupo.getNombre(), act1, act2));
        }

        tablaAlumnos.setItems(alumnosGrupo);

        // Actualizar título de la tabla
        if (tablaAlumnos.getParent() instanceof VBox) {
            VBox parent = (VBox) tablaAlumnos.getParent();
            if (!parent.getChildren().isEmpty() && parent.getChildren().get(0) instanceof Label) {
                ((Label) parent.getChildren().get(0)).setText("ALUMNOS DEL " + grupo.getNombre() + " - " + grupo.getMateria());
            }
        }
    }

    private String obtenerNombreAlumno(String matricula) {
        for (DataStore.Alumno alumno : DataStore.alumnos) {
            if (alumno.getMatricula().equals(matricula)) {
                return alumno.getNombre();
            }
        }
        return "No encontrado";
    }

    private int obtenerCalificacionActividad(String matricula, String materia, String actividad) {
        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMatricula().equals(matricula) &&
                    cal.getMateria().equals(materia) &&
                    cal.getActividad().equals(actividad)) {
                return cal.getPuntaje();
            }
        }
        return 0; // Si no existe, retornar 0
    }

    private void actualizarCalificacionEnDataStore(AlumnoGrupo alumno, String actividad, int puntaje) {
        // Buscar y actualizar la calificación en DataStore
        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMatricula().equals(alumno.getMatricula()) &&
                    cal.getMateria().equals(alumno.getMateria()) &&
                    cal.getActividad().equals(actividad)) {
                cal.setPuntaje(puntaje);
                // Actualizar en backend
                DataStore.actualizarCalificacionBackend(cal);
                break;
            }
        }
    }

    private void guardarCalificaciones() {
        int calificacionesGuardadas = 0;

        // Guardar todas las calificaciones modificadas
        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            boolean exito = DataStore.actualizarCalificacionBackend(cal);
            if (exito) {
                calificacionesGuardadas++;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardado exitoso");
        alert.setHeaderText(null);
        alert.setContentText(calificacionesGuardadas + " calificaciones han sido guardadas correctamente.");
        alert.showAndWait();

        System.out.println("💾 " + calificacionesGuardadas + " calificaciones guardadas");
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex, Button btnBaja,
                                     Button btnInscripcion, Button btnEditarKardex,
                                     Button btnCalificaciones, Button btnGrupos, Button btnSalir) {
        return btnGrupos;
    }
}