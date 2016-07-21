package br.eb.ime.comp.pfc.sgf.core.materia;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.eb.ime.comp.pfc.sgf.core.GeneralUtils;
import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Professor;

public class Utils {

	private static int IND_START = 0;
	private static int IND_END = 1;
	
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
			int idLast = GeneralUtils.getInd(materiaJSON, "\"professor\":", '{', '}')[IND_END];
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
		
		int[] professorInd = GeneralUtils.getInd(materiaJSON, "\"professor\":", '{', '}');
		
		if(professorInd != null)
			return mapper.readValue(materiaJSON.substring(professorInd[IND_START], professorInd[IND_END]), Professor.class);
		
		return null;
	}

}
