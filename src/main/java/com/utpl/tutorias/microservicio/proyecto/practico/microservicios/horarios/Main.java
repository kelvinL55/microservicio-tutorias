package com.utpl.tutorias.microservicio.proyecto.practico.microservicios.horarios;

import io.helidon.microprofile.server.Server;

/**
 * Punto de entrada del microservicio de horarios y tutorías.
 * Inicia un servidor Helidon con los endpoints:
 * <ul>
 * <li>GET /horarios</li>
 * <li>POST /horarios</li>
 * <li>DELETE /horarios?asignatura=Materia</li>
 * </ul>
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Server server = Server.create();
        server.start();
    }
}
