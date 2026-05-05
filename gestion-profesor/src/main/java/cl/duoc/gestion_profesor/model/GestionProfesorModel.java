package cl.duoc.gestion_profesor.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * La anotación @Entity le dice a Spring que esta clase se convertirá en una tabla en la base de datos.
 * La anotación @Data de Lombok genera automáticamente los métodos getter, setter y constructores.
 */
@Data
@Entity
@Table(name = "profesores")
public class GestionProfesorModel {

    /**
     * @Id indica la clave primaria.
     * @GeneratedValue indica que el ID será autoincremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rut;

    private String nombreCompleto;
    private String correo;
}