package cl.duoc.asignaturas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignaturaCreateDTO {

    @NotBlank(message = "El nombre de la asignatura es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La sigla es obligatoria")
    @Size(min = 3, max = 10, message = "La sigla debe tener entre 3 y 10 caracteres (ej: MAT101)")
    private String sigla;

    @NotNull(message = "La cantidad de créditos es obligatoria")
    @Min(value = 1, message = "La asignatura debe tener al menos 1 crédito")
    @Max(value = 20, message = "La asignatura no puede tener más de 20 créditos")
    private Integer creditos;

    @NotBlank(message = "El código de la carrera es obligatorio")
    private String codigoCarrera; 
    
    @NotNull(message = "El ID del profesor es obligatorio")
    private Long idProfesor;
}