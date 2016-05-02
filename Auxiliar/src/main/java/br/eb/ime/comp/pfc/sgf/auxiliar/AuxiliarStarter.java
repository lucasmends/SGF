package br.eb.ime.comp.pfc.sgf.auxiliar;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.eb.ime.comp.pfc.sgf.auxiliar.RegistrationServer;

@SpringBootApplication
public class AuxiliarStarter {

	public static void main(String args[]) {

		Integer port = null;
		String serverName;
		
		try {
			switch (args.length) {
			
			case 2:
				port = new Integer(args[1]);
			case 1:
				serverName = args[0].toLowerCase();
				break;
				
			default:
				usage();
				return;
			}
			
			System.setProperty("spring.config.name", serverName + "-server");
			
			if(port != null)
				System.setProperty("server.port", port.toString());
			
			SpringApplication.run(servers().get(serverName), serverName + "server");
			
		//Caso não seja passado um número como porta
		} catch (NumberFormatException e) {
			usage();
			return;
			
		}
		//Caso outra execeção ocorra
		catch(Exception e){
			usage();
			return;
		}



	}

	/**
	 * Contém todas as classes de configurações dos servidores auxiliares
	 * 
	 * @return Um map sendo a key o nome do servidor e o value a classe de
	 *         configuração
	 */
	public static Map<String, Class<?>> servers() {
		Map<String, Class<?>> allServers = new HashMap<>();
		allServers.put("registration", RegistrationServer.class);
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
