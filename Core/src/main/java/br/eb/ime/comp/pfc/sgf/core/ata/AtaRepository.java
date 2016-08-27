package br.eb.ime.comp.pfc.sgf.core.ata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Ata;
import br.eb.ime.comp.pfc.sgf.models.Professor;


@Component
public class AtaRepository {
	
	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private MongoOperations mongo;
	
	public Ata create(Ata ata){
		mongo.save(ata);
		return ata;
	}
	
	public List<Ata> getAll(){
		return mongo.findAll(Ata.class);
	}
	
	public Ata getById(String id){
		Query searchAtaQuery = new Query(Criteria.where("id").is(id));
		return mongo.findOne(searchAtaQuery, Ata.class);
	}
	
	public List<Ata> getByTurma(String idTurma){
		Query searchAtaQuery = new Query(Criteria.where("idTurma").is(idTurma));
		return mongo.find(searchAtaQuery, Ata.class);
	}
	
	public List<Ata> getByProfessor(String idProfessor){
		Query searchAtaQuery = new Query(Criteria.where("idProfessor").is(idProfessor));
		return mongo.find(searchAtaQuery, Ata.class);
	}
	
	public List<Ata> getByXerife(Aluno xerife){
		Query searchAtaQuery = new Query(Criteria.where("xerife").is(xerife));
		return mongo.find(searchAtaQuery, Ata.class);	
	}
	
	public List<Ata> getByCoordenador(Professor coordenador){
		Query searchAtaQuery = new Query(Criteria.where("coordenador").is(coordenador));
		return mongo.find(searchAtaQuery, Ata.class);		
	}
	
	public Ata save(Ata ata){
		Query searchAtaQuery = new Query(Criteria.where("id").is(ata.getId()));
		
		Update update = new Update();
		update.set("tempos", ata.getTempos());
		update.set("xerife", ata.getXerife());
		update.set("coordenador", ata.getCoordenador());
		
		mongo.upsert(searchAtaQuery, update, Ata.class);
		
		return this.getById(ata.getId());
	}
}
