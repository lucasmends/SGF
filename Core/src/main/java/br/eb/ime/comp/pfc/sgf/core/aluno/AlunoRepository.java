package br.eb.ime.comp.pfc.sgf.core.aluno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import br.eb.ime.comp.pfc.sgf.models.Aluno;

@Component
public class AlunoRepository {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente
	 * pelo Spring
	 */
	@Autowired
	private MongoOperations mongo;

	public Aluno create(Aluno aluno) {
		mongo.save(aluno);
		return aluno;
	}

	public List<Aluno> getAll() {
		return mongo.findAll(Aluno.class);
	}

	public Aluno getByNumero(String numero) {
		Query searchAlunoQuery = new Query(Criteria.where("numero").is(numero));
		return mongo.findOne(searchAlunoQuery, Aluno.class);
	}

	public Aluno getByEmail(String email) {
		Query searchAlunoQuery = new Query(Criteria.where("email").is(email));
		return mongo.findOne(searchAlunoQuery, Aluno.class);
	}

	public Aluno save(Aluno aluno) {
		Query searchAlunoQuery = new Query(Criteria.where("numero").is(aluno.getNumero()));

		Update update = new Update();
		update.set("nome", aluno.getNome());
		/*
		 * Definir de quem vai ser a responsabilidade de checar o hash
		 * update.set("password", aluno.getPassword());
		 */
		//faz update, se não encontrar  o aluno, cria
		mongo.upsert(searchAlunoQuery, update, Aluno.class);
		return this.getByNumero(aluno.getNumero());
	}
}
