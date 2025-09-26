package com.csb.unit1.javafxalumnia;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginPanel extends Application {

    private boolean darkMode = false;
    private Button nightModeButton;
    private VBox loginBox;
    private Text title;
    private Label errorLabel;

    @Override
    public void start(Stage primaryStage) {
        ImageView gifView = new ImageView(Assets.BACKGROUND_GIF);
        gifView.setFitWidth(1080);
        gifView.setFitHeight(666);
        gifView.setPreserveRatio(false);

        StackPane root = new StackPane(gifView);

        loginBox = new VBox(25);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40));
        loginBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        loginBox.setMaxWidth(600);
        loginBox.setMaxHeight(450);
        loginBox.setOpacity(0);

        nightModeButton = new Button("🌙");
        nightModeButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px;");
        nightModeButton.setCursor(Cursor.HAND);
        nightModeButton.setOnAction(e -> toggleNightMode());

        nightModeButton.setOnMouseEntered(e -> {
            nightModeButton.setStyle("-fx-background-color: #f0f0f0; -fx-font-size: 16px; -fx-background-radius: 15;");
        });
        nightModeButton.setOnMouseExited(e -> {
            nightModeButton.setStyle("-fx-background-color: transparent; -fx-font-size: 16px;");
        });

        Button backButton = new Button("← Regresar");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3ba6f7; -fx-font-weight: bold;");
        backButton.setCursor(Cursor.HAND);
        backButton.setOnAction(e -> {
            try {
                new HomePanel().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        backButton.setOnMouseEntered(e -> {
            backButton.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #3ba6f7; -fx-font-weight: bold; -fx-background-radius: 15;");
        });
        backButton.setOnMouseExited(e -> {
            backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #3ba6f7; -fx-font-weight: bold;");
        });

        HBox topBox = new HBox(10, backButton, nightModeButton);
        topBox.setAlignment(Pos.TOP_LEFT);
        topBox.setPadding(new Insets(0, 0, 10, 0));

        HBox logoBox = new HBox(10);
        logoBox.setAlignment(Pos.CENTER);

        title = new Text("ALUMNIA");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        title.setFill(Color.web("#3ba6f7"));

        logoBox.getChildren().add(title);

        Label matriculaLabel = new Label("Matrícula:");
        matriculaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        matriculaLabel.setTextFill(Color.BLACK);
        TextField matriculaField = new TextField();
        matriculaField.setPromptText("Ingrese su matrícula");
        matriculaField.setMaxWidth(300);
        matriculaField.setStyle("-fx-text-fill: black;");

        VBox matriculaBox = new VBox(8, matriculaLabel, matriculaField);
        matriculaBox.setAlignment(Pos.CENTER);

        Label passLabel = new Label("Contraseña:");
        passLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        passLabel.setTextFill(Color.BLACK);
        PasswordField passField = new PasswordField();
        passField.setPromptText("Ingrese su contraseña");
        passField.setMaxWidth(300);
        passField.setStyle("-fx-text-fill: black;");

        VBox passBox = new VBox(8, passLabel, passField);
        passBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("INGRESAR");
        loginButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: #0057d8; -fx-text-fill: white; -fx-background-radius: 20;");
        loginButton.setCursor(Cursor.HAND);

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        loginButton.setOnAction(e -> {
            String matricula = matriculaField.getText();
            String password = passField.getText();

            DataStore.User usuarioAutenticado = autenticarUsuario(matricula, password);

            if (usuarioAutenticado != null) {
                DataStore.currentUser = usuarioAutenticado;
                errorLabel.setText("");
                System.out.println("✅ Inicio de sesión exitoso - Rol: " + usuarioAutenticado.getRole());
                try {
                    new DashboardPanel().start(primaryStage, usuarioAutenticado.getUsername());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                errorLabel.setText("❌ Matrícula o contraseña incorrecta");
            }
        });

        loginButton.setOnMouseEntered(e -> {
            loginButton.setStyle("-fx-background-color: #0040a8; -fx-text-fill: white; -fx-background-radius: 20;");
        });
        loginButton.setOnMouseExited(e -> {
            loginButton.setStyle("-fx-background-color: #0057d8; -fx-text-fill: white; -fx-background-radius: 20;");
        });

        Hyperlink soporteLink = new Hyperlink("SOPORTE");
        soporteLink.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        soporteLink.setCursor(Cursor.HAND);
        soporteLink.setOnAction(e -> {
            System.out.println("Redirigir a soporte...");
        });

        soporteLink.setOnMouseEntered(e -> {
            soporteLink.setStyle("-fx-text-fill: #0040a8; -fx-underline: true;");
        });
        soporteLink.setOnMouseExited(e -> {
            soporteLink.setStyle("-fx-text-fill: -fx-text-base-color; -fx-underline: false;");
        });

        VBox bottomBox = new VBox(15, loginButton, errorLabel, soporteLink);
        bottomBox.setAlignment(Pos.CENTER);

        loginBox.getChildren().addAll(topBox, logoBox, matriculaBox, passBox, bottomBox);

        root.getChildren().add(loginBox);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), loginBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        Scene scene = new Scene(root, 1080, 666);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login - Alumnia");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private DataStore.User autenticarUsuario(String matricula, String password) {
        if ("admin".equals(matricula) && "admin123".equals(password)) {
            return new DataStore.User(matricula, "admin");
        } else if ("alumno".equals(matricula) && "alumno123".equals(password)) {
            return new DataStore.User(matricula, "alumno");
        } else if ("profesor".equals(matricula) && "profesor123".equals(password)) {
            return new DataStore.User(matricula, "profesor");
        } else if ("Chris".equals(matricula) && "Sbs".equals(password)) {
            return new DataStore.User(matricula, "admin"); // Mantener compatibilidad
        }
        return null;
    }

    private void toggleNightMode() {
        darkMode = !darkMode;

        if (darkMode) {
            nightModeButton.setText("☀️");
            loginBox.setBackground(new Background(new BackgroundFill(Color.web("#2D2D2D"), new CornerRadii(10), Insets.EMPTY)));
            title.setFill(Color.web("#E0E0E0"));
            for (var node : loginBox.getChildren()) {
                if (node instanceof VBox) {
                    for (var child : ((VBox) node).getChildren()) {
                        if (child instanceof Label) {
                            ((Label) child).setTextFill(Color.web("#E0E0E0"));
                        }
                    }
                }
            }
        } else {
            nightModeButton.setText("🌙");
            loginBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
            title.setFill(Color.web("#3ba6f7"));
            for (var node : loginBox.getChildren()) {
                if (node instanceof VBox) {
                    for (var child : ((VBox) node).getChildren()) {
                        if (child instanceof Label) {
                            ((Label) child).setTextFill(Color.BLACK);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}//ayudamicabeza