package cl.duoc.beca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients // cambio despues de las 12 
@SpringBootApplication
public class BecaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BecaApplication.class, args);
	}

}
