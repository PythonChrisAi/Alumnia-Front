package com.csb.unit1.javafxalumnia;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class EditarKPanel extends BasePanel {

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(40));

        Label header = new Label("EDITAR KARDEX");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-underline: true;");
        content.getChildren().add(header);

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);

        VBox listaPeriodos = new VBox(20);
        listaPeriodos.setPadding(new Insets(10));

        for (int i = 1; i <= 4; i++) {
            KardexDataStore.PeriodData pd = KardexDataStore.getPeriodos().get(i);

            VBox periodoBox = new VBox(10);
            periodoBox.setPadding(new Insets(10));
            periodoBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-radius: 5;");

            GridPane grid = new GridPane();
            grid.setHgap(20);
            grid.setVgap(10);

            grid.add(new Label("Fecha"), 0, 0);
            grid.add(new Label("ID"), 1, 0);
            grid.add(new Label("Créditos"), 2, 0);
            grid.add(new Label("Materia"), 3, 0);
            grid.add(new Label("Calificación"), 4, 0);

            TextField txtFecha = new TextField(pd.fecha);
            TextField txtID = new TextField(pd.id);
            TextField txtCred = new TextField(pd.creditos);
            TextField txtMat = new TextField(pd.materia);
            TextField txtCal = new TextField(pd.calificacion);

            grid.add(txtFecha, 0, 1);
            grid.add(txtID, 1, 1);
            grid.add(txtCred, 2, 1);
            grid.add(txtMat, 3, 1);
            grid.add(txtCal, 4, 1);

            Button btnGuardar = new Button("Guardar cambios");
            Label guardadoLabel = new Label("");
            guardadoLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

            btnGuardar.setOnAction(e -> {
                pd.fecha = txtFecha.getText();
                pd.id = txtID.getText();
                pd.creditos = txtCred.getText();
                pd.materia = txtMat.getText();
                pd.calificacion = txtCal.getText();

                guardadoLabel.setText("Guardado ✔");
                PauseTransition pt = new PauseTransition(Duration.seconds(1.6));
                pt.setOnFinished(ev -> guardadoLabel.setText(""));
                pt.play();
            });

            periodoBox.getChildren().addAll(grid, new HBox(10, btnGuardar, guardadoLabel));

            TitledPane tp = new TitledPane("PERIODO " + i, periodoBox);
            listaPeriodos.getChildren().add(tp);
        }

        scroll.setContent(listaPeriodos);
        content.getChildren().add(scroll);
        return content;
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        // Si el dashboard no tiene botón propio en la barra lateral:
        return null;
        // O si decides que el "activo" sea alguno en específico, lo regresas:
        // return btnReportes;
    }
}
//ayudamicabeza