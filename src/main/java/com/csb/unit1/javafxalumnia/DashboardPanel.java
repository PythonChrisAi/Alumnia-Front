package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DashboardPanel extends BasePanel {

    @Override
    protected VBox buildContent() {
        VBox box = new VBox(20);
        box.setPadding(new Insets(40));
        box.setAlignment(Pos.CENTER);

        String rol = DataStore.currentUser != null ? DataStore.currentUser.getRole() : "No identificado";

        Label welcomeLabel = new Label("Bienvenido al Dashboard, " + user + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label roleLabel = new Label("Rol: " + rol.toUpperCase());
        roleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        Label permissionsLabel = new Label("Permisos disponibles:");
        permissionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox permissionsList = new VBox(5);
        permissionsList.setAlignment(Pos.CENTER_LEFT);

        if (DataStore.currentUser != null) {
            if (DataStore.currentUser.canAccessRegistrar()) {
                permissionsList.getChildren().add(new Label("✓ Registrar alumnos"));
            }
            if (DataStore.currentUser.canAccessKardex()) {
                permissionsList.getChildren().add(new Label("✓ Consultar kárdex"));
            }
            if (DataStore.currentUser.canAccessBaja()) {
                permissionsList.getChildren().add(new Label("✓ Dar de baja alumnos"));
            }
            if (DataStore.currentUser.canAccessInscripcion()) {
                permissionsList.getChildren().add(new Label("✓ Realizar inscripciones"));
            }
            if (DataStore.currentUser.canAccessEditarKardex()) {
                permissionsList.getChildren().add(new Label("✓ Editar kárdex"));
            }
            if (DataStore.currentUser.canAccessCalificaciones()) {
                permissionsList.getChildren().add(new Label("✓ Calificar actividades"));
            }
        }

        box.getChildren().addAll(welcomeLabel, roleLabel, permissionsLabel, permissionsList);
        return box;
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
