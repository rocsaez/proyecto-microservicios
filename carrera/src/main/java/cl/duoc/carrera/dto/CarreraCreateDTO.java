package cl.duoc.carrera.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de datos requerido para crear o actualizar una carrera")
public class CarreraCreateDTO {

    @NotBlank(message = "El nombre de la carrera es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Schema(description = "Nombre oficial de la carrera universitaria", example = "Ingeniería en Informática")
    private String nombre;

    @NotBlank(message = "La facultad es obligatoria")
    @Schema(description = "Facultad a la que pertenece la carrera", example = "Ingeniería y Tecnologías de la Información")
    private String facultad;

    @NotNull(message = "La cantidad de semestres es obligatoria")
    @Min(value = 1, message = "Debe tener al menos 1 semestre")
    @Max(value = 15, message = "No puede exceder los 15 semestres")
    @Schema(description = "Duración total de la carrera medida en semestres académicos", example = "8")
    private Integer semestres;
}