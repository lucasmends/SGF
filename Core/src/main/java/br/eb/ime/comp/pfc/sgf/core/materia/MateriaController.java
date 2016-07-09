package br.eb.ime.comp.pfc.sgf.core.materia;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Professor;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Materia
 * São utilizados os conceitos de verbos do HTTP, sendo que requisições POST tem
 * significado de criação GET de requisição de recurso, PUT atualição e DELETE
 * de exclusão
 * 
 * @author igorcesar
 *
 */
@RestController
@RequestMapping("/")
public class MateriaController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente
	 * pelo Spring
	 */
	@Autowired
	private MateriaRepository repo;

	/**
	 * Criação de um Materia
	 * 
	 * @param materia A materia formatada em JSON no corpo da requisição
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Materia create(@RequestBody String materiaRaw) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Materia materia;
		
		int idStringProfessor = materiaRaw.indexOf("\"professor\":");
		if(idStringProfessor > 0){
			int idFirst = materiaRaw.substring(idStringProfessor).indexOf("{") + idStringProfessor;
			int idLast = idFirst;
			int count = 1;
			for(int i = idFirst + 1; i < materiaRaw.length(); i++){
				if(materiaRaw.charAt(i) == '{')
					count++;
				else if(materiaRaw.charAt(i) == '}'){
					count--;
					if(count == 0){
						idLast = i + 1;
						break;
					}
				}
			}
			Professor professor = mapper.readValue(materiaRaw.substring(idFirst, idLast), Professor.class);
			
			idStringProfessor = materiaRaw.substring(0, idStringProfessor).lastIndexOf(',');
			materiaRaw = materiaRaw.replace(materiaRaw.substring(idStringProfessor, idLast), "");
			
			materia = mapper.readValue(materiaRaw, Materia.class);
			materia.setProfessor(professor);
		}else 
			materia = mapper.readValue(materiaRaw, Materia.class);
	
		
		return repo.create(materia);
	}

	/**
	 * 
	 * @param materia
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Materia update(@RequestBody Materia materia) {
		return repo.save(materia);
	}

	/**
	 * 
	 * @param materia
	 * @return
	 */
	@RequestMapping(value = "/{id}/professor", method = RequestMethod.PUT)
	public Materia addProfessor(@RequestBody Professor professor, @PathVariable("id") String id) {

		Materia materia = repo.getById(id);

		// se não encontra, cria
		if (materia.equals(null)) {
			return repo.create(materia);
		}

		materia.setProfessor(professor);

		return repo.save(materia);
	}

	/**
	 * TODO Javadoc
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Materia> getAll() {
		return repo.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Materia getById(@PathVariable("id") String id) {
		return repo.getById(id);
	}

	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public Materia getByNome(@PathVariable("nome") String nome) {
		return repo.getByNome(nome);
	}

}
