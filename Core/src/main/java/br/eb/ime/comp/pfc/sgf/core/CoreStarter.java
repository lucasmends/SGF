package br.eb.ime.comp.pfc.sgf.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import br.eb.ime.comp.pfc.sgf.core.aluno.AlunoServer;

public class CoreStarter {

	public static void main(String args[]) {

		String serverName;

		try {
			switch (args.length) {

			case 1:
				serverName = args[0].toLowerCase();
				break;

			default:
				usage();
				return;
			}

			System.setProperty("spring.config.name", serverName + "-server");

			SpringApplication.run(services().get(serverName), serverName + "server");

		} // Caso outra execeção ocorra
		catch (Exception e) {
			usage();
			return;
		}

	}

	/**
	 * Contém todas as classes de configurações dos serviços base
	 * 
	 * @return Um map sendo a key o nome do servidor e o value a classe de
	 *         configuração
	 */
	public static Map<String, Class<?>> services() {
		Map<String, Class<?>> allServers = new HashMap<>();
		allServers.put("aluno", AlunoServer.class);
		return allServers;
	}

	/**
	 * Mensagem de como usar
	 */
	protected static void usage() {
		System.out.println("Usage: java -jar ... <server-name> [server-port]");
		System.out.println("     where server-name is 'registration', " + "'accounts' or 'web' and server-port > 1024");
	}
}
