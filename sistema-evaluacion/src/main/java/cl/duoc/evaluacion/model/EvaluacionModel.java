package cl.duoc.evaluacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "evaluaciones")
public class EvaluacionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombreEstudiante;
    private String asignatura;
    private Double nota;
}