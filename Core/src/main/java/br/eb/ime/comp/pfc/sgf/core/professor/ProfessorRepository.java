package br.eb.ime.comp.pfc.sgf.core.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import br.eb.ime.comp.pfc.sgf.models.Professor;

@Component
public class ProfessorRepository {
	
	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private MongoOperations mongo;
	
	public Professor create(Professor professor){
		if(!professor.getCoordenador().equals("true")){
			professor.setCoordenador("false");
		}
		mongo.save(professor);
		return professor;
	}
	
	public List<Professor> getAll(){
		return mongo.findAll(Professor.class);
	}
	
	public Professor findByEmail(String email){
		Query searchProfessorQuery = new Query(Criteria.where("email").is(email));
		return mongo.findOne(searchProfessorQuery, Professor.class);
	}
	
	public Professor save(Professor professor){
		Query searchProfessorQuery = new Query(Criteria.where("email").is(professor.getEmail()));
		
		Update update = new Update();
		update.set("nome", professor.getNome());
		update.set("coordenador", !professor.getCoordenador().equals("true")?"true":"false");
		/*
		update.set("password", professor.getPassword())
		*/
		update.set("engenharias", professor.getEngenharias());
		
		mongo.upsert(searchProfessorQuery, update, Professor.class);
		return this.findByEmail(professor.getEmail());
	}
}
