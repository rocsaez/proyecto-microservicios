package cl.duoc.gestion_sala;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=none"
})
class GestionSalaApplicationTests {

    @Test
    void contextLoads() {
        // Al proveer un dialecto e URL válidos de H2, Hibernate inicializa correctamente el contexto
    }
}