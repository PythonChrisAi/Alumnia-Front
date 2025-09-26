package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class BasePanel {

    protected BorderPane root;
    protected Stage primaryStage;
    protected String user;

    public void start(Stage stage, String user) {
        this.primaryStage = stage;
        this.user = user;

        root = new BorderPane();

        VBox sideMenu = buildSideMenu();
        root.setLeft(sideMenu);

        root.setCenter(buildContent());

        Scene scene = new Scene(root, 1080, 666);
        stage.setScene(scene);
        stage.setTitle("Alumnia");
        stage.show();
    }

    protected abstract VBox buildContent();

    private VBox buildSideMenu() {
        VBox sideMenu = new VBox(15);
        sideMenu.setPadding(new Insets(20));
        sideMenu.setStyle("-fx-background-color: #42A5F5;");

        Label logo = new Label("ALUMNIA");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");

        // Mostrar información del usuario
        Label userInfo = new Label("Usuario: " + user + "\nRol: " +
                (DataStore.currentUser != null ? DataStore.currentUser.getRole() : "No identificado"));
        userInfo.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        Button btnRegistrar = new Button("REGISTRAR ALUMNO");
        Button btnKardex = new Button("KARDEX");
        Button btnBaja = new Button("DAR DE BAJA ALUMNO");
        Button btnInscripcion = new Button("INSCRIPCION");
        Button btnEditarKardex = new Button("EDITAR KARDEX");
        Button btnCalificaciones = new Button("CALIFICACIONES");
        Button btnGrupos = new Button("GRUPOS");
        Button btnSalir = new Button("CERRAR SESION");

        aplicarPermisosYVisibilidad(btnRegistrar, btnKardex, btnBaja, btnInscripcion, btnEditarKardex, btnCalificaciones, btnGrupos, btnSalir);

        sideMenu.getChildren().addAll(logo, userInfo);

        if (DataStore.currentUser != null) {
            if (DataStore.currentUser.canAccessRegistrar()) {
                sideMenu.getChildren().add(btnRegistrar);
                addHoverEffect(btnRegistrar);
            }
            if (DataStore.currentUser.canAccessKardex()) {
                sideMenu.getChildren().add(btnKardex);
                addHoverEffect(btnKardex);
            }
            if (DataStore.currentUser.canAccessBaja()) {
                sideMenu.getChildren().add(btnBaja);
                addHoverEffect(btnBaja);
            }
            if (DataStore.currentUser.canAccessInscripcion()) {
                sideMenu.getChildren().add(btnInscripcion);
                addHoverEffect(btnInscripcion);
            }
            if (DataStore.currentUser.canAccessEditarKardex()) {
                sideMenu.getChildren().add(btnEditarKardex);
                addHoverEffect(btnEditarKardex);
            }
            if (DataStore.currentUser.canAccessCalificaciones()) {
                sideMenu.getChildren().add(btnCalificaciones);
                addHoverEffect(btnCalificaciones);
            }
            if (DataStore.currentUser.canAccessGrupos()) {
                sideMenu.getChildren().add(btnGrupos);
                addHoverEffect(btnGrupos);
            }
        }

        sideMenu.getChildren().add(btnSalir);
        addHoverEffect(btnSalir);

        configurarAccionesBotones(btnRegistrar, btnKardex, btnBaja, btnInscripcion, btnEditarKardex, btnCalificaciones, btnGrupos, btnSalir);

        marcarActivo(getActiveButton(btnRegistrar, btnKardex, btnBaja, btnInscripcion, btnEditarKardex, btnCalificaciones, btnGrupos, btnSalir),
                btnRegistrar, btnKardex, btnBaja, btnInscripcion, btnEditarKardex, btnCalificaciones, btnGrupos, btnSalir);

        return sideMenu;
    }

    private void aplicarPermisosYVisibilidad(Button btnRegistrar, Button btnKardex, Button btnBaja,
                                             Button btnInscripcion, Button btnEditarKardex,
                                             Button btnCalificaciones, Button btnGrupos, Button btnSalir) {

        String estiloBase = "-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold;";

        btnRegistrar.setStyle(estiloBase);
        btnKardex.setStyle(estiloBase);
        btnBaja.setStyle(estiloBase);
        btnInscripcion.setStyle(estiloBase);
        btnEditarKardex.setStyle(estiloBase);
        btnCalificaciones.setStyle(estiloBase);
        btnGrupos.setStyle(estiloBase);
        btnSalir.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white; -fx-font-weight: bold;");

        // Configurar tamaño máximo para todos
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnKardex.setMaxWidth(Double.MAX_VALUE);
        btnBaja.setMaxWidth(Double.MAX_VALUE);
        btnInscripcion.setMaxWidth(Double.MAX_VALUE);
        btnEditarKardex.setMaxWidth(Double.MAX_VALUE);
        btnCalificaciones.setMaxWidth(Double.MAX_VALUE);
        btnGrupos.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setMaxWidth(Double.MAX_VALUE);
    }

    private void configurarAccionesBotones(Button btnRegistrar, Button btnKardex, Button btnBaja,
                                           Button btnInscripcion, Button btnEditarKardex,
                                           Button btnCalificaciones, Button btnGrupos, Button btnSalir) {
        btnRegistrar.setOnAction(e -> new RegisterPanel().start(primaryStage, user));
        btnKardex.setOnAction(e -> new KardexPanel().start(primaryStage, user));
        btnBaja.setOnAction(e -> new BajaPanel().start(primaryStage, user));
        btnInscripcion.setOnAction(e -> new InscripcionPanel().start(primaryStage, user));
        btnEditarKardex.setOnAction(e -> new EditarKPanel().start(primaryStage, user));
        btnCalificaciones.setOnAction(e -> new CalificacionesPanel().start(primaryStage, user));
        btnGrupos.setOnAction(e -> new GruposPanel().start(primaryStage, user));
        btnSalir.setOnAction(e -> {
            DataStore.currentUser = null; // Limpiar usuario al salir
            try {
                new HomePanel().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    protected void marcarActivo(Button activo, Button... todos) {
        for (Button b : todos) {
            if (b.getParent() != null) {
                if (b.getText().equals("CERRAR SESION")) {
                    b.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white; -fx-font-weight: bold;");
                } else {
                    b.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold;");
                }
            }
        }
        if (activo != null && activo.getParent() != null) {
            activo.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-weight: bold;");
        }
    }

    private void addHoverEffect(Button btn) {
        if (btn.isDisabled()) return;

        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains("white; -fx-text-fill: black;")) {
                btn.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
            }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains("white; -fx-text-fill: black;")) {
                if (btn.getText().equals("CERRAR SESION")) {
                    btn.setStyle("-fx-background-color: #0D47A1; -fx-text-fill: white; -fx-font-weight: bold;");
                } else {
                    btn.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-weight: bold;");
                }
            }
        });
    }

    protected abstract Button getActiveButton(Button btnRegistrar, Button btnKardex, Button btnBaja,
                                              Button btnInscripcion, Button btnEditarKardex,
                                              Button btnCalificaciones, Button btnGrupos, Button btnSalir);
}
//ayudamicabeza