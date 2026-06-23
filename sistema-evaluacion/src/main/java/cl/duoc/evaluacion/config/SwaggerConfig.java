package cl.duoc.evaluacion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Evaluaciones Service")
                        .version("1.0")
                        .description("Microservicio de gestión de evaluaciones y notas de alumnos. "
                                + "Valida si el estudiante cuenta con inscripciones vigentes mediante Feign Client antes de registrar registros."));
    }
}