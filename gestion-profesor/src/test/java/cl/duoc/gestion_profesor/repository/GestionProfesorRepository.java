package cl.duoc.gestion_profesor.repository;

import cl.duoc.gestion_profesor.model.GestionProfesorModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GestionProfesorRepositoryTest {

    @Autowired
    private GestionProfesorRepository repository;

    @Test
    @DisplayName("save - debe persistir el profesor y generar un ID automáticamente")
    void debePersistirProfesorYAsignarId() {
        // Given
        GestionProfesorModel profesor = new GestionProfesorModel();
        profesor.setNombre("Erick González");
        profesor.setAsignatura("Arquitectura de Software");
        profesor.setCorreo("erick.gonzalez@duocuc.cl");

        // When
        GestionProfesorModel guardado = repository.save(profesor);

        // Then
        assertNotNull(guardado.getId());
        assertEquals("Erick González", guardado.getNombre());
    }

    @Test
    @DisplayName("findById - debe retornar vacío si el ID no existe")
    void debeRetornarVacioCuandoIdNoExiste() {
        Optional<GestionProfesorModel> resultado = repository.findById(999L);
        assertFalse(resultado.isPresent());
    }
}