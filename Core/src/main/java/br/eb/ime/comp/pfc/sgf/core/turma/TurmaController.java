package br.eb.ime.comp.pfc.sgf.core.turma;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Disciplina;
import br.eb.ime.comp.pfc.sgf.models.Turma;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Disciplina
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
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Turma create(@RequestBody Turma turma){
		/*Gson gson = new GsonBuilder().create();
		
		Turma turma = gson.fromJson(turmaJSON, Turma.class);*/
		
		return repo.create(turma);
	}
	
	/**
	 * 
	 * @param turmaJSON
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Turma update(@RequestBody Turma turma){
		/*Gson gson = new GsonBuilder().create();
		
		Turma turma = gson.fromJson(turmaJSON, Turma.class);*/
		
		return repo.save(turma);
	}


	@RequestMapping(value = "/{id}/disciplina", method = RequestMethod.PUT)
	public Turma addDisciplina(@RequestBody Disciplina disciplina, @PathVariable("id") String id){

		/**
		 * Deviamos passar apenas o id da disciplina a ser adicionada, buscá-la e adicioná-la
		 */
		Turma turma = repo.getById(id);
		
		//se não encontra, retorna null
		if(turma.equals(null)){
			return null;
		}
		
		/*Gson gson = new GsonBuilder().create();
		
		Disciplina disciplina = gson.fromJson(disciplinaJSON, Disciplina.class);*/
		
		turma.addDisciplina(disciplina);
		
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
