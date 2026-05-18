package cl.duoc.sistema_asistencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SistemaAsistenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaAsistenciaApplication.class, args);
	}

}
