package br.eb.ime.comp.pfc.sgf.core.turma;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.eb.ime.comp.pfc.sgf.core.GeneralUtils;
import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Turma;

public class Utils {
	private static int IND_START = 0;
	private static int IND_END = 1;

	/**
	 * Desserializar um JSON do tipo Turma
	 * 
	 * @param turmaJSON o json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Turma getTurma(String turmaJSON) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Turma turma = null;
		//Os conjuntos começam nulos
		Set<Materia> materias = null;
		Set<Aluno> alunos = null;
		//verifica se existe o atributo matéria
		int id = turmaJSON.indexOf("\"materias\":");
		int idLast;
		if (id > 0) {
			//se sim, chama o método que cria o conjunto de matériasß
			materias = getSet(turmaJSON, Materia.class);
			idLast = GeneralUtils.getInd(turmaJSON, "\"materias\":", '[', ']')[IND_END];
			id = turmaJSON.substring(0, id).lastIndexOf(',');
			//retira o atributo matéria da string
			turmaJSON = turmaJSON.replace(turmaJSON.substring(id, idLast), "");
		}
		//análogo ao anterior
		id = turmaJSON.indexOf("\"alunos\":");
		if (id > 0) {
			alunos = getSet(turmaJSON, Aluno.class);
			idLast = GeneralUtils.getInd(turmaJSON, "\"alunos\":", '[', ']')[IND_END];
			id = turmaJSON.substring(0, id).lastIndexOf(',');
			turmaJSON = turmaJSON.replace(turmaJSON.substring(id, idLast), "");
		}
		turma = mapper.readValue(turmaJSON, Turma.class);
		turma.setAlunos(alunos);
		turma.setMaterias(materias);

		return turma;
	}

	/**
	 * Cria o conjunto da classe passada
	 * 
	 * @param turmaJSON a string que contém o conjunto
	 * @param cls a classe dos elementos do conjunto
	 * @return o conjunto
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static <T> Set<T> getSet(String turmaJSON, Class<T> cls)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		int[] Ind;
		Set<T> set = null;
		boolean flagMateria = false;
		//verifica a classe informada
		if (cls == Materia.class) {
			Ind = GeneralUtils.getInd(turmaJSON, "\"materias\":", '[', ']');
			flagMateria = true;//porque a classe do tipo Matéria é composto
		} else if (cls == Aluno.class)
			Ind = GeneralUtils.getInd(turmaJSON, "\"alunos\":", '[', ']');
		else
			return set;

		if (Ind != null) {
			String setJSON = turmaJSON.substring(Ind[IND_START], Ind[IND_END]);
			set = new HashSet<>();
			int count = 1;
			int indFirst = setJSON.indexOf('{');
			int indLast = indFirst;

			while (indFirst >= 0) {
				count = 1;
				for (int i = indFirst + 1; i < setJSON.length(); i++) {
					if (setJSON.charAt(i) == '{')
						count++;
					else if (setJSON.charAt(i) == '}') {
						count--;
						if (count == 0) {
							indLast = i + 1;
							break;
						}
					}
				}
				System.out.println(setJSON.substring(indFirst, indLast));
				if (flagMateria)
					set.add((T) br.eb.ime.comp.pfc.sgf.core.materia.Utils.getMateria(setJSON.substring(indFirst, indLast)));
				else
					set.add(mapper.readValue(setJSON.substring(indFirst, indLast), cls));

				setJSON = setJSON.substring(indLast);
				indFirst = setJSON.indexOf('{');
			}
			return set;
		}

		return set;
	}

}
