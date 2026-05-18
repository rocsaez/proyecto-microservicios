package cl.duoc.sistema_inscripcion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionCreateDTO {

    @NotBlank(message = "El RUT del estudiante es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido")
    private String rutEstudiante;

    @NotBlank(message = "El nombre de la asignatura es obligatorio")
    private String nombreAsignatura;

    @NotBlank(message = "El periodo es obligatorio (ej: 2024-1)")
    private String periodo;
}