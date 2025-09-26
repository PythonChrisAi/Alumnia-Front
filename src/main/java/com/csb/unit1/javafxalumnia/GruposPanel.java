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

public class GruposPanel extends BasePanel {

    private TableView<AlumnoGrupo> tablaAlumnos;
    private String grupoSeleccionado = "GRUPO A";

    // Clase interna para representar alumnos en grupos
    public static class AlumnoGrupo {
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

        Label lblNombre = new Label("JORDI AZAEL MARTÍNEZ JIMENEZ");
        lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label lblMatricula = new Label("Matrícula: 01928321");
        Label lblCampus = new Label("Campus: SN");

        infoDocente.getChildren().addAll(lblNombre, lblMatricula, lblCampus);

        // Panel de selección de grupo
        VBox seleccionGrupo = new VBox(10);
        seleccionGrupo.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblSeleccion = new Label("SELECCIONAR GRUPO");
        lblSeleccion.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Crear acordeón para grupos
        Accordion accordionGrupos = new Accordion();
        accordionGrupos.setPrefWidth(800);

        // Grupo A
        TitledPane grupoA = crearGrupoTitledPane("GRUPO A", "Programación Orientada a Objetos", "GRUPO A-1234", "ENERO-AGOSTO");
        TitledPane grupoB = crearGrupoTitledPane("GRUPO B", "Base de Datos", "GRUPO B-1234", "ENERO-AGOSTO");
        TitledPane grupoC = crearGrupoTitledPane("GRUPO C", "Redes de Computadoras", "GRUPO C-1234", "ENERO-AGOSTO");
        TitledPane grupoD = crearGrupoTitledPane("GRUPO D", "Matemáticas Discretas", "GRUPO D-1234", "ENERO-AGOSTO");

        accordionGrupos.getPanes().addAll(grupoA, grupoB, grupoC, grupoD);

        // Expandir el primer grupo por defecto
        accordionGrupos.setExpandedPane(grupoA);

        seleccionGrupo.getChildren().addAll(lblSeleccion, accordionGrupos);

        // Tabla de alumnos del grupo seleccionado
        VBox tablaContainer = new VBox(10);
        tablaContainer.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 5; -fx-background-radius: 5;");

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

        content.getChildren().addAll(header, infoDocente, seleccionGrupo, tablaContainer);
        return content;
    }

    private TitledPane crearGrupoTitledPane(String nombreGrupo, String materia, String clave, String periodo) {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);

        // Encabezados
        grid.add(new Label("MATERIA"), 0, 0);
        grid.add(new Label("CLAVE"), 1, 0);
        grid.add(new Label("PERIODO"), 2, 0);

        // Datos
        Label lblMateria = new Label(materia);
        lblMateria.setStyle("-fx-font-weight: bold;");
        Label lblClave = new Label(clave);
        lblClave.setStyle("-fx-font-weight: bold;");
        Label lblPeriodo = new Label(periodo);
        lblPeriodo.setStyle("-fx-font-weight: bold;");

        grid.add(lblMateria, 0, 1);
        grid.add(lblClave, 1, 1);
        grid.add(lblPeriodo, 2, 1);

        Button btnVerAlumnos = new Button("VER ALUMNOS DE ESTE GRUPO");
        btnVerAlumnos.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnVerAlumnos.setOnAction(e -> {
            grupoSeleccionado = nombreGrupo;
            actualizarTablaAlumnos();
        });

        contenido.getChildren().addAll(grid, btnVerAlumnos);

        TitledPane pane = new TitledPane(nombreGrupo, contenido);
        pane.setStyle("-fx-font-weight: bold;");
        return pane;
    }

    private void configurarTablaAlumnos() {
        tablaAlumnos.getColumns().clear();
        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Columna Matrícula
        TableColumn<AlumnoGrupo, String> colMatricula = new TableColumn<>("MATRÍCULA");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMatricula.setStyle("-fx-alignment: CENTER;");

        // Columna Nombre
        TableColumn<AlumnoGrupo, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setStyle("-fx-alignment: CENTER_LEFT;");

        // Columna Actividad 1
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

        // Columna Actividad 2
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

        // Columna Promedio
        TableColumn<AlumnoGrupo, String> colPromedio = new TableColumn<>("PROMEDIO");
        colPromedio.setCellValueFactory(cellData -> {
            AlumnoGrupo alumno = cellData.getValue();
            double promedio = (alumno.getActividad1() + alumno.getActividad2()) / 2.0;
            return new javafx.beans.property.SimpleStringProperty(String.format("%.1f", promedio));
        });
        colPromedio.setStyle("-fx-alignment: CENTER;");

        // Columna Estado
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
        actualizarTablaAlumnos();
    }

    private void actualizarTablaAlumnos() {
        ObservableList<AlumnoGrupo> alumnosGrupo = FXCollections.observableArrayList();

        // Datos de ejemplo - en una aplicación real esto vendría de una base de datos
        switch (grupoSeleccionado) {
            case "GRUPO A":
                alumnosGrupo.addAll(
                        new AlumnoGrupo("01928321", "Jordi Azael Martínez Jimenez", "Programación Orientada a Objetos", "GRUPO A", 85, 92),
                        new AlumnoGrupo("01928322", "Diego Armando Zamora Jurado", "Programación Orientada a Objetos", "GRUPO A", 78, 85),
                        new AlumnoGrupo("01928323", "Luis Gabriel Treviño Mendiola", "Programación Orientada a Objetos", "GRUPO A", 92, 88),
                        new AlumnoGrupo("01928324", "Christopher Sustaita Barrón", "Programación Orientada a Objetos", "GRUPO A", 65, 72)
                );
                break;
            case "GRUPO B":
                alumnosGrupo.addAll(
                        new AlumnoGrupo("01928325", "Ana María López García", "Base de Datos", "GRUPO B", 88, 91),
                        new AlumnoGrupo("01928326", "Carlos Eduardo Ruiz Mendoza", "Base de Datos", "GRUPO B", 76, 82)
                );
                break;
            case "GRUPO C":
                alumnosGrupo.addAll(
                        new AlumnoGrupo("01928327", "María Fernanda Hernández", "Redes de Computadoras", "GRUPO C", 94, 89),
                        new AlumnoGrupo("01928328", "Roberto Carlos Silva", "Redes de Computadoras", "GRUPO C", 81, 79)
                );
                break;
            case "GRUPO D":
                alumnosGrupo.addAll(
                        new AlumnoGrupo("01928329", "Laura Patricia Morales", "Matemáticas Discretas", "GRUPO D", 72, 68),
                        new AlumnoGrupo("01928330", "Javier Antonio Reyes", "Matemáticas Discretas", "GRUPO D", 85, 90)
                );
                break;
        }

        tablaAlumnos.setItems(alumnosGrupo);

        // Actualizar título de la tabla
        if (tablaAlumnos.getParent() instanceof VBox) {
            VBox parent = (VBox) tablaAlumnos.getParent();
            if (parent.getChildren().get(0) instanceof Label) {
                ((Label) parent.getChildren().get(0)).setText("ALUMNOS DEL " + grupoSeleccionado);
            }
        }
    }

    private void actualizarCalificacionEnDataStore(AlumnoGrupo alumno, String actividad, int puntaje) {
        // Buscar y actualizar la calificación en DataStore
        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMatricula().equals(alumno.getMatricula()) &&
                    cal.getMateria().equals(alumno.getMateria()) &&
                    cal.getActividad().equals(actividad)) {
                cal.setPuntaje(puntaje);
                break;
            }
        }
    }

    private void guardarCalificaciones() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guardado exitoso");
        alert.setHeaderText(null);
        alert.setContentText("Las calificaciones del " + grupoSeleccionado + " han sido guardadas correctamente.\nLos cambios se reflejarán en el kárdex de los alumnos.");
        alert.showAndWait();

        System.out.println("Calificaciones guardadas para el grupo: " + grupoSeleccionado);
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex, Button btnBaja,
                                     Button btnInscripcion, Button btnEditarKardex,
                                     Button btnCalificaciones, Button btnGrupos, Button btnSalir) {
        return btnGrupos;
    }
}
//ayudamicabeza