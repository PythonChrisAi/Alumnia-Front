package com.csb.unit1.javafxalumnia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.Map;

public class InscripcionPanel extends BasePanel {

    private TableView<GrupoCompleto> tablaGrupos;
    private String materiaSeleccionada = "Programación Orientada a Objetos";

    // Clase para mostrar grupos completos
    public static class GrupoCompleto {
        private String id;
        private String nombre;
        private String materia;
        private String docente;
        private String periodo;
        private String clave;
        private int cupo;
        private int inscritos;
        private int disponibles;

        public GrupoCompleto(DataStore.Grupo grupo) {
            this.id = grupo.getId();
            this.nombre = grupo.getNombre();
            this.materia = grupo.getMateria();
            this.docente = grupo.getDocente();
            this.periodo = grupo.getPeriodo();
            this.clave = grupo.getClave();
            this.cupo = 30; // Valor por defecto
            this.inscritos = 0; // Valor por defecto
            this.disponibles = this.cupo - this.inscritos;
        }

        // Getters
        public String getId() { return id; }
        public String getNombre() { return nombre; }
        public String getMateria() { return materia; }
        public String getDocente() { return docente; }
        public String getPeriodo() { return periodo; }
        public String getClave() { return clave; }
        public int getCupo() { return cupo; }
        public int getInscritos() { return inscritos; }
        public int getDisponibles() { return disponibles; }
        public String getEstado() { return disponibles > 0 ? "DISPONIBLE" : "LLENO"; }
    }

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label header = new Label("INSCRIPCIÓN A MATERIAS");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        header.setStyle("-fx-underline: true;");

        // Panel de información del alumno
        VBox infoAlumno = new VBox(10);
        infoAlumno.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 15; -fx-border-radius: 5;");

        String nombreAlumno = "No identificado";
        for (DataStore.Alumno alumno : DataStore.alumnos) {
            if (alumno.getMatricula().equals(user)) {
                nombreAlumno = alumno.getNombre();
                break;
            }
        }

        Label lblAlumno = new Label("Alumno: " + nombreAlumno);
        Label lblMatricula = new Label("Matrícula: " + user);
        lblAlumno.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        lblMatricula.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        infoAlumno.getChildren().addAll(lblAlumno, lblMatricula);

        // Controles de búsqueda
        HBox controles = new HBox(20);
        controles.setAlignment(Pos.CENTER_LEFT);

        Label lblMateria = new Label("Materia:");
        ComboBox<String> cmbMaterias = new ComboBox<>();

        // Obtener materias únicas
        ObservableList<String> materias = FXCollections.observableArrayList();
        for (DataStore.Grupo grupo : DataStore.grupos) {
            if (!materias.contains(grupo.getMateria())) {
                materias.add(grupo.getMateria());
            }
        }

        cmbMaterias.setItems(materias);
        cmbMaterias.setValue(materiaSeleccionada);

        cmbMaterias.setOnAction(e -> {
            materiaSeleccionada = cmbMaterias.getValue();
            actualizarTabla();
        });

        Button btnMisInscripciones = new Button("VER MIS INSCRIPCIONES");
        btnMisInscripciones.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        btnMisInscripciones.setOnAction(e -> mostrarMisInscripciones());

        controles.getChildren().addAll(lblMateria, cmbMaterias, btnMisInscripciones);

        // Tabla de grupos
        tablaGrupos = new TableView<>();
        tablaGrupos.setPrefHeight(400);
        configurarTabla();

        content.getChildren().addAll(header, infoAlumno, controles, tablaGrupos);
        return content;
    }

    private void configurarTabla() {
        tablaGrupos.getColumns().clear();
        tablaGrupos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<GrupoCompleto, String> colGrupo = new TableColumn<>("GRUPO");
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGrupo.setStyle("-fx-alignment: CENTER;");

        TableColumn<GrupoCompleto, String> colDocente = new TableColumn<>("DOCENTE");
        colDocente.setCellValueFactory(new PropertyValueFactory<>("docente"));
        colDocente.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<GrupoCompleto, String> colPeriodo = new TableColumn<>("PERIODO");
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        colPeriodo.setStyle("-fx-alignment: CENTER;");

        TableColumn<GrupoCompleto, String> colClave = new TableColumn<>("CLAVE");
        colClave.setCellValueFactory(new PropertyValueFactory<>("clave"));
        colClave.setStyle("-fx-alignment: CENTER;");

        TableColumn<GrupoCompleto, Integer> colCupo = new TableColumn<>("CUPO");
        colCupo.setCellValueFactory(new PropertyValueFactory<>("cupo"));
        colCupo.setStyle("-fx-alignment: CENTER;");

        TableColumn<GrupoCompleto, Integer> colDisponibles = new TableColumn<>("DISPONIBLES");
        colDisponibles.setCellValueFactory(new PropertyValueFactory<>("disponibles"));
        colDisponibles.setStyle("-fx-alignment: CENTER;");

        TableColumn<GrupoCompleto, String> colEstado = new TableColumn<>("ESTADO");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setCellFactory(column -> new TableCell<GrupoCompleto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("DISPONIBLE".equals(item)) {
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    }
                }
            }
        });
        colEstado.setStyle("-fx-alignment: CENTER;");

        TableColumn<GrupoCompleto, Void> colInscribir = new TableColumn<>("ACCIÓN");
        colInscribir.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("INSCRIBIRSE");

            {
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                btn.setOnAction(event -> {
                    GrupoCompleto grupo = getTableView().getItems().get(getIndex());
                    inscribirEnGrupo(grupo);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    GrupoCompleto grupo = getTableView().getItems().get(getIndex());
                    if (grupo.getDisponibles() > 0) {
                        btn.setDisable(false);
                        btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                    } else {
                        btn.setDisable(true);
                        btn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");
                    }
                    setGraphic(btn);
                }
            }
        });
        colInscribir.setStyle("-fx-alignment: CENTER;");

        tablaGrupos.getColumns().addAll(colGrupo, colDocente, colPeriodo, colClave,
                colCupo, colDisponibles, colEstado, colInscribir);
        actualizarTabla();
    }

    private void actualizarTabla() {
        ObservableList<GrupoCompleto> gruposFiltrados = FXCollections.observableArrayList();

        for (DataStore.Grupo grupo : DataStore.grupos) {
            if (grupo.getMateria().equals(materiaSeleccionada)) {
                gruposFiltrados.add(new GrupoCompleto(grupo));
            }
        }

        tablaGrupos.setItems(gruposFiltrados);
        System.out.println("📊 Mostrando " + gruposFiltrados.size() + " grupos para: " + materiaSeleccionada);
    }

    private void inscribirEnGrupo(GrupoCompleto grupo) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar inscripción");
        confirmacion.setHeaderText("¿Inscribirse en este grupo?");
        confirmacion.setContentText("Materia: " + grupo.getMateria() +
                "\nGrupo: " + grupo.getNombre() +
                "\nDocente: " + grupo.getDocente() +
                "\nClave: " + grupo.getClave());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean exito = DataStore.inscribirAlumnoEnGrupo(user, grupo.getId());

                if (exito) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Inscripción exitosa");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("¡Inscripción realizada con éxito!\n\n" +
                            "Materia: " + grupo.getMateria() + "\n" +
                            "Grupo: " + grupo.getNombre() + "\n" +
                            "Docente: " + grupo.getDocente());
                    successAlert.showAndWait();

                    // Actualizar tabla
                    actualizarTabla();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error en inscripción");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No se pudo completar la inscripción. Intente nuevamente.");
                    errorAlert.showAndWait();
                }
            }
        });
    }

    private void mostrarMisInscripciones() {
        List<Map<String, Object>> inscripciones = DataStore.obtenerInscripcionesAlumno(user);

        if (inscripciones.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mis inscripciones");
            alert.setHeaderText(null);
            alert.setContentText("No estás inscrito en ningún grupo actualmente.");
            alert.showAndWait();
        } else {
            StringBuilder contenido = new StringBuilder("Tus inscripciones actuales:\n\n");
            for (Map<String, Object> inscripcion : inscripciones) {
                contenido.append("• ").append(inscripcion.get("materia"))
                        .append(" - ").append(inscripcion.get("docente"))
                        .append(" (").append(inscripcion.get("periodo")).append(")\n");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mis inscripciones");
            alert.setHeaderText(null);
            alert.setContentText(contenido.toString());
            alert.showAndWait();
        }
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return btnInscripcion;
    }
}