package cl.duoc.sistema_inscripcion.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inscripciones")
public class SistemaInscripcionesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String rutEstudiante;
    private String nombreAsignatura;
    private String periodo;
}