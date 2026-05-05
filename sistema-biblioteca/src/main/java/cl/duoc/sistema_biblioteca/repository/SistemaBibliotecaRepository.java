package cl.duoc.sistema_biblioteca.repository;

import cl.duoc.sistema_biblioteca.model.SistemaBibliotecaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaBibliotecaRepository extends JpaRepository<SistemaBibliotecaModel, Long> {
}