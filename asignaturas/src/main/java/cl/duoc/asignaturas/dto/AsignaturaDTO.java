package cl.duoc.asignaturas.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignaturaDTO {
    private Long id;
    private String nombre;
    private String sigla;
    private Integer creditos;
    private String codigoCarrera;
    private Long idProfesor;
}