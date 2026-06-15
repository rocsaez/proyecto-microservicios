# Plan de Pruebas Unitarias y Cobertura de Reglas de Negocio

Este documento detalla la estrategia, cobertura y resultados de las pruebas automatizadas implementadas para el módulo **Gestión de Estudiantes**, garantizando el aislamiento de la base de datos y la velocidad del proceso según las buenas prácticas del desarrollo de software.

---

## Estrategia de Testing (Base de la Pirámide)

Siguiendo la **Pirámide de Testing**, nos enfocamos en la base: **Pruebas Unitarias**. 
Para lograrlo, se implementó el framework **JUnit 5** en conjunto con **Mockito** para simular las dependencias del repositorio (`GestionEstudianteRepository`), logrando pruebas que cumplen con la regla **F.I.R.S.T.** (Rápidas, Aisladas y Repetibles).

###  Componentes de Mockito Utilizados
* `@ExtendWith(MockitoExtension.class)`: Activa el motor de Mockito en JUnit 5.
* `@Mock`: Crea un clon simulado del repositorio sin tocar la base de datos real en Docker.
* `@InjectMocks`: Inyecta el repositorio simulado dentro del servicio real (`GestionEstudianteService`).

---

##  Cobertura Actual de Reglas de Negocio

Se diseñaron escenarios de prueba siguiendo la estructura **Given-When-Then (AAA)** para cubrir tanto el flujo exitoso como la gestión de errores del servicio:

| Regla de Negocio / Flujo | Tipo de Test | Estado | Escenario Verificado (Camino Feliz / Error) |
| :--- | :--- | :--- | :--- |
| **Búsqueda por ID Existente** | Unitario (`Mockito`) | CUBIERTA | **Camino Feliz:** Retorna un objeto `EstudianteDTO` válido y con los datos correctos si el ID existe en el sistema. |
| **Búsqueda por ID Inexistente** | Unitario (`Mockito`) |  CUBIERTA | **Caso de Error:** Lanza de forma controlada la excepción personalizada `RecursoNoEncontradoException` (Error 404). |

---

##  Evidencia de Ejecución Exitosa (Maven)

Al ejecutar el comando de automatización `mvn test`, la suite completa de pruebas compila y finaliza con un **100% de éxito**, asegurando que el código está listo para su integración o entrega semestral.

```text
[INFO] Running cl.duoc.gestion_estudiante.service.EstudianteServiceTest
2026-06-15T03:30:38.020Z  WARN 21393 --- [gestion-estudiante] [           main] c.d.g.service.GestionEstudianteService   : Búsqueda fallida: ID 2 no existe
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.139 s -- in cl.duoc.gestion_estudiante.service.EstudianteServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
