package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterPanel extends BasePanel {

    @Override
    protected VBox buildContent() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(40));

        Label header = new Label("REGISTRAR ALUMNO");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-underline: true;");

        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(10);

        Label lblNombre = new Label("NOMBRE DEL ALUMNO:");
        Label lblFecha = new Label("FECHA DE NACIMIENTO:");
        Label lblCarrera = new Label("CARRERA:");
        Label lblTelefono = new Label("TELEFONO:");
        Label lblDireccion = new Label("DIRECCION:");
        Label lblCampus = new Label("CAMPUS:");

        TextField txtNombre = new TextField();
        TextField txtFecha = new TextField();
        TextField txtCarrera = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtDireccion = new TextField();
        TextField txtCampus = new TextField();

        grid.add(lblNombre, 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(lblFecha, 0, 1);
        grid.add(txtFecha, 1, 1);
        grid.add(lblCarrera, 0, 2);
        grid.add(txtCarrera, 1, 2);
        grid.add(lblTelefono, 0, 3);
        grid.add(txtTelefono, 1, 3);
        grid.add(lblDireccion, 0, 4);
        grid.add(txtDireccion, 1, 4);
        grid.add(lblCampus, 0, 5);
        grid.add(txtCampus, 1, 5);

        HBox topContent = new HBox(30, header);
        topContent.setAlignment(Pos.TOP_LEFT);

        Button btnConfirmar = new Button("CONFIRMAR REGISTRO");
        btnConfirmar.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold;");

        btnConfirmar.setOnMouseEntered(e ->
                btnConfirmar.setStyle("-fx-background-color: #43A047; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;")
        );
        btnConfirmar.setOnMouseExited(e ->
                btnConfirmar.setStyle("-fx-background-color: lightgreen; -fx-font-weight: bold;")
        );

        btnConfirmar.setOnAction(e -> {
            System.out.println("Alumno registrado: " + txtNombre.getText());
        });

        form.getChildren().addAll(topContent, grid, btnConfirmar);
        return form;
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return btnRegistrar;
    }
}
//ayudamicabeza