package cl.duoc.gestion_estudiante.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Esto genera los Getter y Setter automáticamente
@Entity // Esto le dice a Spring que esta clase es una tabla de BD
public class GestionEstudianteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String rut;
    private String correo;
}