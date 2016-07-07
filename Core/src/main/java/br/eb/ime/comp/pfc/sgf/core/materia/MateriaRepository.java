package br.eb.ime.comp.pfc.sgf.core.materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import br.eb.ime.comp.pfc.sgf.models.Materia;

@Component
public class MateriaRepository {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente
	 * pelo Spring
	 */
	@Autowired
	private MongoOperations mongo;

	public Materia create(Materia materia) {
		mongo.save(materia);
		return materia;
	}

	public List<Materia> getAll() {
		return mongo.findAll(Materia.class);
	}

	public Materia getByName(String name) {
		Query searchMateriaQuery = new Query(Criteria.where("nome").is(nome));
		return mongo.findOne(searchMateriaQuery, Materia.class);
	}

	public Materia getById(String id) {
		Query searchMateriaQuery = new Query(Criteria.where("id").is(id));
		return mongo.findOne(searchMateriaQuery, Materia.class);
	}

	public Materia save(Materia materia) {
		Query searchMateriaQuery = new Query(Criteria.where("id").is(materia.getId()));

		Update update = new Update();
		update.set("nome", materia.getNome());
		// ver como faz pra atualizar lista de professores
		// update.set("professor", materia.getProfessor());

		//faz update, se não encontrar  a materia, cria
		mongo.upsert(searchMateriaQuery, update, Materia.class);
		return this.getByNome(materia.getNome());
	}
}
