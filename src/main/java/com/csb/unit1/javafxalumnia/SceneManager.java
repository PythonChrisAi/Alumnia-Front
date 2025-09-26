package com.csb.unit1.javafxalumnia;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneManager {

    public static void showWithPopup(Stage stage, Scene newScene) {
        if (stage == null || newScene == null) {
            System.err.println("⚠️ Stage o Scene es null, no se puede mostrar");
            return;
        }

        stage.setScene(newScene);

        Node root = newScene.getRoot();
        if (root == null) {
            System.err.println("⚠️ Root del Scene es null");
            return;
        }

        root.setScaleX(0.0);
        root.setScaleY(0.0);

        ScaleTransition st = new ScaleTransition(Duration.millis(400), root);
        st.setFromX(0.0);
        st.setFromY(0.0);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setCycleCount(1);
        st.play();
    }

    public static void transition(Stage stage, Scene newScene) {
        Platform.runLater(() -> showWithPopup(stage, newScene));
    }
}
//ayudamicabeza

