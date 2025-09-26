package com.csb.unit1.javafxalumnia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Stack;

public class DataStore {

    public static class Alumno {
        private String matricula;
        private String nombre;

        public Alumno(String matricula, String nombre) {
            this.matricula = matricula;
            this.nombre = nombre;
        }

        public String getMatricula() {
            return matricula;
        }

        public String getNombre() {
            return nombre;
        }
    }

    public static final ObservableList<Alumno> alumnos = FXCollections.observableArrayList(
            new Alumno("01928321", "Jordi Azael Martínez Jimenez"),
            new Alumno("01928322", "Diego Armando Zamora Jurado"),
            new Alumno("01928323", "Luis Gabriel Treviño Mendiola"),
            new Alumno("01928324", "Christopher Sustaita Barrón")
    );

    public static final Stack<Alumno> eliminados = new Stack<>();

    public static class User {
        private String username;
        private String role; // admin, alumno, profesor

        public User(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() { return username; }
        public String getRole() { return role; }

        // Métodos de verificación de permisos
        public boolean canAccessRegistrar() {
            return "admin".equals(role);
        }

        public boolean canAccessKardex() {
            return "admin".equals(role) || "alumno".equals(role) || "profesor".equals(role);
        }

        public boolean canAccessBaja() {
            return "admin".equals(role);
        }

        public boolean canAccessInscripcion() {
            return "admin".equals(role) || "alumno".equals(role);
        }

        public boolean canAccessEditarKardex() {
            return "admin".equals(role) || "profesor".equals(role);
        }

        public boolean canAccessCalificaciones() {
            return "admin".equals(role) || "profesor".equals(role);
        }

        public boolean canAccessGrupos() {
            return "admin".equals(role) || "profesor".equals(role);
        }
    }

    // Usuario logueado actualmente
    public static User currentUser;

    // Nueva clase para calificaciones
    public static class Calificacion {
        private String matricula;
        private String materia;
        private String actividad;
        private int puntaje;
        private int maxPuntos;

        public Calificacion(String matricula, String materia, String actividad, int puntaje, int maxPuntos) {
            this.matricula = matricula;
            this.materia = materia;
            this.actividad = actividad;
            this.puntaje = puntaje;
            this.maxPuntos = maxPuntos;
        }

        // Getters
        public String getMatricula() { return matricula; }
        public String getMateria() { return materia; }
        public String getActividad() { return actividad; }
        public int getPuntaje() { return puntaje; }
        public int getMaxPuntos() { return maxPuntos; }

        // Setters
        public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    }

    // Lista para almacenar calificaciones
    public static final ObservableList<Calificacion> calificaciones = FXCollections.observableArrayList(
            // Datos de ejemplo
            new Calificacion("01928321", "Programación Orientada a Objetos", "Actividad 1", 85, 100),
            new Calificacion("01928322", "Programación Orientada a Objetos", "Actividad 1", 90, 100),
            new Calificacion("01928323", "Programación Orientada a Objetos", "Actividad 1", 78, 100),
            new Calificacion("01928324", "Programación Orientada a Objetos", "Actividad 1", 92, 100),
            new Calificacion("01928321", "Programación Orientada a Objetos", "Actividad 2", 0, 100),
            new Calificacion("01928322", "Programación Orientada a Objetos", "Actividad 2", 0, 100),
            new Calificacion("01928323", "Programación Orientada a Objetos", "Actividad 2", 0, 100),
            new Calificacion("01928324", "Programación Orientada a Objetos", "Actividad 2", 0, 100)
    );
}
//ayudamicabeza