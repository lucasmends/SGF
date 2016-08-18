package br.eb.ime.comp.pfc.sgf.core.professor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import br.eb.ime.comp.pfc.sgf.core.MongoConfig;

/**
 * Classe de configuração do serviço Professor
 * 
 * @author lucasmendes
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { JacksonAutoConfiguration.class })
@EnableDiscoveryClient
@Import({MongoConfig.class})
public class ProfessorServer {
	
	/**
	 *  Caso queira iniciar somente esse serviço através da IDE
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "professor-server");
		SpringApplication.run(ProfessorServer.class, args);
	}
}
