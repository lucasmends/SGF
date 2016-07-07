package br.eb.ime.comp.pfc.sgf.core.turma;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import br.eb.ime.comp.pfc.sgf.models.Turma;

@Component
public class TurmaRepository {
	
	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private MongoOperations mongo;
	
	public Turma create(Turma turma){
		mongo.save(turma);
		return turma;
	}
	
	public List<Turma> getAll(){
		return mongo.findAll(Turma.class);
	}
	
	public Turma getById(String id){
		Query searchProfessorQuery = new Query(Criteria.where("id").is(id));
		return mongo.findOne(searchProfessorQuery, Turma.class);
	}
	
	public List<Turma> findByEngenharia(String engenharia){
		Query searchProfessorQuery = new Query(Criteria.where("engenharia").is(engenharia));
		return mongo.find(searchProfessorQuery, Turma.class);
	}
	
	public List<Turma> findByAno(String ano){
		Query searchProfessorQuery = new Query(Criteria.where("ano").is(ano));
		return mongo.find(searchProfessorQuery, Turma.class);
	}
	
	public Turma save(Turma turma){
		Query searchProfessorQuery = new Query(Criteria.where("id").is(turma.getId()));
		
		Update update = new Update();
		update.set("ano", turma.getAno());
		update.set("engenharia", turma.getEngenharia());
		update.set("materias", turma.getMaterias());
		update.set("alunos", turma.getAlunos());
		
		mongo.upsert(searchProfessorQuery, update, Turma.class);
		return this.getById(turma.getId());
	}
}
