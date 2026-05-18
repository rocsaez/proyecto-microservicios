package cl.duoc.sistema_asistencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaDTO {
    private Long id;
    private String rutEstudiante;
    private String codigoClase;
    private LocalDateTime fechaHora;
}