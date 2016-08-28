package br.eb.ime.comp.pfc.sgf.core.disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import br.eb.ime.comp.pfc.sgf.models.Disciplina;

@Component
public class DisciplinaRepository {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente
	 * pelo Spring
	 */
	@Autowired
	private MongoOperations mongo;

	public Disciplina create(Disciplina disciplina) {
		mongo.save(disciplina);
		return disciplina;
	}

	public List<Disciplina> getAll() {
		return mongo.findAll(Disciplina.class);
	}

	public Disciplina getByNome(String nome) {
		Query searchDisciplinaQuery = new Query(Criteria.where("nome").is(nome));
		return mongo.findOne(searchDisciplinaQuery, Disciplina.class);
	}

	public Disciplina getById(String id) {
		Query searchDisciplinaQuery = new Query(Criteria.where("id").is(id));
		return mongo.findOne(searchDisciplinaQuery, Disciplina.class);
	}

	public Disciplina save(Disciplina disciplina) {
		Query searchDisciplinaQuery = new Query(Criteria.where("id").is(disciplina.getId()));

		Update update = new Update();
		update.set("nome", disciplina.getNome());
		// ver como faz pra atualizar lista de professores
		update.set("professor", disciplina.getProfessor());

		//faz update, se não encontrar  a disciplina, cria
		mongo.upsert(searchDisciplinaQuery, update, Disciplina.class);
		return this.getByNome(disciplina.getNome());
	}
}
