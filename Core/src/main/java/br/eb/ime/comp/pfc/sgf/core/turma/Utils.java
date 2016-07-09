package br.eb.ime.comp.pfc.sgf.core.turma;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Turma;

public class Utils {
	private static int IND_START = 0;
	private static int IND_END = 1;

	public static Turma getTurma(String turmaJSON) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Turma turma = null;
		Set<Materia> materias = null;
		Set<Aluno> alunos = null;
		
		int id = turmaJSON.indexOf("\"materias\":");
		int idLast;
		if(id > 0){
			materias = getSet(turmaJSON, Materia.class);
			idLast = getInd(turmaJSON, "\"materias\":")[IND_END];
			id = turmaJSON.substring(0, id).lastIndexOf(',');
			turmaJSON = turmaJSON.replace(turmaJSON.substring(id, idLast), "");
		}
		
		id = turmaJSON.indexOf("\"alunos\":");
		if(id > 0){
			alunos = getSet(turmaJSON, Aluno.class);
			idLast = getInd(turmaJSON, "\"alunos\":")[IND_END];
			id = turmaJSON.substring(0, id).lastIndexOf(',');
			turmaJSON = turmaJSON.replace(turmaJSON.substring(id, idLast), "");
		}
		turma = mapper.readValue(turmaJSON, Turma.class);
		turma.setAlunos(alunos);
		turma.setMaterias(materias);
		
		return turma;
	}

	private static <T> Set<T> getSet(String turmaJSON, Class<T> cls) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		int[] Ind;
		Set<T> set = null;
		
		if(cls == Materia.class)
			Ind = getInd(turmaJSON, "\"materias\":");
		else if (cls == Aluno.class)
			Ind = getInd(turmaJSON, "\"alunos\":");
		else return set;


		if (Ind != null) {
			String setJSON = turmaJSON.substring(Ind[IND_START], Ind[IND_END]);
			int count;
			int indFirst = setJSON.indexOf('[');
			int indLast = indFirst;
			
			if(indFirst > 0)
				set = new HashSet<>();
			
			while (indFirst > 0) {
				count = 1;
				for (int i = indFirst + 1; i < setJSON.length(); i++) {
					if (setJSON.charAt(i) == '[')
						count++;
					else if (setJSON.charAt(i) == ']') {
						count--;
						if (count == 0) {
							indLast = i + 1;
							break;
						}
					}
				}
				set.add(mapper.readValue(setJSON.substring(indFirst, indLast), cls));
				
				setJSON = setJSON.substring(indLast);
				indFirst = setJSON.indexOf('[');
			}
			return set;
		}

		return set;
	}	
	
	private static int[] getInd(String turmaJSON, String name) {
		int idStringMaterias = turmaJSON.indexOf(name);
		if (idStringMaterias > 0) {
			int idFirst = turmaJSON.substring(idStringMaterias).indexOf("{") + idStringMaterias;
			int idLast = idFirst;
			int count = 1;
			for (int i = idFirst + 1; i < turmaJSON.length(); i++) {
				if (turmaJSON.charAt(i) == '{')
					count++;
				else if (turmaJSON.charAt(i) == '}') {
					count--;
					if (count == 0) {
						idLast = i + 1;
						break;
					}
				}
			}
			int[] result = { idFirst, idLast };
			return result;
		}
		return null;
	}
	
}
