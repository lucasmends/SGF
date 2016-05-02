package br.eb.ime.comp.pfc.sgf.auxiliar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * @author lucasmendes
 * 
 * Classe de configuração do servidor Eureka
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {
	
	public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		System.setProperty("spring.config.name", "registration-server");

		SpringApplication.run(RegistrationServer.class, args);
	}
}
