package com.csb.unit1.javafxalumnia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DataStore {

    private static final ObjectMapper mapper = new ObjectMapper();

    // DTO para Alumno
    public static class AlumnoDTO {
        private String matricula;
        private String nombre;
        private String fechaNacimiento;
        private String carrera;
        private String telefono;
        private String direccion;
        private String campus;
        private boolean activo;

        public AlumnoDTO() {}

        public AlumnoDTO(Alumno alumno) {
            this.matricula = alumno.getMatricula();
            this.nombre = alumno.getNombre();
            this.fechaNacimiento = alumno.getFechaNacimiento();
            this.carrera = alumno.getCarrera();
            this.telefono = alumno.getTelefono();
            this.direccion = alumno.getDireccion();
            this.campus = alumno.getCampus();
            this.activo = alumno.isActivo();
        }

        // Getters y Setters públicos
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

    // DTO para Calificacion
    public static class CalificacionDTO {
        private Long id;
        private String matricula;
        private String materia;
        private String actividad;
        private int puntaje;
        private int maxPuntos;
        private String periodo;

        public CalificacionDTO() {}

        public CalificacionDTO(Calificacion calificacion) {
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

    // Clase para el request de registro completo
    public static class RegistroCompletoRequest {
        private String matricula;
        private String nombre;
        private String fechaNacimiento;
        private String carrera;
        private String telefono;
        private String direccion;
        private String campus;
        private String username;
        private String password;
        private String rol;

        // Getters y Setters
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
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
    }

    // Clase Alumno original
    public static class Alumno {
        private String matricula;
        private String nombre;
        private String fechaNacimiento;
        private String carrera;
        private String telefono;
        private String direccion;
        private String campus;
        private boolean activo;

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

        public Alumno() {}
        public Alumno(String matricula, String nombre) {
            this.matricula = matricula;
            this.nombre = nombre;
        }
    }

    // Obtener alumnos desde el backend
    public static ObservableList<Alumno> getAlumnos() {
        try {
            System.out.println("🔄 Obteniendo alumnos del backend...");
            String json = ApiClient.get("/alumnos");

            if (json != null && !json.trim().isEmpty() && !json.equals("[]")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                List<Map<String, Object>> jsonList = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});

                List<Alumno> lista = new ArrayList<>();
                for (Map<String, Object> jsonObj : jsonList) {
                    Alumno alumno = new Alumno();
                    alumno.setMatricula((String) jsonObj.get("matricula"));
                    alumno.setNombre((String) jsonObj.get("nombre"));
                    alumno.setFechaNacimiento((String) jsonObj.get("fechaNacimiento"));
                    alumno.setCarrera((String) jsonObj.get("carrera"));
                    alumno.setTelefono((String) jsonObj.get("telefono"));
                    alumno.setDireccion((String) jsonObj.get("direccion"));
                    alumno.setCampus((String) jsonObj.get("campus"));
                    alumno.setActivo(jsonObj.get("activo") != null ? (Boolean) jsonObj.get("activo") : true);
                    lista.add(alumno);
                }

                System.out.println("✅ " + lista.size() + " alumnos cargados del backend");
                return FXCollections.observableArrayList(lista);
            } else {
                System.out.println("⚠️ No se pudieron cargar alumnos del backend");
                return FXCollections.observableArrayList();
            }

        } catch (Exception e) {
            System.out.println("❌ Error cargando alumnos: " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    // Actualizar alumno en backend
    public static boolean actualizarAlumnoBackend(Alumno alumno) {
        try {
            AlumnoDTO alumnoDTO = new AlumnoDTO(alumno);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(alumnoDTO);

            String response = ApiClient.put("/alumnos/" + alumno.getMatricula(), json);
            System.out.println("✅ Alumno actualizado en backend: " + alumno.getMatricula());
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error actualizando alumno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar alumno en backend
    public static boolean eliminarAlumnoBackend(String matricula) {
        try {
            String response = ApiClient.delete("/alumnos/" + matricula);
            System.out.println("✅ Alumno eliminado en backend: " + matricula);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error eliminando alumno: " + e.getMessage());
            return false;
        }
    }

    // Reactivar alumno en backend
    public static boolean reactivarAlumnoBackend(String matricula) {
        try {
            String response = ApiClient.put("/alumnos/reactivar/" + matricula, "{}");
            System.out.println("✅ Alumno reactivado en backend: " + matricula);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error reactivando alumno: " + e.getMessage());
            return false;
        }
    }

    // Registrar nuevo alumno en backend
    public static boolean registrarAlumnoBackend(Alumno alumno) {
        try {
            AlumnoDTO alumnoDTO = new AlumnoDTO(alumno);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(alumnoDTO);

            String response = ApiClient.post("/alumnos", json);
            System.out.println("✅ Alumno registrado en backend: " + alumno.getMatricula());
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error registrando alumno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Registrar usuario completo (alumno o profesor)
    public static boolean registrarUsuarioCompleto(RegistroCompletoRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(request);

            String response = ApiClient.post("/usuarios/registro-completo", json);

            // Verificar si fue exitoso
            if (response.contains("\"message\"")) {
                System.out.println("✅ Usuario registrado exitosamente: " + request.getNombre());
                return true;
            } else {
                System.out.println("❌ Error registrando usuario: " + response);
                return false;
            }

        } catch (Exception e) {
            System.out.println("❌ Error registrando usuario completo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Inscribir alumno en grupo
    public static boolean inscribirAlumnoEnGrupo(String matricula, String grupoId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Map.of(
                    "matricula", matricula,
                    "grupoId", grupoId
            ));

            String response = ApiClient.post("/inscripciones/inscribir", json);

            if (response.contains("\"message\"")) {
                System.out.println("✅ Alumno inscrito en grupo: " + grupoId);

                // Recargar grupos para actualizar cupos
                grupos.setAll(getGrupos());

                return true;
            } else {
                System.out.println("❌ Error en inscripción: " + response);
                return false;
            }

        } catch (Exception e) {
            System.out.println("❌ Error inscribiendo alumno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Obtener inscripciones del alumno (CORREGIDO)
    public static List<Map<String, Object>> obtenerInscripcionesAlumno(String matricula) {
        try {
            String json = ApiClient.get("/inscripciones/alumno/" + matricula);

            System.out.println("📋 Respuesta inscripciones: " + json);

            if (json != null && !json.trim().isEmpty() && !json.equals("[]") && !json.contains("error")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
            }
            return new ArrayList<>();

        } catch (Exception e) {
            System.out.println("❌ Error obteniendo inscripciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Obtener materias inscritas por alumno (para kárdex)
    public static List<Map<String, Object>> obtenerMateriasInscritas(String matricula) {
        try {
            String json = ApiClient.get("/inscripciones/materias-alumno/" + matricula);

            System.out.println("📚 Respuesta materias inscritas: " + json);

            if (json != null && !json.trim().isEmpty() && !json.equals("[]") && !json.contains("error")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
            }
            return new ArrayList<>();

        } catch (Exception e) {
            System.out.println("❌ Error obteniendo materias inscritas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Obtener grupos por materia
    public static List<Grupo> getGruposPorMateria(String materia) {
        List<Grupo> gruposMateria = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.getMateria().equals(materia)) {
                gruposMateria.add(grupo);
            }
        }
        return gruposMateria;
    }

    // Obtener materias únicas
    public static List<String> getMateriasUnicas() {
        List<String> materias = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (!materias.contains(grupo.getMateria())) {
                materias.add(grupo.getMateria());
            }
        }
        return materias;
    }

    // Obtener calificaciones por alumno
    public static List<Calificacion> getCalificacionesPorAlumno(String matricula) {
        List<Calificacion> calificacionesAlumno = new ArrayList<>();
        for (Calificacion cal : calificaciones) {
            if (cal.getMatricula().equals(matricula)) {
                calificacionesAlumno.add(cal);
            }
        }
        return calificacionesAlumno;
    }

    // Obtener calificaciones por materia y alumno
    public static List<Calificacion> getCalificacionesPorMateriaYAlumno(String matricula, String materia) {
        List<Calificacion> calificacionesFiltradas = new ArrayList<>();
        for (Calificacion cal : calificaciones) {
            if (cal.getMatricula().equals(matricula) && cal.getMateria().equals(materia)) {
                calificacionesFiltradas.add(cal);
            }
        }
        return calificacionesFiltradas;
    }

    // Crear calificación inicial para materia inscrita
    public static boolean crearCalificacionInicial(String matricula, String materia, String actividad) {
        try {
            // Verificar si ya existe la calificación
            for (Calificacion cal : calificaciones) {
                if (cal.getMatricula().equals(matricula) &&
                        cal.getMateria().equals(materia) &&
                        cal.getActividad().equals(actividad)) {
                    return true; // Ya existe
                }
            }

            // Crear nueva calificación
            Calificacion nuevaCal = new Calificacion();
            nuevaCal.setMatricula(matricula);
            nuevaCal.setMateria(materia);
            nuevaCal.setActividad(actividad);
            nuevaCal.setPuntaje(0);
            nuevaCal.setMaxPuntos(100);
            nuevaCal.setPeriodo("2025-1");

            return crearCalificacionBackend(nuevaCal);

        } catch (Exception e) {
            System.out.println("❌ Error creando calificación inicial: " + e.getMessage());
            return false;
        }
    }

    public static final ObservableList<Alumno> alumnos = getAlumnos();
    public static final Stack<Alumno> eliminados = new Stack<>();

    public static class User {
        private String username;
        private String role;

        public User(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() { return username; }
        public String getRole() { return role; }

        public boolean canAccessRegistrar() { return "ADMIN".equals(role); }
        public boolean canAccessKardex() { return true; }
        public boolean canAccessBaja() { return "ADMIN".equals(role); }
        public boolean canAccessInscripcion() { return "ADMIN".equals(role) || "ALUMNO".equals(role); }
        public boolean canAccessEditarKardex() { return "ADMIN".equals(role) || "PROFESOR".equals(role); }
        public boolean canAccessCalificaciones() { return "ADMIN".equals(role) || "PROFESOR".equals(role); }
        public boolean canAccessGrupos() { return "ADMIN".equals(role) || "PROFESOR".equals(role); }
    }

    public static User currentUser;

    public static class Calificacion {
        private Long id;
        private String matricula;
        private String materia;
        private String actividad;
        private int puntaje;
        private int maxPuntos;
        private String periodo;

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

    // Obtener calificaciones desde el backend
    public static ObservableList<Calificacion> getCalificaciones() {
        try {
            System.out.println("🔄 Obteniendo calificaciones del backend...");
            String json = ApiClient.get("/calificaciones");

            if (json != null && !json.trim().isEmpty() && !json.equals("[]")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                List<Map<String, Object>> jsonList = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});

                List<Calificacion> lista = new ArrayList<>();
                for (Map<String, Object> jsonObj : jsonList) {
                    Calificacion cal = new Calificacion();
                    cal.setId(jsonObj.get("id") != null ? ((Number) jsonObj.get("id")).longValue() : null);
                    cal.setMatricula((String) jsonObj.get("matricula"));
                    cal.setMateria((String) jsonObj.get("materia"));
                    cal.setActividad((String) jsonObj.get("actividad"));
                    cal.setPuntaje(jsonObj.get("puntaje") != null ? ((Number) jsonObj.get("puntaje")).intValue() : 0);
                    cal.setMaxPuntos(jsonObj.get("maxPuntos") != null ? ((Number) jsonObj.get("maxPuntos")).intValue() : 100);
                    cal.setPeriodo((String) jsonObj.get("periodo"));
                    lista.add(cal);
                }

                System.out.println("✅ " + lista.size() + " calificaciones cargadas del backend");
                return FXCollections.observableArrayList(lista);
            } else {
                System.out.println("⚠️ No se pudieron cargar calificaciones del backend");
                return FXCollections.observableArrayList();
            }

        } catch (Exception e) {
            System.out.println("❌ Error cargando calificaciones: " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    // Actualizar calificación en backend
    public static boolean actualizarCalificacionBackend(Calificacion calificacion) {
        try {
            CalificacionDTO calDTO = new CalificacionDTO(calificacion);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(calDTO);

            String response = ApiClient.put("/calificaciones/" + calificacion.getId(), json);
            System.out.println("✅ Calificación actualizada en backend: " + calificacion.getId());
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error actualizando calificación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Crear nueva calificación en backend
    public static boolean crearCalificacionBackend(Calificacion calificacion) {
        try {
            CalificacionDTO calDTO = new CalificacionDTO(calificacion);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(calDTO);

            String response = ApiClient.post("/calificaciones", json);
            System.out.println("✅ Calificación creada en backend: " + calificacion.getMatricula());

            // Recargar calificaciones
            if (response.contains("id")) {
                calificaciones.setAll(getCalificaciones());
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("❌ Error creando calificación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static List<Map<String, Object>> obtenerInscripcionesPorGrupo(String grupoId) {
        try {
            String json = ApiClient.get("/inscripciones/grupo/" + grupoId);

            if (json != null && !json.trim().isEmpty() && !json.equals("[]") && !json.contains("error")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
            }
            return new ArrayList<>();

        } catch (Exception e) {
            System.out.println("❌ Error obteniendo inscripciones por grupo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    // Obtener grupos desde el backend
    public static ObservableList<Grupo> getGrupos() {
        try {
            System.out.println("🔄 Obteniendo grupos del backend...");
            String json = ApiClient.get("/grupos");

            if (json != null && !json.trim().isEmpty() && !json.equals("[]")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                List<Map<String, Object>> jsonList = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});

                List<Grupo> lista = new ArrayList<>();
                for (Map<String, Object> jsonObj : jsonList) {
                    Grupo grupo = new Grupo();
                    grupo.setId((String) jsonObj.get("id"));
                    grupo.setNombre((String) jsonObj.get("nombre"));
                    grupo.setMateria((String) jsonObj.get("materia"));
                    grupo.setDocente((String) jsonObj.get("docente"));
                    grupo.setPeriodo((String) jsonObj.get("periodo"));
                    grupo.setClave((String) jsonObj.get("clave"));
                    // Campos nuevos para inscripciones
                    grupo.setCupo(jsonObj.get("cupo") != null ? ((Number) jsonObj.get("cupo")).intValue() : 30);
                    grupo.setInscritos(jsonObj.get("inscritos") != null ? ((Number) jsonObj.get("inscritos")).intValue() : 0);
                    lista.add(grupo);
                }

                System.out.println("✅ " + lista.size() + " grupos cargados del backend");
                return FXCollections.observableArrayList(lista);
            } else {
                System.out.println("⚠️ No se pudieron cargar grupos del backend");
                return FXCollections.observableArrayList();
            }

        } catch (Exception e) {
            System.out.println("❌ Error cargando grupos: " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    public static class Grupo {
        private String id;
        private String nombre;
        private String materia;
        private String docente;
        private String periodo;
        private String clave;
        private int cupo;
        private int inscritos;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getMateria() { return materia; }
        public void setMateria(String materia) { this.materia = materia; }
        public String getDocente() { return docente; }
        public void setDocente(String docente) { this.docente = docente; }
        public String getPeriodo() { return periodo; }
        public void setPeriodo(String periodo) { this.periodo = periodo; }
        public String getClave() { return clave; }
        public void setClave(String clave) { this.clave = clave; }
        public int getCupo() { return cupo; }
        public void setCupo(int cupo) { this.cupo = cupo; }
        public int getInscritos() { return inscritos; }
        public void setInscritos(int inscritos) { this.inscritos = inscritos; }
        public int getDisponibles() { return cupo - inscritos; }
        public String getEstado() { return getDisponibles() > 0 ? "DISPONIBLE" : "LLENO"; }
    }

    public static final ObservableList<Calificacion> calificaciones = getCalificaciones();
    public static final ObservableList<Grupo> grupos = getGrupos();
}