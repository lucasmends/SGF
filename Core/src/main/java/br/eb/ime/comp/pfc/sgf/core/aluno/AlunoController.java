package br.eb.ime.comp.pfc.sgf.core.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Srping
	 */
	@Autowired
	private MongoOperations mongo;
	
	/**
	 * Criação de um Aluno
	 * 
	 * @param aluno O aluno formatado em JSON no corpo da requisição
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Aluno create(@RequestBody Aluno aluno){
		mongo.save(aluno);
		return aluno;
	}
	
	/**
	 * 
	 * @param aluno
	 * @return
	 */
	@RequestMapping(value = "/{numero}", method = RequestMethod.PUT)
	public Aluno update(@RequestBody Aluno aluno, @PathVariable("numero") String numero){
		Query searchUserQuery = new Query(Criteria.where("numero").is(numero));
		//Verificar o que está sendo mudado
		Aluno old = mongo.findOne(searchUserQuery, Aluno.class);
		
		//se não encontra, cria
		if(old.equals(null)){
			mongo.save(aluno);
			return aluno;		
		}
		//se o número informado não corresponder ao Aluno informado
		if(!old.getNumero().equals(aluno.getNumero()))
			return null;
		
		//Atualizar o email caso haja mudança
		if(!old.getEmail().equals(aluno.getEmail()))
			mongo.updateFirst(searchUserQuery, Update.update("email", aluno.getEmail()), Aluno.class);
		/**
		 * Senha tem o caso do hash
		//Atualizar a senha caso haja mudança
		if(!old.getPassword().equals(aluno.getPassword()))
			mongo.updateFirst(searchUserQuery, Update.update("password", aluno.getPassword()), Aluno.class);
		**/
		
		
		return aluno;
	}
	
	/**
	 * TODO Javadoc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Aluno> getAll(){
		return mongo.findAll(Aluno.class);		
	}
	
	@RequestMapping(value = "/{numero}", method = RequestMethod.GET)
	public Aluno getByNumero(@PathVariable("numero") String numero){
		Query searchUserQuery = new Query(Criteria.where("numero").is(numero));
		return mongo.findOne(searchUserQuery, Aluno.class);
	}
	
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public Aluno getByEmail(@PathVariable("email") String email){
		Query searchUserQuery = new Query(Criteria.where("email").is(email));
		return mongo.findOne(searchUserQuery, Aluno.class);
	}
	
}
