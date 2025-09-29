package com.csb.unit1.javafxalumnia;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BajaPanel extends BasePanel {

    private final ObservableList<DataStore.Alumno> alumnos = DataStore.alumnos;

    @Override
    protected VBox buildContent() {
        System.out.println("=== CARGANDO BAJA PANEL ===");
        System.out.println("Alumnos en DataStore: " + alumnos.size());

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
        colMatricula.setCellValueFactory(cellData -> {
            DataStore.Alumno alumno = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(alumno.getMatricula());
        });
        colMatricula.setStyle("-fx-alignment: CENTER;");

        TableColumn<DataStore.Alumno, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(cellData -> {
            DataStore.Alumno alumno = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(alumno.getNombre());
        });
        colNombre.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<DataStore.Alumno, Void> colEliminar = new TableColumn<>("ELIMINAR");
        colEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button();

            {
                btn.setText("🗑️");
                btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px;");

                btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #FFCDD2; -fx-cursor: hand; -fx-font-size: 14px;"));
                btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 14px;"));

                btn.setOnAction(event -> {
                    DataStore.Alumno alumno = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmar eliminación");
                    alert.setHeaderText("¿Eliminar alumno?");
                    alert.setContentText("¿Estás seguro de que deseas eliminar al alumno " +
                            alumno.getNombre() + " con matrícula " + alumno.getMatricula() + "?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            // Eliminar del backend
                            boolean exito = DataStore.eliminarAlumnoBackend(alumno.getMatricula());

                            if (exito) {
                                DataStore.eliminados.push(alumno);
                                alumnos.remove(alumno);
                                System.out.println("✅ Alumno eliminado: " + alumno.getNombre());

                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                successAlert.setTitle("Eliminado exitoso");
                                successAlert.setHeaderText(null);
                                successAlert.setContentText("Alumno eliminado correctamente del sistema.");
                                successAlert.showAndWait();
                            } else {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Error");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("No se pudo eliminar el alumno. Intente nuevamente.");
                                errorAlert.showAndWait();
                            }
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

        Button btnDeshacer = new Button("DESHACER ÚLTIMA ELIMINACIÓN");
        btnDeshacer.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold;");

        btnDeshacer.setOnMouseEntered(e -> btnDeshacer.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;"));
        btnDeshacer.setOnMouseExited(e -> btnDeshacer.setStyle("-fx-background-color: lightblue; -fx-font-weight: bold;"));

        // ✅ CÓDIGO CORREGIDO PARA EL BOTÓN DESHACER
        btnDeshacer.setOnAction(e -> {
            if (!DataStore.eliminados.isEmpty()) {
                DataStore.Alumno ultimo = DataStore.eliminados.pop();

                // Usar el método específico para reactivar
                boolean exito = DataStore.reactivarAlumnoBackend(ultimo.getMatricula());

                if (exito) {
                    // Actualizar el estado local
                    ultimo.setActivo(true);
                    alumnos.add(ultimo);
                    alumnos.sort((a, b) -> a.getMatricula().compareTo(b.getMatricula()));

                    System.out.println("✅ Alumno restaurado: " + ultimo.getNombre());
                    System.out.println("📊 Alumnos activos ahora: " + alumnos.size());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Restaurado");
                    alert.setHeaderText(null);
                    alert.setContentText("Alumno '" + ultimo.getNombre() + "' restaurado correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se pudo reactivar el alumno en el sistema.");
                    alert.showAndWait();

                    // Volver a poner en la pila si falló
                    DataStore.eliminados.push(ultimo);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nada que deshacer");
                alert.setHeaderText(null);
                alert.setContentText("No hay eliminaciones para revertir.");
                alert.showAndWait();
            }
        });

        botones.getChildren().addAll(btnDeshacer);

        content.getChildren().addAll(header, table, botones);
        return content;
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return btnBaja;
    }
}