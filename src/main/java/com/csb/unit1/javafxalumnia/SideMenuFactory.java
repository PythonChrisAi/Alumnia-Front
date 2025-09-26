package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SideMenuFactory {
    public static VBox createMenu(Stage primaryStage, String user, String activePage) {
        VBox sideMenu = new VBox(15);
        sideMenu.setPadding(new Insets(20));
        sideMenu.setStyle("-fx-background-color: #42A5F5;");

        Label logo = new Label("ALUMNIA");
        logo.setTextFill(Color.WHITE);
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Button btnRegistrar = new Button("REGISTRAR ALUMNO");
        Button btnKardex = new Button("KARDEX");
        Button btnBaja = new Button("DAR DE BAJA ALUMNO");
        Button btnInscripcion = new Button("INSCRIPCION");
        Button btnEditarKardex = new Button("EDITAR KARDEX");
        Button btnSalir = new Button("CERRAR SESION");

        Button[] buttons = {btnRegistrar, btnKardex, btnBaja, btnInscripcion, btnEditarKardex, btnSalir};

        for (Button b : buttons) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        switch (activePage) {
            case "REGISTRAR ALUMNO" -> btnRegistrar.setStyle("-fx-background-color: white; -fx-font-weight: bold;");
            case "KARDEX" -> btnKardex.setStyle("-fx-background-color: white; -fx-font-weight: bold;");
            case "DAR DE BAJA ALUMNO" -> btnBaja.setStyle("-fx-background-color: white; -fx-font-weight: bold;");
            case "INSCRIPCION" -> btnInscripcion.setStyle("-fx-background-color: white; -fx-font-weight: bold;");
            case "EDITAR KARDEX" -> btnEditarKardex.setStyle("-fx-background-color: white; -fx-font-weight: bold;");
        }
        btnSalir.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white; -fx-font-weight: bold;");

        btnRegistrar.setOnAction(e -> new RegisterPanel().start(primaryStage, user));
        btnKardex.setOnAction(e -> new KardexPanel().start(primaryStage, user));
        btnBaja.setOnAction(e -> new BajaPanel().start(primaryStage, user));
        btnInscripcion.setOnAction(e -> new InscripcionPanel().start(primaryStage, user));
        btnEditarKardex.setOnAction(e -> new EditarKPanel().start(primaryStage, user));
        btnSalir.setOnAction(e -> {
            try {
                new HomePanel().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox.setMargin(logo, new Insets(0, 0, 30, 0));
        sideMenu.getChildren().addAll(logo, btnRegistrar, btnKardex, btnBaja, btnInscripcion, btnEditarKardex, btnSalir);

        return sideMenu;
    }
}

//ayudamicabeza
