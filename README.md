# Microservicio de Horarios y Tutorías

Microservicio en Helidon MP para gestionar horarios académicos y potenciales cruce de tutorías. Todo el código relevante para la actividad práctica se encuentra en `src/main/java/com/utpl/tutorias/microservicio/proyecto/practico/microservicios/horarios`.

## Requisitos

- JDK 21
- Maven 3.9+

## Compilación y ejecución

```bash
mvn package
java -jar target/microservicio-tutorias.jar
```

Tras iniciar la aplicación, los endpoints quedan disponibles en `http://localhost:8080`.

## Endpoints principales

- `GET /horarios` lista todos los horarios registrados.
- `POST /horarios` crea un horario enviando en el cuerpo un texto plano con el formato `asignatura,dia,horaInicio,horaFin`.
- `DELETE /horarios?asignatura=Nombre` elimina el horario asociado a la asignatura indicada.
- `GET /health` y `GET /metrics` expuestos automáticamente por Helidon para revisar salud y métricas.

## Pruebas rápidas con cURL

```bash
# Crear horario
curl -X POST http://localhost:8080/horarios \
     -H "Content-Type: text/plain" \
     -d "Practicum II,Lunes,19:00,20:00"

# Consultar horarios
curl http://localhost:8080/horarios

# Eliminar horario
curl -X DELETE "http://localhost:8080/horarios?asignatura=Practicum II"

# Verificar salud
curl http://localhost:8080/health

# Verificar métricas
curl http://localhost:8080/metrics
```

También puedes replicar estas peticiones desde Bruno, Postman u otra herramienta REST enviando el cuerpo como texto plano.

## Docker (opcional)

```bash
docker build -t microservicio-tutorias .
docker run --rm -p 8080:8080 microservicio-tutorias:latest
```

## Kubernetes (opcional)

El archivo `app.yaml` permite desplegar el microservicio en un clúster compatible con Kubernetes u OKE. Ajusta los recursos según tu entorno y aplica los manifiestos con `kubectl apply -f app.yaml`.
