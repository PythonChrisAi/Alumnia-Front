package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MateriaDetallePanel extends BasePanel {

    private final String materia;

    public MateriaDetallePanel(String materia) {
        this.materia = materia;
    }

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label header = new Label("Detalle de materia: " + materia);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        double promedio = calcularPromedioReal(materia);
        Label lblPromedio = new Label("Promedio general: " + (int) promedio);
        lblPromedio.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 16px;");

        VBox lista = new VBox(15);
        mostrarActividadesReales(materia, lista);

        Button btnVolver = new Button("← Volver al Kardex");
        btnVolver.setOnAction(e -> new KardexPanel().start(primaryStage, user));
        btnVolver.setStyle("-fx-background-color: lightgray; -fx-font-weight: bold;");
        btnVolver.setOnMouseEntered(ev -> btnVolver.setStyle("-fx-background-color: #B0BEC5; -fx-font-weight: bold;"));
        btnVolver.setOnMouseExited(ev -> btnVolver.setStyle("-fx-background-color: lightgray; -fx-font-weight: bold;"));

        VBox.setMargin(btnVolver, new Insets(20, 0, 0, 0));
        btnVolver.setAlignment(Pos.CENTER);
        content.getChildren().addAll(header, lblPromedio, lista, btnVolver);
        return content;
    }

    private double calcularPromedioReal(String materia) {
        int suma = 0;
        int count = 0;

        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMateria().equals(materia) && cal.getMatricula().equals(user)) {
                suma += cal.getPuntaje();
                count++;
            }
        }

        return count > 0 ? (double) suma / count : 0.0;
    }

    private void mostrarActividadesReales(String materia, VBox lista) {
        for (DataStore.Calificacion cal : DataStore.calificaciones) {
            if (cal.getMateria().equals(materia) && cal.getMatricula().equals(user)) {
                VBox item = new VBox(5);

                HBox fila = new HBox();
                Label lblActividad = new Label(cal.getActividad() + " - ");
                Label lblCal = new Label(cal.getPuntaje() + "/" + cal.getMaxPuntos());

                HBox.setHgrow(lblActividad, Priority.ALWAYS);
                fila.getChildren().addAll(lblActividad, lblCal);

                ProgressBar barra = new ProgressBar(cal.getPuntaje() / 100.0);
                barra.setPrefWidth(400);

                item.getChildren().addAll(fila, barra);
                lista.getChildren().add(item);
            }
        }
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {

        return null;
    }
}