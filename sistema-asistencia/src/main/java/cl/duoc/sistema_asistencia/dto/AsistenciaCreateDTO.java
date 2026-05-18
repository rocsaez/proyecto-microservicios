package cl.duoc.sistema_asistencia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaCreateDTO {
    @NotBlank(message = "El RUT es obligatorio")
    private String rutEstudiante; // Mantenemos String

    @NotBlank(message = "El código de la clase es obligatorio")
    private String codigoClase;
}