package cl.duoc.gestion_profesor.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class GestionProfesorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String asignatura;
    private String correo;
}