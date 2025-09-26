package com.csb.unit1.javafxalumnia;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BajaPanel extends BasePanel {

    private final ObservableList<DataStore.Alumno> alumnos = DataStore.alumnos;

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);

        Label header = new Label("DAR DE BAJA ALUMNOS");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        header.setStyle("-fx-underline: true;");

        TableView<DataStore.Alumno> table = new TableView<>();
        table.setItems(alumnos);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<DataStore.Alumno, String> colMatricula = new TableColumn<>("MATRÍCULA");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        TableColumn<DataStore.Alumno, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<DataStore.Alumno, Void> colEliminar = new TableColumn<>("ELIMINAR");
        colEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button();

            {
                ImageView icon = new ImageView(new Image("file:src/main/resources/trash.png"));
                icon.setFitWidth(20);
                icon.setFitHeight(20);
                btn.setGraphic(icon);
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #FFCDD2; -fx-cursor: hand;"));
                btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));

                btn.setOnAction(event -> {
                    DataStore.Alumno alumno = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmar eliminación");
                    alert.setHeaderText("¿Eliminar alumno?");
                    alert.setContentText("¿Estás seguro de que deseas eliminar al alumno " +
                            alumno.getNombre() + " con matrícula " + alumno.getMatricula() + "?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            DataStore.eliminados.push(alumno); // Guardamos en pila global
                            alumnos.remove(alumno);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        table.getColumns().addAll(colMatricula, colNombre, colEliminar);

        HBox botones = new HBox(20);
        botones.setAlignment(Pos.CENTER);

        Button btnGuardar = new Button("GUARDAR CAMBIOS");
        btnGuardar.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold;");

        btnGuardar.setOnMouseEntered(e -> btnGuardar.setStyle("-fx-background-color: #43A047; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;"));
        btnGuardar.setOnMouseExited(e -> btnGuardar.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold;"));

        btnGuardar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setHeaderText(null);
            alert.setContentText("Los cambios se han guardado en esta sesión.");
            alert.showAndWait();
        });

        Button btnDeshacer = new Button("DESHACER");
        btnDeshacer.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold;");

        btnDeshacer.setOnMouseEntered(e -> btnDeshacer.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;"));
        btnDeshacer.setOnMouseExited(e -> btnDeshacer.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold;"));

        btnDeshacer.setOnAction(e -> {
            if (!DataStore.eliminados.isEmpty()) {
                DataStore.Alumno ultimo = DataStore.eliminados.pop();
                alumnos.add(ultimo);
                alumnos.sort((a, b) -> a.getMatricula().compareTo(b.getMatricula())); // mantener orden
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nada que deshacer");
                alert.setHeaderText(null);
                alert.setContentText("No hay eliminaciones para revertir.");
                alert.showAndWait();
            }
        });

        botones.getChildren().addAll(btnGuardar, btnDeshacer);

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