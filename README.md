#  Sistema de Gestión Universitaria - Hito 2

Este proyecto implementa una arquitectura de microservicios para la gestión académica integral, utilizando **Spring Boot**, **Docker Compose** y **MySQL**. En esta fase, nos enfocamos en la intercomunicación y consistencia de datos entre módulos.

## Comunicación entre microservicios (Hito 2)

### Diagrama de dependencias y flujo de datos
La comunicación se realiza de forma interna mediante la red de Docker (`red_universidad`), utilizando nombres de servicios para el descubrimiento de hosts.

| Microservicio Origen | Microservicio Destino | Propósito de la conexión |
| :--- | :--- | :--- |
| **Inscripciones** | Estudiantes | Validar que el alumno existe y está activo antes de inscribirlo. |
| **Inscripciones** | Asignaturas | Verificar que la asignatura tenga cupos y exista. |
| **Pagos** | Estudiantes | Obtener el RUT y nombre para generar la boleta de pago. |
| **Pagos** | Becas | Consultar si el alumno tiene un descuento asignado. |
| **Asignaturas** | Profesores | Saber quién es el docente que dicta la materia. |
| **Asignaturas** | Carrera | Validar a qué malla curricular pertenece la materia. |
| **Asistencia** | Inscripciones | Confirmar que el alumno realmente está inscrito en esa clase. |
| **Asistencia** | Salas | Verificar que la sala donde se marca asistencia es la correcta. |
| **Biblioteca** | Estudiantes | Saber a quién se le está prestando un libro. |
| **Evaluación** | Asignaturas | Saber sobre qué materia se está calificando. |

### Tecnología utilizada
- **Cliente REST:** `Feign Client`. 
  - *Justificación:* Permite una integración declarativa, facilitando la escalabilidad y el mantenimiento del código al tratar las llamadas externas como interfaces locales.
- **Manejo de errores:** `@ControllerAdvice` + `FeignException` para asegurar que las fallas en un servicio destino no provoquen caídas en cascada (Resiliencia).
- **Logs:** SLF4J para trazabilidad de peticiones inter-servicios.
- **Pruebas de integración:** Colección Postman en `/postman/hito2-integracion.json`.

### Escenario de despliegue
- **[X] Escenario A — Todos los servicios en una sola instancia EC2**
  - **Instancia:** AWS EC2 (Ubuntu 24.04 LTS).
  - **Puertos:** Rango 8080-8090 habilitado en el Security Group de AWS.
  - **Base de Datos:** Cada microservicio cuenta con su propio contenedor MySQL independiente, comunicándose internamente por el puerto `3306`.

##  Cómo probar la integración

1. **Levantar todos los servicios:**
   ```bash
   docker compose up -d
- **Repositorio:** https://github.com/rocsaez/proyecto-microservicios


