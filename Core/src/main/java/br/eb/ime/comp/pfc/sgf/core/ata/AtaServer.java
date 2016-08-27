package br.eb.ime.comp.pfc.sgf.core.ata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableDiscoveryClient
@Import({MongoConfig.class})
public class AtaServer {
	
	/**
	 *  Caso queira iniciar somente esse serviço através da IDE
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "ata-server");
		SpringApplication.run(AtaServer.class, args);
	}
}
