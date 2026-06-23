# Sistema de Gestión Universitaria - Arquitectura de Microservicios

Este repositorio centraliza el ecosistema distribuido para la administración integral de la universidad. La arquitectura está compuesta por **8 microservicios independientes** construidos con **Spring Boot 3.x**, **Java 21**, comunicación síncrona mediante **Spring Cloud OpenFeign** con tolerancia a fallos (timeouts configurados), y persistencia aislada a través de contenedores **MySQL 8.0**.

---

##  Arquitectura y Puertos del Ecosistema

Cada microservicio dispone de su propia base de datos dedicada, garantizando el principio de diseño *Database-per-Service*.

| Microservicio | Puerto App | Contenedor Workspace | Base de Datos (MySQL) | Puerto DB Host | Redes Docker |
| :--- | :---: | :--- | :--- | :---: | :--- |
| **Asignaturas** | `8080` | `entorno_asignaturas` | `asignaturas_db` | `3314` | `red_asignaturas`, `red_universidad` |
| **Gestión Estudiante** | `8081` | `entorno_programacion1` | `estudiantes_db` | `3311` | `red_estudiantes`, `red_universidad` |
| **Gestión Profesor** | `8082` | `entorno_profesor` | `universidad_backend` | `3307` | `red_profesores` |
| **Sistema Biblioteca** | `8083` | `entorno_biblioteca` | `biblioteca_db` | `3308` | `red_biblioteca`, `red_universidad` |
| **Evaluación** | `8084` | `entorno_evaluacion` | `evaluacion_db` | `3309` | `red_evaluacion`, `red_universidad` |
| **Sistema Inscripción** | `8085` | `entorno_inscripcion` | `inscripciones_db` | `3310` | `red_inscripcion`, `red_universidad` |
| **Beca** | `8086` | `entorno_beca` | `becas_db` | `3311` | `red_becas` |
| **Gestión Sala** | `8087` | `entorno_sala` | `salas_db` | `3312` | `red_salas`, `red_universidad` |
| **Sistema Asistencia** | `8088` | `entorno_asistencia` | `asistencia_db` | `3313` | `red_asistencia`, `red_universidad` |
| **Carrera** | `8089` | `entorno_carrera` | `carrera_db` | `3315` | `red_carrera`, `red_universidad` |
| **Pagos** | `8090` | `entorno_pagos` | `pagos_db` | `3316` | `red_pagos`, `red_universidad` |

---

##  Catálogo de Servicios y Enlaces Swagger API (OpenAPI 3.0)

Todas las APIs se encuentran completamente auto-documentadas mediante SpringDoc. Al levantar el ecosistema localmente, puedes acceder a los paneles interactivos de Swagger en las siguientes direcciones:

### 1. Asignaturas (`Port: 8080`)
* **Context Path Base:** `/api/asignaturas`
* **Operaciones principales:** CRUD completo de asignaturas, búsquedas por ID y por sigla única académica.
* **Enlace Swagger UI:** [http://localhost:8080/doc/swagger-ui.html](http://localhost:8080/doc/swagger-ui.html)

### 2. Gestión Estudiante (`Port: 8081`)
* **Context Path Base:** `/api/estudiantes`
* **Operaciones principales:** Registro de alumnos, listados, búsquedas por ID, actualizaciones y consulta por RUT con compatibilidad de llamadas Feign internas.
* **Enlace Swagger UI:** [http://localhost:8081/doc/swagger-ui.html](http://localhost:8081/doc/swagger-ui.html)

### 3. Gestión Profesor (`Port: 8082`)
* **Context Path Base:** `/api/profesores`
* **Operaciones principales:** Mantenimiento integral de la planta docente (Creación, lectura, edición y borrado lógico/físico).
* **Enlace Swagger UI:** *(Módulo Core sin UI expuesta directamente)*

### 4. Sistema Biblioteca (`Port: 8083`)
* **Context Path Base:** `/api/biblioteca`
* **Operaciones principales:** Administración y catalogación de libros escolares y control del stock literario.
* **Enlace Swagger UI:** [http://localhost:8083/doc/swagger-ui.html](http://localhost:8083/doc/swagger-ui.html)

### 5. Evaluaciones (`Port: 8084`)
* **Context Path Base:** `/api/evaluaciones`
* **Operaciones principales:** Gestión académica de calificaciones parciales y exámenes, validaciones de rangos de notas y eliminación controlada.
* **Enlace Swagger UI:** [http://localhost:8084/doc/swagger-ui.html](http://localhost:8084/doc/swagger-ui.html)

### 6. Sistema Inscripción (`Port: 8085`)
* **Context Path Base:** `/api/inscripciones`
* **Operaciones principales:** Inscripción formal de alumnos en asignaturas vigentes cruzando datos distribuidos mediante clientes Feign.
* **Enlace Swagger UI:** [http://localhost:8085/doc/swagger-ui.html](http://localhost:8085/doc/swagger-ui.html)

### 7. Becas (`Port: 8086`)
* **Context Path Base:** `/api/becas`
* **Operaciones principales:** Asignación, control y revocación de beneficios arancelarios estudiantiles financieros.
* **Enlace Swagger UI:** [http://localhost:8086/doc/swagger-ui.html](http://localhost:8086/doc/swagger-ui.html)

### 8. Gestión Sala (`Port: 8087`)
* **Context Path Base:** `/api/salas`
* **Operaciones principales:** Registro, parametrización y control de inventario de las aulas y laboratorios universitarios.
* **Enlace Swagger UI:** [http://localhost:8087/doc/swagger-ui.html](http://localhost:8087/doc/swagger-ui.html)

### 9. Sistema Asistencia (`Port: 8088`)
* **Context Path Base:** `/api/asistencias`
* **Operaciones principales:** Control de asistencia diaria por bloques de salas y módulos asignados.
* **Enlace Swagger UI:** [http://localhost:8088/doc/swagger-ui.html](http://localhost:8088/doc/swagger-ui.html)

### 10. Carreras (`Port: 8089`)
* **Context Path Base:** `/api/carreras`
* **Operaciones principales:** Registro corporativo e institucional de los programas y mallas de carreras ofertadas.
* **Enlace Swagger UI:** [http://localhost:8089/doc/swagger-ui.html](http://localhost:8089/doc/swagger-ui.html)

### 11. Procesamiento de Pagos (`Port: 8090`)
* **Context Path Base:** `/api/pagos`
* **Operaciones principales:** Registro transaccional de pagos, validación con microservicios de estudiantes y becas asociadas vía Feign.
* **Enlace Swagger UI:** [http://localhost:8090/doc/swagger-ui.html](http://localhost:8090/doc/swagger-ui.html)

---

## Evidencia y Reporte de Pruebas Automatizadas (Suite JUnit 5)

El sistema ha sido verificado exhaustivamente ejecutando las suites de pruebas integradas en cada contenedor. A continuación, se detalla el reporte consolidado de éxito extraído de la última ejecución en el servidor de desarrollo:

### Resumen Ejecutivo de Cobertura

Todos los módulos core completaron el ciclo de vida de pruebas con **0 errores** y **0 fallos**, garantizando la integridad de las APIs y capas de servicio:

| Microservicio / Módulo | Tests Ejecutados | Fallos (`Failures`) | Errores (`Errors`) | Tiempo Total de Build | Estado Final |
| :--- | :---: | :---: | :---: | :---: | :---: |
|  **Gestión Estudiante** | **11** | 0 | 0 | 21.50 s | `BUILD SUCCESS`  |
|  **Asignaturas** | **13** | 0 | 0 | 25.89 s | `BUILD SUCCESS` |
|  **Sistema Inscripción** | **21** | 0 | 0 | 23.98 s | `BUILD SUCCESS`  |
|  **Gestión Sala** | **17** | 0 | 0 | 27.14 s | `BUILD SUCCESS`  |
|  **Gestión Profesor** | *Suite Core* | 0 | 0 | 15.21 s | `BUILD SUCCESS`  |

---

### Casos de Prueba Verificados en Consola

Las pruebas unitarias y de integración cubren los siguientes comportamientos críticos del negocio, los cuales fueron validados exitosamente por el motor de Spring:

1. **`GestionEstudianteControllerTest` (Spring Boot 3.3.4):**
   * Verificación del arranque del contexto (`ContextLoader`) utilizando perfiles de prueba y entornos simulados de Servlet de manera exitosa en 0.886 segundos.
2. **`AsignaturaServiceTest` & `AsignaturaRepositoryTest`:**
   * Validaciones de persistencia relacional (`insert into asignaturas...`).
   * Captura y manejo estructurado de excepciones de validación de campos obligatorios (`La sigla es obligatoria`, `El ID del profesor es obligatorio`).
3. **`SistemaInscripcionesServiceTest` & `ControllerTest`:**
   * Validación automática de la existencia del estudiante y de la asignatura en flujos cruzados.
   * Pruebas de integración con MockMvc simulando errores de formato y restricciones de patrones (ej: Regex de validación de formato de RUN/RUT).
4. **`GestionSalaApplicationTests`:**
   * Aislamiento completo del motor de persistencia mediante el dialecto dinámico `H2Dialect` apuntando al repositorio simulado `jdbc:h2:mem:testdb` sin generar colisiones con la base de datos de producción.

Para volver a correr este reporte exacto en cualquiera de los entornos dockerizados, ejecute:
```bash
docker exec -it <nombre_contenedor> mvn test

## Instrucciones de Despliegue Local (Docker Compose)

Cada módulo contiene su propio descriptor de topología multi-contenedor independiente. Para desplegar cualquiera de los microservicios, ubícate en la carpeta raíz del módulo correspondiente y ejecuta:

```bash
# 1. Levantar la infraestructura (Base de Datos + Workspace Java)
docker compose up -d

# 2. Verificar el estado de los contenedores activos
docker ps

# 3. Compilar y correr el set completo de pruebas unitarias automatizadas
# (Ejemplo para el módulo de Gestión de Salas)
docker exec -w /app -it entorno_sala mvn test

