package cl.duoc.gestion_estudiante.repository;

import cl.duoc.gestion_estudiante.model.GestionEstudianteModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GestionEstudianteRepositoryTest {

    @Autowired
    private GestionEstudianteRepository repository;

    @Test
    @DisplayName("findByRut - debe retornar el estudiante correcto si el RUT existe")
    void debeEncontrarEstudiantePorRut() {
        GestionEstudianteModel estudiante = new GestionEstudianteModel();
        estudiante.setNombre("Andres Bello");
        estudiante.setRut("22222222-2");
        estudiante.setCorreo("andres@duocuc.cl");
        repository.save(estudiante);

        Optional<GestionEstudianteModel> resultado = repository.findByRut("22222222-2");

        assertTrue(resultado.isPresent());
        assertEquals("Andres Bello", resultado.get().getNombre());
    }
}