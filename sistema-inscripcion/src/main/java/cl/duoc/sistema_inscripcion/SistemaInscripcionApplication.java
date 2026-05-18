package cl.duoc.sistema_inscripcion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SistemaInscripcionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaInscripcionApplication.class, args);
	}

}
