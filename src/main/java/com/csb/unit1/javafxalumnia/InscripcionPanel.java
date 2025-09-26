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

public class InscripcionPanel extends BasePanel {

    public static class Grupo {
        private String periodo;
        private String id;
        private int creditos;
        private String nombreMateria;
        private String docente;
        private String grupo;

        public Grupo(String periodo, String id, int creditos, String nombreMateria, String docente, String grupo) {
            this.periodo = periodo;
            this.id = id;
            this.creditos = creditos;
            this.nombreMateria = nombreMateria;
            this.docente = docente;
            this.grupo = grupo;
        }

        public String getPeriodo() { return periodo; }
        public String getId() { return id; }
        public int getCreditos() { return creditos; }
        public String getNombreMateria() { return nombreMateria; }
        public String getDocente() { return docente; }
        public String getGrupo() { return grupo; }
    }

    @Override
    protected VBox buildContent() {
        return buildMateriasPage();
    }

    private VBox buildMateriasPage() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label header = new Label("INSCRIPCIÓN");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        header.setStyle("-fx-underline: true;");

        GridPane grid = new GridPane();
        grid.setHgap(50);
        grid.setVgap(30);
        grid.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 6; i++) {
            Button btnMateria = new Button("MATERIA " + i);
            btnMateria.setMinWidth(200);
            btnMateria.setMinHeight(60);
            btnMateria.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            btnMateria.setStyle("-fx-background-color: #E0E0E0; -fx-font-weight: bold;");

            btnMateria.setOnMouseEntered(e -> btnMateria.setStyle("-fx-background-color: #90CAF9; -fx-font-weight: bold;"));
            btnMateria.setOnMouseExited(e -> btnMateria.setStyle("-fx-background-color: #E0E0E0; -fx-font-weight: bold;"));

            int materiaIndex = i;
            btnMateria.setOnAction(e -> {
                BorderPane root = (BorderPane) btnMateria.getScene().getRoot();
                VBox gruposPage = buildGruposPage("MATERIA " + materiaIndex);
                root.setCenter(gruposPage);
            });

            grid.add(btnMateria, (i - 1) % 2, (i - 1) / 2);
        }

        content.getChildren().addAll(header, grid);
        return content;
    }

    private VBox buildGruposPage(String materia) {
        VBox content = new VBox(20);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label header = new Label("GRUPOS DISPONIBLES - " + materia);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        header.setStyle("-fx-underline: true;");

        TableView<Grupo> table = new TableView<>();
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Grupo, String> colPeriodo = new TableColumn<>("PERIODO");
        colPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));

        TableColumn<Grupo, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Grupo, Integer> colCreditos = new TableColumn<>("CRÉDITOS");
        colCreditos.setCellValueFactory(new PropertyValueFactory<>("creditos"));

        TableColumn<Grupo, String> colNombre = new TableColumn<>("NOMBRE MATERIA");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreMateria"));

        TableColumn<Grupo, String> colDocente = new TableColumn<>("DOCENTE");
        colDocente.setCellValueFactory(new PropertyValueFactory<>("docente"));

        TableColumn<Grupo, String> colGrupo = new TableColumn<>("GRUPO");
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));

        table.getColumns().addAll(colPeriodo, colId, colCreditos, colNombre, colDocente, colGrupo);

        ObservableList<Grupo> grupos = FXCollections.observableArrayList(
                new Grupo("2025-1", "MAT101", 8, materia, "Profesor A", "G1"),
                new Grupo("2025-1", "MAT102", 8, materia, "Profesor B", "G2"),
                new Grupo("2025-1", "MAT103", 8, materia, "Profesor C", "G3")
        );
        table.setItems(grupos);

        HBox botones = new HBox(20);
        botones.setAlignment(Pos.CENTER);

        Button btnConfirmar = new Button("CONFIRMAR INSCRIPCIÓN");
        btnConfirmar.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold;");
        btnConfirmar.setOnMouseEntered(e -> btnConfirmar.setStyle("-fx-background-color: #66BB6A; -fx-font-weight: bold;"));
        btnConfirmar.setOnMouseExited(e -> btnConfirmar.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold;"));

        btnConfirmar.setOnAction(e -> {
            Grupo seleccionado = table.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Inscripción exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Te has inscrito en " + seleccionado.getNombreMateria() +
                        " con el docente " + seleccionado.getDocente() +
                        " (Grupo " + seleccionado.getGrupo() + ")");
                alert.showAndWait();

                BorderPane root = (BorderPane) btnConfirmar.getScene().getRoot();
                root.setCenter(buildMateriasPage());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ningún grupo seleccionado");
                alert.setHeaderText(null);
                alert.setContentText("Por favor selecciona un grupo antes de confirmar.");
                alert.showAndWait();
            }
        });

        Button btnVolver = new Button("VOLVER");
        btnVolver.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold;");
        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle("-fx-background-color: #64B5F6; -fx-font-weight: bold;"));
        btnVolver.setOnMouseExited(e -> btnVolver.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold;"));

        btnVolver.setOnAction(e -> {
            BorderPane root = (BorderPane) btnVolver.getScene().getRoot();
            root.setCenter(buildMateriasPage());
        });

        botones.getChildren().addAll(btnConfirmar, btnVolver);

        content.getChildren().addAll(header, table, botones);
        return content;
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return null;
    }

}
//ayudamicabeza