package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class KardexPanel extends BasePanel {

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(40));

        Label header = new Label("CONSULTA DE KÁRDEX - Documento sin validez oficial");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
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

            Label lblFecha = new Label(pd.fecha);
            Label lblID = new Label(pd.id);
            Label lblCred = new Label(pd.creditos);
            Label lblMat = new Label(pd.materia);
            Label lblCal = new Label(pd.calificacion);

            grid.add(lblFecha, 0, 1);
            grid.add(lblID, 1, 1);
            grid.add(lblCred, 2, 1);
            grid.add(lblMat, 3, 1);
            grid.add(lblCal, 4, 1);

            Hyperlink verDetalle = new Hyperlink("Ver detalle");
            verDetalle.setOnAction(e -> new MateriaDetallePanel(lblMat.getText()).start(primaryStage, user));

            periodoBox.getChildren().addAll(grid, verDetalle);

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
        return null;
    }

}
//ayudamicabeza