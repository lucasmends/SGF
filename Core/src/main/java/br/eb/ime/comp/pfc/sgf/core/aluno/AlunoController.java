package br.eb.ime.comp.pfc.sgf.core.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Aluno
 * É utilizado os conceitos de verbos do HTTP, sendo que requisições POST tem significado de criação
 * GET de requisição de recurso, PUT atualição e DELETE de exclusão
 * 
 * @author lucasmendes
 *
 */
@RestController
@RequestMapping("/")
public class AlunoController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private AlunoRepository repo;
	
	/**
	 * Criação de um Aluno
	 * 
	 * @param aluno O aluno formatado em JSON no corpo da requisição
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Aluno create(@RequestBody Aluno aluno){
		return repo.create(aluno);
	}
	
	/**
	 * 
	 * @param aluno
	 * @return
	 */
	@RequestMapping(value = "/{numero}", method = RequestMethod.PUT)
	public Aluno update(@RequestBody Aluno aluno, @PathVariable("numero") String numero){

		Aluno old = repo.getByNumero(numero);
		
		//se não encontra, cria
		if(old.equals(null)){
			return repo.create(aluno);	
		}
		//se o número informado não corresponder ao Aluno informado
		if(!old.getNumero().equals(aluno.getNumero()))
			return null;
		
		return repo.save(aluno);
	}
	
	/**
	 * TODO Javadoc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Aluno> getAll(){
		return repo.getAll();		
	}
	
	@RequestMapping(value = "/{numero}", method = RequestMethod.GET)
	public Aluno getByNumero(@PathVariable("numero") String numero){
		return repo.getByNumero(numero);
	}
	
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public Aluno getByEmail(@PathVariable("email") String email){
		return repo.getByEmail(email);
	}
	
}
