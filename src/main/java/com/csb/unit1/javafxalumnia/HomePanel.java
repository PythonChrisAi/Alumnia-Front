package com.csb.unit1.javafxalumnia;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePanel extends Application {

    @Override
    public void start(Stage primaryStage) {
        ImageView gifView = new ImageView(Assets.BACKGROUND_GIF);
        gifView.setFitWidth(1080);
        gifView.setFitHeight(666);
        gifView.setPreserveRatio(false);

        StackPane root = new StackPane(gifView);

        BorderPane content = new BorderPane();

        Text title = new Text("ALUMNIA");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 80));
        title.setFill(Color.WHITE);

        StackPane topPane = new StackPane(title);
        topPane.setAlignment(Pos.TOP_CENTER);
        topPane.setPadding(new Insets(60, 0, 0, 0));

        Button loginButton = new Button("LOG-IN");
        loginButton.getStyleClass().add("login-button");
        loginButton.setOnAction(e -> {
            try {
                animateButtonToPanel(primaryStage, loginButton);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        StackPane centerPane = new StackPane(loginButton);
        centerPane.setAlignment(Pos.CENTER);

        Button exitButton = new Button("➔ SALIR");
        exitButton.getStyleClass().add("exit-button");
        exitButton.setOnAction(e -> primaryStage.close());

        StackPane bottomPane = new StackPane(exitButton);
        bottomPane.setAlignment(Pos.BOTTOM_RIGHT);
        bottomPane.setPadding(new Insets(0, 20, 20, 0));

        content.setTop(topPane);
        content.setCenter(centerPane);
        content.setBottom(bottomPane);

        root.getChildren().add(content);

        Scene scene = new Scene(root, 1080, 666);
        scene.getStylesheets().add(getClass().getResource("/styles/home.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Home - Alumnia");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void animateButtonToPanel(Stage primaryStage, Button loginButton) {

        Scene currentScene = primaryStage.getScene();
        StackPane root = (StackPane) currentScene.getRoot();

        double buttonCenterX = loginButton.localToScene(loginButton.getBoundsInLocal()).getMinX() + loginButton.getWidth() / 2;
        double buttonCenterY = loginButton.localToScene(loginButton.getBoundsInLocal()).getMinY() + loginButton.getHeight() / 2;

        Rectangle buttonCopy = new Rectangle(
                loginButton.getWidth(),
                loginButton.getHeight(),
                Color.WHITE
        );

        buttonCopy.setLayoutX(buttonCenterX - loginButton.getWidth() / 2);
        buttonCopy.setLayoutY(buttonCenterY - loginButton.getHeight() / 2);
        buttonCopy.setArcWidth(20);
        buttonCopy.setArcHeight(20);

        StackPane animationOverlay = new StackPane();
        animationOverlay.setBackground(Background.EMPTY);
        animationOverlay.setMouseTransparent(true);
        animationOverlay.getChildren().add(buttonCopy);
        root.getChildren().add(animationOverlay);

        double targetWidth = 600;
        double targetHeight = 450;
        double targetCenterX = 1080 / 2;
        double targetCenterY = (666 / 2) + 30;

        double scaleX = targetWidth / buttonCopy.getWidth();
        double scaleY = targetHeight / buttonCopy.getHeight();

        double translateX = (targetCenterX - buttonCenterX);
        double translateY = (targetCenterY - buttonCenterY);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.8), buttonCopy);
        scaleTransition.setToX(scaleX);
        scaleTransition.setToY(scaleY);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), buttonCopy);
        translateTransition.setToX(translateX);
        translateTransition.setToY(translateY);

        ParallelTransition parallelTransition = new ParallelTransition(
                scaleTransition, translateTransition
        );

        parallelTransition.setOnFinished(event -> {
            root.getChildren().remove(animationOverlay);
            try {
                new LoginPanel().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        parallelTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//ayudamicabeza













