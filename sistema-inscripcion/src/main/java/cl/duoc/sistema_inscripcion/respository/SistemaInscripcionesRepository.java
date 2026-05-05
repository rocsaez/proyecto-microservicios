package cl.duoc.sistema_inscripcion.respository;

import cl.duoc.sistema_inscripcion.model.SistemaInscripcionesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaInscripcionesRepository extends JpaRepository<SistemaInscripcionesModel, Long> {
}