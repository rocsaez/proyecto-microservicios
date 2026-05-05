# Proyecto Semestral: Sistema Universitario de Microservicios Descentralizado

## Integrantes
- Rocío Sáez


## Arquitectura y Estado del Sistema
El sistema utiliza una arquitectura de microservicios **totalmente descentralizada**. Cada servicio posee su propio contenedor de aplicación y su propia instancia de base de datos MySQL aislada.

| Microservicio        | Puerto App | Puerto DB (Host) | DB Name               |
| :------------------- | :--------- | :--------------- | :-------------------- |
| Estudiantes          | 8081       | 3306             | universidad_backend   |
| Profesores           | 8082       | 3307             | universidad_backend   |
| Biblioteca           | 8083       | 3308             | universidad_backend   |
| Evaluación           | 8084       | 3309             | universidad_backend   |
| Inscripción          | 8085       | 3310             | universidad_backend   |
| Becas                | 8086       | 3311             | universidad_backend   |
| Salas                | 8087       | 3312             | universidad_backend   |
| Asistencia           | 8088       | 3313             | universidad_backend   |

## Despliegue Técnico
- **Instancia:** AWS EC2 (Ubuntu 24.04)
- **Orquestación:** Docker Compose (Descentralizado por módulo)
- **Repositorio:** https://github.com/rocsaez/proyecto-microservicios


