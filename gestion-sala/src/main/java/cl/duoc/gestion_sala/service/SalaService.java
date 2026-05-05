package cl.duoc.gestion_sala.service;

import cl.duoc.gestion_sala.model.SalaModel;
import cl.duoc.gestion_sala.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public List<SalaModel> obtenerTodas() {
        return salaRepository.findAll();
    }

    public SalaModel guardarSala(SalaModel sala) {
        return salaRepository.save(sala);
    }

    public void eliminarSala(Long id) {
        salaRepository.deleteById(id);
    }
}