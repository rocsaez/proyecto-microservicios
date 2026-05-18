package cl.duoc.asignaturas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorDTO {
    private Long id;
    private String nombre;
    private String asignatura;
    private String correo;
}