package br.eb.ime.comp.pfc.sgf.core.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class AlunoRepository {
	
	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Srping
	 */
	@Autowired
	private MongoOperations mongo;
	
	
	public Aluno create(Aluno aluno){
		mongo.save(aluno);
		return aluno;
	}
	
	public List<Aluno> getAll(){
		return mongo.findAll(Aluno.class);	
	}
	
	public Aluno getByNumero(String numero){
		Query searchUserQuery = new Query(Criteria.where("numero").is(numero));
		return mongo.findOne(searchUserQuery, Aluno.class);
	}

	public Aluno getByEmail(String email){
		Query searchUserQuery = new Query(Criteria.where("email").is(email));
		return mongo.findOne(searchUserQuery, Aluno.class);
	}
	
	public Aluno save(Aluno aluno){
		Query searchUserQuery = new Query(Criteria.where("numero").is(aluno.getNumero()));
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
}
