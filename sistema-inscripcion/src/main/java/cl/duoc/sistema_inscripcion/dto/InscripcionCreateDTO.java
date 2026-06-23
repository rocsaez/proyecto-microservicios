package cl.duoc.sistema_inscripcion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de entrada para el envío y creación de una inscripción")
public class InscripcionCreateDTO {

    @NotBlank(message = "El RUT del estudiante es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido")
    @Schema(description = "RUT del estudiante sin puntos y con guion", example = "12345678-9")
    private String rutEstudiante;

    @NotBlank(message = "El nombre de la asignatura es obligatorio")
    @Schema(description = "Nombre completo de la asignatura a inscribir", example = "Programación de Aplicaciones Móviles")
    private String nombreAsignatura;

    @NotBlank(message = "El periodo es obligatorio (ej: 2024-1)")
    @Schema(description = "Periodo académico correspondiente", example = "2026-1")
    private String periodo;
}