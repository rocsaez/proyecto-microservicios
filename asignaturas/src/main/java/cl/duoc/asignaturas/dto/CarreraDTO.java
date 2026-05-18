package cl.duoc.asignaturas.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {
    private Long id;
    private String nombre;
    private String facultad;
    private Integer semestres;
}