package br.eb.ime.comp.pfc.sgf.core.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Aluno;

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
	 * @return o aluno criado
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Aluno create(@RequestBody Aluno aluno){
		return repo.create(aluno);
	}
	
	/**
	 * Atualiza um aluno que foi passado via JSON, se o aluno não existir, cria ele
	 * 
	 * @param aluno o aluno que é enviado em JSON e será desserializado
	 * @return o aluno atualizado
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Aluno update(@RequestBody Aluno aluno){

		Aluno old = repo.getByNumero(aluno.getNumero());
		
		//se não encontra, cria
		if(old.equals(null)){
			return repo.create(aluno);	
		}
		
		return repo.save(aluno);
	}
	
	/**
	 * Retorna todos os alunos da base de dados 
	 *
	 * @return a lista com todos os alunos
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Aluno> getAll(){
		return repo.getAll();		
	}
	
	/**
	 * Procura um aluno pelo seu número
	 * 
	 * @param numero o número do aluno
	 * @return o aluno caso exista ou null caso não
	 */
	@RequestMapping(value = "/{numero}", method = RequestMethod.GET)
	public Aluno getByNumero(@PathVariable("numero") String numero){
		return repo.getByNumero(numero);
	}
	
	/**
	 * Procura um aluno pelo email
	 * 
	 * @param email o email do aluno
	 * @return o aluno caso exista ou null caso não
	 */
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public Aluno getByEmail(@PathVariable("email") String email){
		return repo.getByEmail(email);
	}
	
}
