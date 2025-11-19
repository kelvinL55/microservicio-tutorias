package com.utpl.tutorias.microservicio.proyecto.practico.microservicios.horarios;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Servicio en memoria encargado de manejar el CRUD de horarios.
 */
@ApplicationScoped
public class HorarioService {

    private final List<Horario> horarios = new CopyOnWriteArrayList<>();

    public List<Horario> obtenerHorarios() {
        return List.copyOf(horarios);
    }

    public Horario crearHorario(Horario horario) {
        horario.setCruce(verificarCruce(horario));
        horarios.add(horario);
        return horario;
    }

    public boolean eliminarHorario(String asignatura) {
        if (asignatura == null || asignatura.isBlank()) {
            return false;
        }
        return horarios.removeIf(h -> h.getAsignatura().equalsIgnoreCase(asignatura.trim()));
    }

    public boolean verificarCruce(Horario nuevoHorario) {
        return horarios.stream()
                .filter(h -> h.getDia().equalsIgnoreCase(nuevoHorario.getDia()))
                .anyMatch(h -> traslapa(h.getHoraInicio(), h.getHoraFin(),
                        nuevoHorario.getHoraInicio(), nuevoHorario.getHoraFin()));
    }

    private boolean traslapa(LocalTime inicioExistente,
                             LocalTime finExistente,
                             LocalTime inicioNuevo,
                             LocalTime finNuevo) {
        return inicioNuevo.isBefore(finExistente) && finNuevo.isAfter(inicioExistente);
    }
}

