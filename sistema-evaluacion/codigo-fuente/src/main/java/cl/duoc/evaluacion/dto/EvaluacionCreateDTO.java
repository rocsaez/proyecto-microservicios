package cl.duoc.evaluacion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionCreateDTO {

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String nombreEstudiante;

    @NotBlank(message = "La asignatura es obligatoria")
    private String asignatura;

    @NotNull(message = "La nota es obligatoria")
    @Min(value = 1, message = "La nota mínima es 1.0")
    @Max(value = 7, message = "La nota máxima es 7.0")
    private Double nota;
}