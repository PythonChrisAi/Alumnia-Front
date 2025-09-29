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

        Label header = new Label("REGISTRAR USUARIO (SOLO ADMIN)");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-underline: true;");

        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(10);

        // Rol (Alumno o Profesor)
        Label lblRol = new Label("TIPO DE USUARIO:");
        ComboBox<String> cmbRol = new ComboBox<>();
        cmbRol.getItems().addAll("ALUMNO", "PROFESOR");
        cmbRol.setValue("ALUMNO");

        // Matrícula (obligatoria para alumnos, opcional para profesores)
        Label lblMatricula = new Label("MATRÍCULA:");
        TextField txtMatricula = new TextField();
        txtMatricula.setPromptText("Obligatorio para alumnos");

        // Campos de información personal
        Label lblNombre = new Label("NOMBRE COMPLETO:");
        TextField txtNombre = new TextField();

        Label lblFecha = new Label("FECHA DE NACIMIENTO:");
        TextField txtFecha = new TextField();
        txtFecha.setPromptText("YYYY-MM-DD");

        Label lblCarrera = new Label("CARRERA:");
        TextField txtCarrera = new TextField();

        Label lblTelefono = new Label("TELÉFONO:");
        TextField txtTelefono = new TextField();

        Label lblDireccion = new Label("DIRECCIÓN:");
        TextField txtDireccion = new TextField();

        Label lblCampus = new Label("CAMPUS:");
        TextField txtCampus = new TextField();

        // Campos de cuenta
        Label lblUsername = new Label("USUARIO:");
        TextField txtUsername = new TextField();

        Label lblPassword = new Label("CONTRASEÑA:");
        PasswordField txtPassword = new PasswordField();

        // Organizar en grid
        grid.add(lblRol, 0, 0);
        grid.add(cmbRol, 1, 0);
        grid.add(lblMatricula, 0, 1);
        grid.add(txtMatricula, 1, 1);
        grid.add(lblNombre, 0, 2);
        grid.add(txtNombre, 1, 2);
        grid.add(lblFecha, 0, 3);
        grid.add(txtFecha, 1, 3);
        grid.add(lblCarrera, 0, 4);
        grid.add(txtCarrera, 1, 4);
        grid.add(lblTelefono, 0, 5);
        grid.add(txtTelefono, 1, 5);
        grid.add(lblDireccion, 0, 6);
        grid.add(txtDireccion, 1, 6);
        grid.add(lblCampus, 0, 7);
        grid.add(txtCampus, 1, 7);
        grid.add(lblUsername, 0, 8);
        grid.add(txtUsername, 1, 8);
        grid.add(lblPassword, 0, 9);
        grid.add(txtPassword, 1, 9);

        // Actualizar campos según el rol seleccionado
        cmbRol.setOnAction(e -> {
            if ("PROFESOR".equals(cmbRol.getValue())) {
                txtMatricula.setPromptText("Opcional - Se generará automáticamente");
                txtCarrera.setDisable(true);
                txtCarrera.clear();
            } else {
                txtMatricula.setPromptText("Obligatorio para alumnos");
                txtCarrera.setDisable(false);
            }
        });

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
            if (validarCampos(cmbRol, txtMatricula, txtNombre, txtUsername, txtPassword)) {
                DataStore.RegistroCompletoRequest request = new DataStore.RegistroCompletoRequest();
                request.setRol(cmbRol.getValue());
                request.setMatricula(txtMatricula.getText().trim());
                request.setNombre(txtNombre.getText().trim());
                request.setFechaNacimiento(txtFecha.getText().trim());
                request.setCarrera(txtCarrera.getText().trim());
                request.setTelefono(txtTelefono.getText().trim());
                request.setDireccion(txtDireccion.getText().trim());
                request.setCampus(txtCampus.getText().trim());
                request.setUsername(txtUsername.getText().trim());
                request.setPassword(txtPassword.getText().trim());

                boolean exito = DataStore.registrarUsuarioCompleto(request);

                if (exito) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Registro exitoso");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Usuario registrado correctamente.\n\n" +
                            "Tipo: " + request.getRol() + "\n" +
                            "Usuario: " + request.getUsername() + "\n" +
                            "Puede iniciar sesión inmediatamente.");
                    successAlert.showAndWait();

                    // Limpiar campos
                    limpiarCampos(txtMatricula, txtNombre, txtFecha, txtCarrera,
                            txtTelefono, txtDireccion, txtCampus, txtUsername, txtPassword);
                    cmbRol.setValue("ALUMNO");
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("No se pudo registrar el usuario. Verifique los datos.");
                    errorAlert.showAndWait();
                }
            }
        });

        form.getChildren().addAll(topContent, grid, btnConfirmar);
        return form;
    }

    private boolean validarCampos(ComboBox<String> cmbRol, TextField matricula, TextField nombre,
                                  TextField username, PasswordField password) {

        // Validar campos obligatorios
        if (nombre.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio");
            return false;
        }

        if (username.getText().trim().isEmpty()) {
            mostrarError("El usuario es obligatorio");
            return false;
        }

        if (password.getText().trim().isEmpty()) {
            mostrarError("La contraseña es obligatoria");
            return false;
        }

        // Validar matrícula para alumnos
        if ("ALUMNO".equals(cmbRol.getValue()) && matricula.getText().trim().isEmpty()) {
            mostrarError("La matrícula es obligatoria para alumnos");
            return false;
        }

        return true;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Campos requeridos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
        }
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return btnRegistrar;
    }
}