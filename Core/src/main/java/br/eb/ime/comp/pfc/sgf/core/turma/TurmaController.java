package br.eb.ime.comp.pfc.sgf.core.turma;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Turma;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Materia
 * São utilizados os conceitos de verbos do HTTP, sendo que requisições POST tem significado de criação
 * GET de requisição de recurso, PUT atualição e DELETE de exclusão
 * 
 * @author igorcesar
 *
 */
@RestController
@RequestMapping("/")
public class TurmaController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private TurmaRepository repo;
	
	/**
	 * Criação de uma turma
	 * 
	 * @param turmaJSON A turma formatada em JSON no corpo da requisição
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Turma create(@RequestBody String turmaJSON) throws JsonParseException, JsonMappingException, IOException{
		Turma turma = Utils.getTurma(turmaJSON);
		
		return repo.create(turma);
	}
	
	/**
	 * 
	 * @param turmaJSON
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Turma update(@RequestBody String turmaJSON) throws JsonParseException, JsonMappingException, IOException{
		Turma turma = Utils.getTurma(turmaJSON);
		
		return repo.save(turma);
	}


	@RequestMapping(value = "/{id}/materia", method = RequestMethod.PUT)
	public Turma addMateria(@RequestBody Materia materia, @PathVariable("id") String id){

		/**
		 * Deviamos passar apenas o id da materia a ser adicionada, buscá-la e adicioná-la
		 */
		Turma turma = repo.getById(id);
		
		//se não encontra, retorna null
		if(turma.equals(null)){
			return null;
		}
		
		turma.addMateria(materia);
		
		return repo.save(turma);
	}
	
	@RequestMapping(value = "/{id}/aluno", method = RequestMethod.PUT)
	public Turma addAluno(@RequestBody Aluno aluno, @PathVariable("id") String id){

		/**
		 * Deviamos passar apenas o id do aluno a ser adicionado, buscá-lo e adicioná-lo
		 */
		Turma turma = repo.getById(id);
		
		//se não encontra, retorna null
		if(turma.equals(null)){
			return null;	
		}
		
		turma.addAluno(aluno);
		
		return repo.save(turma);
	}		
	/**
	 * TODO Javadoc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Turma> getAll(){
		return repo.getAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Turma getById(@PathVariable("id") String id){
		return repo.getById(id);
	}
	
}
