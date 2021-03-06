package br.eb.ime.comp.pfc.sgf.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

import br.eb.ime.comp.pfc.sgf.core.aluno.AlunoServer;
import br.eb.ime.comp.pfc.sgf.core.disciplina.DisciplinaServer;
import br.eb.ime.comp.pfc.sgf.core.professor.ProfessorServer;
import br.eb.ime.comp.pfc.sgf.core.turma.TurmaServer;
import br.eb.ime.comp.pfc.sgf.models.Ata;

/**
 * Classe para a simulação de serviços separados que estão no mesmo jar
 * 
 * @author lucasmendes
 *
 */
public class CoreStarter {

	public static void main(String args[]) {

		String serverName;

		try {
			switch (args.length) {
			
			case 2:
				System.setProperty("server.port", args[1]);
				
			case 1:
				serverName = args[0].toLowerCase();
				break;

			default:
				usage();
				return;
			}

			System.setProperty("spring.config.name", serverName + "-server");
			
			if(args.length == 2){
				try{
					Integer.parseInt(args[1]);
					System.setProperty("server.port", args[1]);
				}catch (Exception e) {
					System.out.println("Porta inválida. Usando a default " + System.getProperty("server.port"));
				}	
			}
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
		allServers.put("disciplina", DisciplinaServer.class);
		allServers.put("professor", ProfessorServer.class);
		allServers.put("turma", TurmaServer.class);
		allServers.put("ata", Ata.class);
		return allServers;
	}

	/**
	 * Mensagem de como usar
	 */
	protected static void usage() {
		System.out.println("Uso: java -jar ... <nome-do-servico> [porta-do-serviço]");
		System.out.println("     onde nome-do-servico é 'aluno', " + "'disciplina', 'professor', 'turma' ou 'ata'");
		System.out.println("     e porta-do-serviço > 1024");
	}
}
