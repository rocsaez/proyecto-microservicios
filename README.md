# Sistema de Gestión Universitaria - Arquitectura de Microservicios

Este repositorio centraliza el ecosistema distribuido para la administración integral de la universidad. La arquitectura está compuesta por 11 microservicios independientes construidos con Spring Boot 3.x, Java 21, comunicación síncrona mediante Spring Cloud OpenFeign con tolerancia a fallos, descubrimiento dinámico de servicios mediante Netflix Eureka Server y enrutamiento perimetral centralizado a través de Spring Cloud Gateway.

## Arquitectura del Ecosistema Desplegado en AWS

El sistema se encuentra distribuido en la nube de Amazon Web Services (AWS EC2) bajo el siguiente esquema de infraestructura:

* **Servidor de Descubrimiento (Eureka):** Centraliza el registro dinámico de instancias.
  * IP Pública AWS: 3.86.130.217 (Puerto: 8761)
* **Nodo de Microservicios y API Gateway:** Aloja los 11 microservicios core y la puerta de enlace pública.
  * IP Pública AWS: 54.91.52.2 (Puerto Gateway: 8099)
* ** Link Github EUREKA https://github.com/rocsaez/ms-eureka.git

### Matriz de Microservicios, Puertos y Persistencia

Cada microservicio dispone de su propia base de datos dedicada, garantizando el principio de diseño Database-per-Service.

| Microservicio | Puerto Interno | Contenedor Workspace | Base de Datos (MySQL) | Puerto DB Host | Redes Docker |
| :--- | :---: | :--- | :--- | :---: | :--- |
| Asignaturas (MS-ASIGNATURAS) | 8080 | entorno_asignaturas | asignaturas_db | 3314 | red_asignaturas, red_universidad |
| Gestión Estudiante (MS-ESTUDIANTES) | 8081 | entorno_programacion1 | estudiantes_db | 3311 | red_estudiantes, red_universidad |
| Gestión Profesor (MS-PROFESORES) | 8082 | universidad_backend | universidad_backend_db | 3307 | red_profesores |
| Sistema Biblioteca (MS-BIBLIOTECA) | 8083 | entorno_biblioteca | biblioteca_db | 3308 | red_biblioteca, red_universidad |
| Evaluación (MS-EVALUACIONES) | 8084 | entorno_evaluacion | evaluacion_db | 3309 | red_evaluacion, red_universidad |
| Sistema Inscripción (MS-INSCRIPCIONES) | 8085 | entorno_inscripcion | inscripciones_db | 3310 | red_inscripcion, red_universidad |
| Beca (MS-BECAS) | 8086 | entorno_beca | becas_db | 3311 | red_becas |
| Gestión Sala (MS-SALAS) | 8087 | entorno_sala | salas_db | 3312 | red_salas, red_universidad |
| Sistema Asistencia (MS-ASISTENCIAS) | 8088 | entorno_asistencia | asistencia_db | 3313 | red_asistencia, red_universidad |
| Carrera (MS-CARRERAS) | 8089 | entorno_carrera | carrera_db | 3315 | red_carrera, red_universidad |
| Pagos (MS-PAGOS) | 8090 | entorno_pagos | pagos_db | 3316 | red_pagos, red_universidad |

---

## Catálogo de Rutas y Enlaces Centralizados de Swagger (OpenAPI 3.0)

Gracias a la integración de SpringDoc OpenAPI y Spring Cloud Gateway, ya no es necesario acceder de forma aislada a los puertos de cada backend. Toda la documentación interactiva se encuentra unificada y accesible públicamente a través del puerto del API Gateway.

* **URL del Panel Unificado en AWS:** http://54.91.52.2:8099/swagger-ui.html
* **URL de Eureka Server Dashboard:** http://3.86.130.217:8761/

Desde el selector superior derecho de la interfaz de Swagger UI en el Gateway, podrás alternar entre las especificaciones técnicas de las siguientes rutas públicas:

| # | Servicio / Módulo | Context Path Base | Enrutamiento Eureka (lb://) | Endpoint de API Docs del Gateway |
| :---: | :--- | :--- | :--- | :--- |
| 1 | Asignaturas | `/api/asignaturas/**` | `lb://MS-ASIGNATURAS` | `/v3/api-docs/MS-ASIGNATURAS` |
| 2 | Becas | `/api/becas/**` | `lb://MS-BECAS` | `/v3/api-docs/MS-BECAS` |
| 3 | Carreras | `/api/carreras/**` | `lb://MS-CARRERAS` | `/v3/api-docs/MS-CARRERAS` |
| 4 | Evaluaciones | `/api/evaluaciones/**` | `lb://MS-EVALUACIONES` | `/v3/api-docs/MS-EVALUACIONES` |
| 5 | Estudiantes | `/api/estudiantes/**` | `lb://MS-ESTUDIANTES` | `/v3/api-docs/MS-ESTUDIANTES` |
| 6 | Profesores | `/api/profesores/**` | `lb://MS-PROFESORES` | `/v3/api-docs/MS-PROFESORES` |
| 7 | Salas | `/api/salas/**` | `lb://MS-SALAS` | `/v3/api-docs/MS-SALAS` |
| 8 | Pagos | `/api/pagos/**` | `lb://MS-PAGOS` | `/v3/api-docs/MS-PAGOS` |
| 9 | Asistencias | `/api/asistencias/**` | `lb://MS-ASISTENCIAS` | `/v3/api-docs/MS-ASISTENCIAS` |
| 10 | Biblioteca | `/api/biblioteca/**` | `lb://MS-BIBLIOTECA` | `/v3/api-docs/MS-BIBLIOTECA` |
| 11 | Inscripciones | `/api/inscripciones/**` | `lb://MS-INSCRIPCIONES` | `/v3/api-docs/MS-INSCRIPCIONES` |

---

## Evidencia y Reporte de Pruebas Automatizadas (Suite JUnit 5)

El sistema ha sido verificado exhaustivamente ejecutando las suites de pruebas integradas en cada contenedor. A continuación, se detalla el reporte consolidado de éxito extraído de la última ejecución en el servidor de desarrollo:

### Resumen Ejecutivo de Cobertura

Todos los módulos core completaron el ciclo de vida de pruebas con 0 errores y 0 fallos, garantizando la integridad de las APIs y capas de servicio:

| Microservicio / Módulo | Tests Ejecutados | Fallos (Failures) | Errores (Errors) | Tiempo Total de Build | Estado Final |
| :--- | :---: | :---: | :---: | :---: | :--- |
| Gestión Estudiante | 11 | 0 | 0 | 21.50 s | BUILD SUCCESS |
| Asignaturas | 13 | 0 | 0 | 25.89 s | BUILD SUCCESS |
| Sistema Inscripción | 21 | 0 | 0 | 23.98 s | BUILD SUCCESS |
| Gestión Sala | 17 | 0 | 0 | 27.14 s | BUILD SUCCESS |
| Gestión Profesor | Suite Core | 0 | 0 | 15.21 s | BUILD SUCCESS |

### Casos de Prueba Verificados en Consola

Las pruebas unitarias y de integración cubren los siguientes comportamientos críticos del negocio, los cuales fueron validados exitosamente por el motor de Spring:

* **GestionEstudianteControllerTest (Spring Boot 3.3.4):** Verificación del arranque del contexto (ContextLoader) utilizando perfiles de prueba y entornos simulados de Servlet de manera exitosa en 0.886 segundos.
* **AsignaturaServiceTest & AsignaturaRepositoryTest:** Validaciones de persistencia relacional (`insert into asignaturas...`) y captura estructurada de excepciones de validación de campos obligatorios ("La sigla es obligatoria", "El ID del profesor es obligatorio").
* **SistemaInscripcionesServiceTest & ControllerTest:** Validación automática de la existencia del estudiante y de la asignatura en flujos cruzados inter-servicio simulando errores de formato y restricciones de patrones (ej: Regex de validación de formato de RUN/RUT).
* **GestionSalaApplicationTests:** Aislamiento completo del motor de persistencia mediante el dialecto dinámico `H2Dialect` apuntando al repositorio en memoria `jdbc:h2:mem:testdb` sin generar colisiones con las bases de datos transaccionales.

Para volver a correr este reporte exacto en cualquiera de los entornos dockerizados, ejecute:

```bash
docker exec -it <nombre_contenedor> mvn test

Detener y remover por completo contenedores antiguos, redes y volúmenes huérfanos
docker compose down --remove-orphans

 Reconstruir las imágenes locales (eliminando caché intermedia) y levantar en segundo plano
docker compose up -d --build

 Monitorear los logs en tiempo real para verificar el correcto registro en el servidor Eureka
docker compose logs -f
