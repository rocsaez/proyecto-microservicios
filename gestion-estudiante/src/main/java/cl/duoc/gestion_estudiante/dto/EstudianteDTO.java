package cl.duoc.gestion_estudiante.dto;

import io.swagger.v3.oas.annotations.media.Schema; // <-- Importación de Swagger
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Objeto que representa a un estudiante ya registrado en el sistema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
    
    @Schema(description = "Identificador único autogenerado en la base de datos", example = "1")
    private Long id;
    
    @Schema(description = "Nombre completo del estudiante", example = "Juan Pérez")
    private String nombre;
    
    @Schema(description = "RUT del estudiante", example = "19283746-K")
    private String rut;
    
    @Schema(description = "Correo electrónico del estudiante", example = "juan.perez@duocuc.cl")
    private String correo;
}