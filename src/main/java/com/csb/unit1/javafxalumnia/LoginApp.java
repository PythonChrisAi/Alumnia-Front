package com.csb.unit1.javafxalumnia;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Text loginText = new Text("Login Page");
        loginText.setFont(Font.font("Arial", 40));
        loginText.setFill(Color.WHITE);

        StackPane centerPane = new StackPane(loginText);
        centerPane.setAlignment(Pos.CENTER);

        Button homeButton = new Button("🏠");
        homeButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #38b6ff;" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 20px;"
        );
        homeButton.setOnAction(e -> {
            try {
                new HomePanel().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(centerPane);
        root.setBottom(homeButton);
        BorderPane.setAlignment(homeButton, Pos.BOTTOM_LEFT);

        Scene scene = new Scene(root, 1080, 666);
        root.setStyle("-fx-background-color: #38b6ff;");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login - Alumnia");
        primaryStage.show();
    }
}


