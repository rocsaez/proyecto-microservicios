package cl.duoc.evaluacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionDTO {
    private Long id;
    private String nombreEstudiante;
    private String asignatura;
    private Double nota;
}