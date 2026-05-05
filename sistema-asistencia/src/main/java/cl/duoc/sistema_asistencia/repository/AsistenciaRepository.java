package cl.duoc.sistema_asistencia.repository;

import cl.duoc.sistema_asistencia.model.AsistenciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaRepository extends JpaRepository<AsistenciaModel, Long> {
}