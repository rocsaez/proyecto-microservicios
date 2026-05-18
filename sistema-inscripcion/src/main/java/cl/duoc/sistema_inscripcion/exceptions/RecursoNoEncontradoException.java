package cl.duoc.sistema_inscripcion.exceptions;

// Debe heredar de RuntimeException para que Spring la maneje
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}