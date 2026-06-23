package cl.duoc.evaluacion.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluacionModelTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        EvaluacionModel evaluacion = new EvaluacionModel();
        assertNotNull(evaluacion);
    }

    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        EvaluacionModel evaluacion = new EvaluacionModel(
            1L, "19234567-8", "ASY4131", 6.5
        );

        assertEquals(1L, evaluacion.getId());
        assertEquals("19234567-8", evaluacion.getNombreEstudiante());
        assertEquals("ASY4131", evaluacion.getAsignatura());
        assertEquals(6.5, evaluacion.getNota());
    }

    @Test
    @DisplayName("Setters - debe permitir modificar cada propiedad de la calificación")
    void settersDebenPermitirModificarCampos() {
        EvaluacionModel evaluacion = new EvaluacionModel();

        evaluacion.setId(5L);
        evaluacion.setNombreEstudiante("18456123-K");
        evaluacion.setAsignatura("MDY3131");
        evaluacion.setNota(4.0);

        assertEquals(5L, evaluacion.getId());
        assertEquals("18456123-K", evaluacion.getNombreEstudiante());
        assertEquals("MDY3131", evaluacion.getAsignatura());
        assertEquals(4.0, evaluacion.getNota());
    }

    @Test
    @DisplayName("equals y hashCode - dos modelos con datos idénticos deben considerarse iguales")
    void dosModelosConMismosDatosDebenSerIguales() {
        EvaluacionModel e1 = new EvaluacionModel(1L, "19234567-8", "ASY4131", 5.0);
        EvaluacionModel e2 = new EvaluacionModel(1L, "19234567-8", "ASY4131", 5.0);

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    @DisplayName("toString - debe contener el rut o nombre del estudiante en su cadena")
    void toStringDebeContenerIdentificacion() {
        EvaluacionModel evaluacion = new EvaluacionModel(1L, "19234567-8", "ASY4131", 7.0);

        String texto = evaluacion.toString();

        assertNotNull(texto);
        assertTrue(texto.contains("19234567-8"));
    }
}