package com.csb.unit1.javafxalumnia;

public class AlumnoDTO {
    private String matricula;
    private String nombre;
    private String fechaNacimiento;
    private String carrera;
    private String telefono;
    private String direccion;
    private String campus;
    private boolean activo;

    // Constructor vacío
    public AlumnoDTO() {}

    // Constructor desde Alumno
    public AlumnoDTO(DataStore.Alumno alumno) {
        this.matricula = alumno.getMatricula();
        this.nombre = alumno.getNombre();
        this.fechaNacimiento = alumno.getFechaNacimiento();
        this.carrera = alumno.getCarrera();
        this.telefono = alumno.getTelefono();
        this.direccion = alumno.getDireccion();
        this.campus = alumno.getCampus();
        this.activo = alumno.isActivo();
    }

    // Getters y Setters (IMPORTANTE: deben ser públicos)
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}