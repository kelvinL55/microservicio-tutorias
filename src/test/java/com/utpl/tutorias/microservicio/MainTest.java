
package com.utpl.tutorias.microservicio;

import com.utpl.tutorias.microservicio.proyecto.practico.microservicios.horarios.Horario;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import io.helidon.microprofile.testing.junit5.HelidonTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

@HelidonTest
class MainTest {

    @Inject
    private WebTarget target;

    @BeforeEach
    void limpiarDatos() {
        Horario[] existentes = target.path("horarios")
                .request()
                .get(Horario[].class);
        for (Horario horario : existentes) {
            target.path("horarios")
                    .queryParam("asignatura", horario.getAsignatura())
                    .request()
                    .delete();
        }
    }

    @Test
    void healthDebeResponder200() {
        Response response = target
                .path("health")
                .request()
                .get();
        assertThat(response.getStatus(), is(200));
    }

    @Test
    void crudDeHorarios() {
        Horario[] inicial = target.path("horarios")
                .request()
                .get(Horario[].class);
        assertThat(inicial.length, is(0));

        Horario creado = target.path("horarios")
                .request()
                .post(Entity.text("Practicum II,Lunes,19:00,20:00"), Horario.class);
        assertThat(creado, is(notNullValue()));
        assertThat(creado.getAsignatura(), is("Practicum II"));
        assertThat(creado.getDia(), is("Lunes"));
        assertThat(creado.isCruce(), is(false));

        Horario[] despuesDeCrear = target.path("horarios")
                .request()
                .get(Horario[].class);
        assertThat(despuesDeCrear, arrayWithSize(1));

        Response deleteResponse = target.path("horarios")
                .queryParam("asignatura", "Practicum II")
                .request()
                .delete();
        assertThat(deleteResponse.getStatus(), is(200));

        Horario[] finalLista = target.path("horarios")
                .request()
                .get(Horario[].class);
        assertThat(finalLista.length, is(0));
    }

    @Test
    void detectaCruces() {
        target.path("horarios")
                .request()
                .post(Entity.text("Microservicios,Martes,10:00,12:00"), Horario.class);

        Horario conCruce = target.path("horarios")
                .request()
                .post(Entity.text("Arquitectura,Martes,11:00,12:30"), Horario.class);

        assertTrue(conCruce.isCruce(), "El horario debería marcar cruce al traslapar con otro existente.");
    }
}
