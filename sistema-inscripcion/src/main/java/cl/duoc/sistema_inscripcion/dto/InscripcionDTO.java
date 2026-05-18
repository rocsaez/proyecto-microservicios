package cl.duoc.sistema_inscripcion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO {
    private Long id;
    private String rutEstudiante;
    private String nombreAsignatura;
    private String periodo;
}