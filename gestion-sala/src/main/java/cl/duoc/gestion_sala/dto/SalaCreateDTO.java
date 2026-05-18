package cl.duoc.gestion_sala.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaCreateDTO {

    @NotBlank(message = "El nombre de la sala es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombreSala;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1 persona")
    @Max(value = 500, message = "La capacidad no puede superar las 500 personas")
    private Integer capacidad;

    @NotBlank(message = "El tipo de sala es obligatorio (Aula, Laboratorio, etc.)")
    private String tipo;

    @NotBlank(message = "La ubicación es obligatoria (Piso/Edificio)")
    private String ubicacion;
}