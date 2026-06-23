package cl.duoc.beca.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BecaCreateDTO {

    @NotBlank(message = "El nombre de la beca es obligatorio")
    private String nombreBeca;

    @NotBlank(message = "El RUT del estudiante es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido (ej: 12345678-9)")
    private String rutEstudiante;

    @NotNull(message = "El porcentaje es obligatorio")
    @Min(value = 1, message = "El descuento mínimo es 1%")
    @Max(value = 100, message = "El descuento no puede superar el 100%")
    private Double porcentajeDescuento;

    @NotBlank(message = "El estado es obligatorio (Ej: Activo/Inactivo)")
    private String estado;
}