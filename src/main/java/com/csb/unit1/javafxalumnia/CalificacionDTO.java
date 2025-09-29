package com.csb.unit1.javafxalumnia;

public class CalificacionDTO {
    private Long id;
    private String matricula;
    private String materia;
    private String actividad;
    private int puntaje;
    private int maxPuntos;
    private String periodo;

    public CalificacionDTO() {}

    public CalificacionDTO(DataStore.Calificacion calificacion) {
        this.id = calificacion.getId();
        this.matricula = calificacion.getMatricula();
        this.materia = calificacion.getMateria();
        this.actividad = calificacion.getActividad();
        this.puntaje = calificacion.getPuntaje();
        this.maxPuntos = calificacion.getMaxPuntos();
        this.periodo = calificacion.getPeriodo();
    }

    // Getters y Setters públicos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public String getActividad() { return actividad; }
    public void setActividad(String actividad) { this.actividad = actividad; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public int getMaxPuntos() { return maxPuntos; }
    public void setMaxPuntos(int maxPuntos) { this.maxPuntos = maxPuntos; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}