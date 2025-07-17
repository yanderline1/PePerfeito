package br.com.peperfeito.PePerfeito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class PePerfeitoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PePerfeitoApplication.class, args);
	}

}
