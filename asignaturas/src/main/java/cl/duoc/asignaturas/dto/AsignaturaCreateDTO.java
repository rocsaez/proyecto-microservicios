package cl.duoc.asignaturas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// NUEVO IMPORT DE SWAGGER FOR DTOS
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de datos requerido para registrar o actualizar una Asignatura")
public class AsignaturaCreateDTO {

    @Schema(description = "Nombre oficial de la asignatura académica", example = "Cálculo Diferencial")
    @NotBlank(message = "El nombre de la asignatura es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Schema(description = "Sigla o código alfa-numérico único de la asignatura", example = "MAT101")
    @NotBlank(message = "La sigla es obligatoria")
    @Size(min = 3, max = 10, message = "La sigla debe tener entre 3 y 10 caracteres (ej: MAT101)")
    private String sigla;

    @Schema(description = "Puntaje de créditos académicos otorgados", example = "6", minimum = "1", maximum = "20")
    @NotNull(message = "La cantidad de créditos es obligatoria")
    @Min(value = 1, message = "La asignatura debe tener al menos 1 crédito")
    @Max(value = 20, message = "La asignatura no puede tener más de 20 créditos")
    private Integer creditos;

    @Schema(description = "Código identificador de la carrera vinculada", example = "ING-INF")
    @NotBlank(message = "El código de la carrera es obligatorio")
    private String codigoCarrera; 
    
    @Schema(description = "Identificador numérico único del profesor asignado", example = "14")
    @NotNull(message = "El ID del profesor es obligatorio")
    private Long idProfesor;
}