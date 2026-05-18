package cl.duoc.asignaturas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AsignaturasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsignaturasApplication.class, args);
	}

}
