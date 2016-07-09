package br.eb.ime.comp.pfc.sgf.core.materia;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Professor;

public class Utils {

	private static int PROFESSOR_IND_START = 0;
	private static int PROFESSOR_IND_END = 1;
	
	/**
	 * Cria o Objeto do tipo Materia apartir da notação em JSON
	 * 
	 * @param materiaJSON objeto descrito no formato JSON
	 * @return o Objeto Materia devidamente instanciado
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Materia getMateria(String materiaJSON) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Professor professor = null;
		
		int idStringProfessor = materiaJSON.indexOf("\"professor\":");
		if(idStringProfessor > 0){
			professor = getProfessor(materiaJSON);
			int idLast = getProfessirInd(materiaJSON)[PROFESSOR_IND_END];
			idStringProfessor = materiaJSON.substring(0, idStringProfessor).lastIndexOf(',');
			materiaJSON = materiaJSON.replace(materiaJSON.substring(idStringProfessor, idLast), "");
		}
		
		Materia materia =  mapper.readValue(materiaJSON, Materia.class);
		materia.setProfessor(professor);
		
		return materia;
	}
	
	private static Professor getProfessor(String materiaJSON) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		int[] professorInd = getProfessirInd(materiaJSON);
		
		if(professorInd != null)
			return mapper.readValue(materiaJSON.substring(professorInd[PROFESSOR_IND_START], professorInd[PROFESSOR_IND_END]), Professor.class);
		
		return null;
	}

	private static int[] getProfessirInd(String materiaJSON) {
		int idStringProfessor = materiaJSON.indexOf("\"professor\":");
		if (idStringProfessor > 0) {
			int idFirst = materiaJSON.substring(idStringProfessor).indexOf("{") + idStringProfessor;
			int idLast = idFirst;
			int count = 1;
			for (int i = idFirst + 1; i < materiaJSON.length(); i++) {
				if (materiaJSON.charAt(i) == '{')
					count++;
				else if (materiaJSON.charAt(i) == '}') {
					count--;
					if (count == 0) {
						idLast = i + 1;
						break;
					}
				}
			}
			int[] result = {idFirst, idLast};
			return result;
		}
		return null;
	}
}
