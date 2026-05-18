package cl.duoc.evaluacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {
    private Long id;
    private String rutEstudiante;
    private String codigoAsignatura;
    private String semestre;
    private Integer anio;
}