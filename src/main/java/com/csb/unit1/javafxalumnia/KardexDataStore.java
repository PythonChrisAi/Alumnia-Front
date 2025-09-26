package com.csb.unit1.javafxalumnia;

import java.util.HashMap;
import java.util.Map;

public class KardexDataStore {

    public static class PeriodData {
        public String fecha, id, creditos, materia, calificacion;

        public PeriodData(String f, String id, String c, String m, String cal) {
            this.fecha = f;
            this.id = id;
            this.creditos = c;
            this.materia = m;
            this.calificacion = cal;
        }
    }

    private static final Map<Integer, PeriodData> periodos = new HashMap<>();

    static {
        for (int i = 1; i <= 4; i++) {
            periodos.put(i, new PeriodData(
                    "ENE-JUN 202" + i,
                    "CSB10" + i,
                    "8",
                    "Programación Orientada a Objetos",
                    "95"
            ));
        }
    }

    public static Map<Integer, PeriodData> getPeriodos() {
        return periodos;
    }
}
//ayudamicabeza