package br.eb.ime.comp.pfc.sgf.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import br.eb.ime.comp.pfc.sgf.api.login.LoginServer;

public class APIStarter {

	public static void main(String args[]) {

		String serviceName = "login";

		/*
		 * try { switch (args.length) {
		 * 
		 * case 1: serviceName = args[0].toLowerCase(); break;
		 * 
		 * default: usage(); return; }
		 */

		System.setProperty("spring.config.name", serviceName + "-server");

		if (args.length == 1) {
			try {
				Integer.parseInt(args[1]);
				System.setProperty("server.port", args[0]);
			} catch (Exception e) {
				System.out.println("Porta inválida. Usando a default " + System.getProperty("server.port"));
			}
		}
		
		SpringApplication.run(services().get(serviceName), serviceName + "server");

		// } // Caso uma execeção ocorra
		// catch (Exception e) {
		// usage();
		// return;
		// }

	}

	/**
	 * Contém todas as classes de configurações dos serviços base
	 * 
	 * @return Um map sendo a key o nome do servidor e o value a classe de
	 *         configuração
	 */
	public static Map<String, Class<?>> services() {
		Map<String, Class<?>> allServices = new HashMap<>();
		allServices.put("login", LoginServer.class);
		return allServices;
	}

	/**
	 * Mensagem de como usar
	 */
	protected static void usage() {
		System.out.println("Usage: java -jar ... <server-name> [server-port]");
		System.out.println("     where server-name is 'registration', " + "'accounts' or 'web' and server-port > 1024");
	}
}
