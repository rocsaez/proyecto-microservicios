package cl.duoc.sistema_biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BibliotecaDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
}