package com.utpl.tutorias.microservicio.proyecto.practico.microservicios.horarios;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Recurso HTTP encargado de exponer el CRUD de horarios.
 */
@Path("/horarios")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class HorarioResource {

    private final HorarioService horarioService;

    @Inject
    public HorarioResource(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @GET
    public List<Horario> obtenerHorarios() {
        return horarioService.obtenerHorarios();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response crearHorario(String payload) {
        Horario horario = convertirDesdeTexto(payload);
        Horario creado = horarioService.crearHorario(horario);
        return Response.status(Response.Status.CREATED).entity(creado).build();
    }

    @DELETE
    public Response eliminarHorario(@QueryParam("asignatura") String asignatura) {
        if (asignatura == null || asignatura.isBlank()) {
            if (horarioService.eliminarTodos()) {
                return Response.ok(Map.of("mensaje", "Todos los horarios eliminados")).build();
            } else {
                return Response.ok(Map.of("mensaje", "no hay ningún horario activo o creado")).build();
            }
        }

        if (horarioService.eliminarHorario(asignatura)) {
            return Response.ok(Map.of("mensaje", "Horario eliminado", "asignatura", asignatura.trim())).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("mensaje", "Horario no encontrado", "asignatura", asignatura.trim()))
                .build();
    }

    private Horario convertirDesdeTexto(String payload) {
        if (payload == null || payload.isBlank()) {
            throw new BadRequestException("El cuerpo de la solicitud no puede estar vacío.");
        }
        String[] partes = payload.split(",");
        if (partes.length != 4) {
            throw new BadRequestException("Formato inválido. Usa asignatura,dia,horaInicio,horaFin");
        }
        try {
            String asignatura = partes[0].trim();
            String dia = partes[1].trim();
            LocalTime horaInicio = LocalTime.parse(partes[2].trim());
            LocalTime horaFin = LocalTime.parse(partes[3].trim());
            return new Horario(asignatura, dia, horaInicio, horaFin);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException("Formato de hora inválido. Usa HH:mm", ex);
        }
    }
}
