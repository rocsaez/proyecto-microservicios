package cl.duoc.sistema_inscripcion.repository;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaInscripcionesRepository extends JpaRepository<SistemaInscripcionesModel, Long> 
{
    // Método para validar si ya existe una inscripción con el mismo RUT, asignatura y periodo
    boolean existsByRutEstudianteAndNombreAsignaturaAndPeriodo(String rut, String asignatura, String periodo);
}