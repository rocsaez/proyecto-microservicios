package cl.duoc.sistema_asistencia.config;

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
                        .title("API Asistencia Service")
                        .version("1.0")
                        .description("Microservicio de gestión de asistencias. "
                                + "Permite registrar, consultar, actualizar y eliminar asistencias de clases."));
    }
}