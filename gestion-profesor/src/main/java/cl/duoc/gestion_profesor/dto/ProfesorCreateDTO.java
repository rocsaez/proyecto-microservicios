package cl.duoc.gestion_profesor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estructura de datos requerida para registrar o actualizar un docente")
public class ProfesorCreateDTO {

    @Schema(description = "Nombre completo del docente", example = "Erick González")
    @NotBlank(message = "El nombre del profesor es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Schema(description = "Cátedra o asignatura asignada", example = "Arquitectura de Software")
    @NotBlank(message = "La asignatura es obligatoria")
    private String asignatura;

    @Schema(description = "Correo institucional de contacto", example = "erick.gonzalez@duocuc.cl")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    private String correo;
}