package cl.duoc.gestion_estudiante;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import cl.duoc.gestion_estudiante.repository.GestionEstudianteRepository;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
class GestionEstudianteApplicationTests {

    
    @MockBean
    private GestionEstudianteRepository gestionEstudianteRepository;

    @Test
    void contextLoads() {
        
    }

}