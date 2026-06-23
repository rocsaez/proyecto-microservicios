package cl.duoc.carrera.repository;

import cl.duoc.carrera.model.CarreraModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarreraRepositoryTest {

    @Autowired
    private CarreraRepository repository;

    @Test
    @DisplayName("save - debe persistir la carrera y asignar un ID generado automáticamente")
    void debePersistirCarreraYAsignarIdGenerado() {
        // Given
        CarreraModel carrera = new CarreraModel();
        carrera.setNombre("Ingeniería en Informática");
        carrera.setFacultad("Ingeniería");
        carrera.setSemestres(8);

        // When
        CarreraModel guardado = repository.save(carrera);

        // Then
        assertNotNull(guardado.getId());
        assertTrue(guardado.getId() > 0);
        assertEquals("Ingeniería en Informática", guardado.getNombre());
        assertEquals(8, guardado.getSemestres());
    }

    @Test
    @DisplayName("findAll - debe retornar todas las carreras guardadas en la BD")
    void debeRetornarTodasLasCarrerasGuardadas() {
        // Given
        CarreraModel c1 = new CarreraModel();
        c1.setNombre("Diseño"); c1.setFacultad("Arte"); c1.setSemestres(8);
        repository.save(c1);

        CarreraModel c2 = new CarreraModel();
        c2.setNombre("Enfermería"); c2.setFacultad("Salud"); c2.setSemestres(10);
        repository.save(c2);

        // When
        List<CarreraModel> carreras = repository.findAll();

        // Then
        assertNotNull(carreras);
        assertEquals(2, carreras.size());
    }

    @Test
    @DisplayName("findById - debe retornar la carrera correcta cuando el ID existe")
    void debeEncontrarCarreraPorIdExistente() {
        // Given
        CarreraModel carrera = new CarreraModel();
        carrera.setNombre("Psicología"); carrera.setFacultad("Sociales"); carrera.setSemestres(10);
        CarreraModel guardado = repository.save(carrera);

        // When
        Optional<CarreraModel> resultado = repository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Psicología", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<CarreraModel> resultado = repository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar la carrera de la base de datos")
    void debeEliminarCarreraPorId() {
        // Given
        CarreraModel carrera = new CarreraModel();
        carrera.setNombre("Gastronomía"); carrera.setFacultad("Turismo"); carrera.setSemestres(4);
        CarreraModel guardado = repository.save(carrera);
        Long id = guardado.getId();

        // When
        repository.deleteById(id);

        // Then
        assertFalse(repository.findById(id).isPresent());
    }
}