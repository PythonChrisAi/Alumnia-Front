package com.csb.unit1.javafxalumnia;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Map;

public class KardexPanel extends BasePanel {

    @Override
    protected VBox buildContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(40));

        Label header = new Label("CONSULTA DE KÁRDEX - Documento sin validez oficial");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        content.getChildren().add(header);

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);

        VBox listaPeriodos = new VBox(20);
        listaPeriodos.setPadding(new Insets(10));

        // Obtener materias inscritas del alumno
        List<Map<String, Object>> materiasInscritas = DataStore.obtenerMateriasInscritas(user);

        if (materiasInscritas.isEmpty()) {
            Label lblSinMaterias = new Label("No estás inscrito en ninguna materia actualmente.");
            lblSinMaterias.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            listaPeriodos.getChildren().add(lblSinMaterias);
        } else {
            // Mostrar materias inscritas en el kárdex
            for (int i = 0; i < materiasInscritas.size(); i++) {
                Map<String, Object> materia = materiasInscritas.get(i);

                VBox materiaBox = new VBox(10);
                materiaBox.setPadding(new Insets(10));
                materiaBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-radius: 5;");

                GridPane grid = new GridPane();
                grid.setHgap(20);
                grid.setVgap(10);

                // Encabezados
                grid.add(new Label("MATERIA"), 0, 0);
                grid.add(new Label("GRUPO"), 1, 0);
                grid.add(new Label("CLAVE"), 2, 0);
                grid.add(new Label("DOCENTE"), 3, 0);
                grid.add(new Label("PERIODO"), 4, 0);
                grid.add(new Label("CALIFICACIÓN"), 5, 0);

                // Datos de la materia
                Label lblMateria = new Label((String) materia.get("materia"));
                Label lblGrupo = new Label("GRUPO " + getLetraGrupo((String) materia.get("grupoId")));
                Label lblClave = new Label((String) materia.get("clave"));
                Label lblDocente = new Label((String) materia.get("docente"));
                Label lblPeriodo = new Label((String) materia.get("periodo"));

                // Obtener calificación de la materia
                String calificacion = obtenerCalificacionMateria(user, (String) materia.get("materia"));
                Label lblCalificacion = new Label(calificacion);
                lblCalificacion.setStyle("-fx-font-weight: bold; -fx-text-fill: " +
                        (calificacion.equals("S/C") ? "orange" : "green") + ";");

                grid.add(lblMateria, 0, 1);
                grid.add(lblGrupo, 1, 1);
                grid.add(lblClave, 2, 1);
                grid.add(lblDocente, 3, 1);
                grid.add(lblPeriodo, 4, 1);
                grid.add(lblCalificacion, 5, 1);

                // Botón para ver detalle de calificaciones
                Hyperlink verDetalle = new Hyperlink("Ver calificaciones");
                verDetalle.setOnAction(e -> {
                    String nombreMateria = (String) materia.get("materia");
                    new MateriaDetallePanel(nombreMateria).start(primaryStage, user);
                });

                materiaBox.getChildren().addAll(grid, verDetalle);

                TitledPane tp = new TitledPane("MATERIA " + (i + 1) + " - " + materia.get("materia"), materiaBox);
                listaPeriodos.getChildren().add(tp);
            }
        }

        // Agregar también las materias históricas (opcional)
        agregarMateriasHistoricas(listaPeriodos);

        scroll.setContent(listaPeriodos);
        content.getChildren().add(scroll);
        return content;
    }

    private String getLetraGrupo(String grupoId) {
        if (grupoId.contains("-A")) return "A";
        if (grupoId.contains("-B")) return "B";
        if (grupoId.contains("-C")) return "C";
        return "A";
    }

    private String obtenerCalificacionMateria(String matricula, String materia) {
        List<DataStore.Calificacion> calificacionesMateria = DataStore.getCalificacionesPorMateriaYAlumno(matricula, materia);

        if (calificacionesMateria.isEmpty()) {
            return "S/C"; // Sin calificación
        }

        // Calcular promedio si hay calificaciones
        double suma = 0;
        int count = 0;

        for (DataStore.Calificacion cal : calificacionesMateria) {
            suma += cal.getPuntaje();
            count++;
        }

        if (count > 0) {
            return String.valueOf((int) Math.round(suma / count));
        }

        return "S/C";
    }

    private void agregarMateriasHistoricas(VBox listaPeriodos) {
        // Aquí puedes agregar materias históricas si las tienes
        // Por ahora solo mostramos las materias inscritas actualmente
    }

    @Override
    protected Button getActiveButton(Button btnRegistrar, Button btnKardex,
                                     Button btnBaja, Button btnInscripcion,
                                     Button btnEditarKardex, Button btnCalificaciones,
                                     Button btnReportes, Button btnSalir) {
        return btnKardex;
    }
}