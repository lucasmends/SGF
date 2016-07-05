/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.core.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucasmendes
 *
 */
@RestController
@RequestMapping("/")
public class ProfessorController {
	
	@Autowired
	private MongoOperations mongo;


	@RequestMapping(method = RequestMethod.GET)
	public List<Professor> getAll(){
		return mongo.findAll(Professor.class);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Professor create(@RequestBody Professor professor){
		mongo.save(professor);
		return professor;
	}
	
	/**
	 * TODO
	 * 
	 * @param email
	 * @param professor
	 * @return
	 */
	@RequestMapping(value = "/{email}", method = RequestMethod.PUT)
	public Professor update(@PathVariable("email") String email, @RequestBody Professor professor){
		Query searchUserQuery = new Query(Criteria.where("email").is(email));
		
		Professor old = mongo.findOne(searchUserQuery, Professor.class);
		
		//Se não existe na tabela, cria
		if(old.equals(null)){
			mongo.save(professor);
			return professor;
		}
		//Se o email indicado e o fornecido são diferentes
		if(!old.getEmail().equals(professor.getEmail()))
			return null;
		
		//Atualiza o nome caso haja mudança
		if(!old.getNome().equals(professor.getNome()))
			mongo.updateFirst(searchUserQuery, Update.update("nome", professor.getNome()), Professor.class);
		
		/**
		 * Senha tem o caso do hash
		 * 
		if(!old.getPassword().equals(professor.getPassword()))
			mongo.updateFirst(searchUserQuery, Update.update("password", professor.getPassword()), Professor.class);
		**/
		return professor;
	}
	
	/**
	 * 
	 * Adiciona uma engenharia para o professor
	 * 
	 * @param email o campo unico que indentifica o professor
	 * @param engenharia a engenharia a ser adicionada
	 * @return
	 */
	@RequestMapping(value = "/{email}/engenharia/{engenharia}", method = RequestMethod.PUT)
	public Professor addEngenharia(@PathVariable("email") String email, @PathVariable("engenharia") String engenharia){
		Query searchUserQuery = new Query(Criteria.where("email").is(email));
		
		Professor professor = mongo.findOne(searchUserQuery, Professor.class);
		
		if(professor.equals(null))
			return null;
		
		if(professor.getEngenharias().add(engenharia))
			mongo.updateFirst(searchUserQuery, Update.update("engenharias", professor.getEngenharias()), Professor.class);
		
		return professor;
	}
	
}
