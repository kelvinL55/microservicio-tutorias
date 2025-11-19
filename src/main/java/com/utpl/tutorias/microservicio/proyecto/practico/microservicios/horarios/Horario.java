package com.utpl.tutorias.microservicio.proyecto.practico.microservicios.horarios;

import java.time.LocalTime;
import java.util.Objects;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

/**
 * Modelo que representa un horario académico dentro del microservicio.
 */
public class Horario {

    private final String asignatura;
    private final String dia;
    private final LocalTime horaInicio;
    private final LocalTime horaFin;
    private boolean cruce;

    public Horario(String asignatura,
            String dia,
            LocalTime horaInicio,
            LocalTime horaFin) {
        this(asignatura, dia, horaInicio, horaFin, false);
    }

    @JsonbCreator
    public Horario(@JsonbProperty("asignatura") String asignatura,
            @JsonbProperty("dia") String dia,
            @JsonbProperty("horaInicio") LocalTime horaInicio,
            @JsonbProperty("horaFin") LocalTime horaFin,
            @JsonbProperty("cruce") boolean cruce) {
        this.asignatura = Objects.requireNonNull(asignatura, "asignatura");
        this.dia = Objects.requireNonNull(dia, "dia");
        this.horaInicio = Objects.requireNonNull(horaInicio, "horaInicio");
        this.horaFin = Objects.requireNonNull(horaFin, "horaFin");
        if (!horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio.");
        }
        this.cruce = cruce;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public String getDia() {
        return dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public boolean isCruce() {
        return cruce;
    }

    public void setCruce(boolean cruce) {
        this.cruce = cruce;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "asignatura='" + asignatura + '\'' +
                ", dia='" + dia + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", cruce=" + cruce +
                '}';
    }
}
