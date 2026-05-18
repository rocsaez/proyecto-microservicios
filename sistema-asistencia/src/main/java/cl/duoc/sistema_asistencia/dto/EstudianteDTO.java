package cl.duoc.sistema_asistencia.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {
    private Long id;
    private String nombre;
    private String rut;
    private String correo;
}