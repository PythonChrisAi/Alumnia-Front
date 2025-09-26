package com.csb.unit1.javafxalumnia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar la vista de alumnos
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("alumno-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Lista de Alumnos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
