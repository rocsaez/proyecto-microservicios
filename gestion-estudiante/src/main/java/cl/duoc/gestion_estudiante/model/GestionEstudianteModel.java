package cl.duoc.gestion_estudiante.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * La anotación @Entity le dice a Spring que esta clase se convertirá en una tabla en la base de datos.
 * La anotación @Data es de Lombok y genera automáticamente los métodos getter, setter y constructores.
 */
@Data
@Entity
@Table(name = "estudiantes")
public class GestionEstudianteModel {

    /**
     * @Id indica que esta variable será la clave primaria (identificador único) de la tabla.
     * @GeneratedValue indica que la base de datos asignará este número automáticamente de forma secuencial.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * @Column(unique = true) asegura que el RUT no se repita en la base de datos.
     */
    @Column(nullable = false, unique = true)
    private String rut;
    private String nombreCompleto;
    private String correo;
}