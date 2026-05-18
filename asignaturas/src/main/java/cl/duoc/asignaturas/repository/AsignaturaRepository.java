package cl.duoc.asignaturas.repository;

import cl.duoc.asignaturas.model.AsignaturaModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaRepository extends JpaRepository<AsignaturaModel, Long> {
    Optional<AsignaturaModel> findBySigla(String sigla);
}