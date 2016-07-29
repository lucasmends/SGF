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

	/**
	 * Salva no banco de dados o aluno
	 * 
	 * @param aluno o aluno a ser salvo
	 * @return o aluno salvo
	 */
	public Aluno create(Aluno aluno) {
		mongo.save(aluno);
		return aluno;
	}

	/**
	 * Retorna todos os alunos da base de dados
	 * 
	 * @return a lista com todos os alunos
	 */
	public List<Aluno> getAll() {
		return mongo.findAll(Aluno.class);
	}

	
	/**
	 * Procura no banco de dados um aluno pelo seu número
	 * 
	 * @param numero  o número do aluno
	 * @return o aluno caso exista, null caso não
	 */
	public Aluno getByNumero(String numero) {
		Query searchAlunoQuery = new Query(Criteria.where("numero").is(numero));
		return mongo.findOne(searchAlunoQuery, Aluno.class);
	}

	/**
	 * Procura um aluno pelo seu email
	 * 
	 * @param email o email do aluno
	 * @return o aluno caso exista, null caso não
	 */
	public Aluno getByEmail(String email) {
		Query searchAlunoQuery = new Query(Criteria.where("email").is(email));
		return mongo.findOne(searchAlunoQuery, Aluno.class);
	}

	/**
	 * Atualiza um aluno, cria se a aluno passado não existir
	 * 
	 * @param aluno o aluno com as informações atualizadas
	 * @return o aluno atualizadoß
	 */
	public Aluno save(Aluno aluno) {
		Query searchAlunoQuery = new Query(Criteria.where("numero").is(aluno.getNumero()));

		Update update = new Update();
		update.set("nome", aluno.getNome());
		update.set("email", aluno.getEmail());
		/*
		 * Definir de quem vai ser a responsabilidade de checar o hash
		 * update.set("password", aluno.getPassword());
		 */
		//faz update, se não encontrar  o aluno, cria
		mongo.upsert(searchAlunoQuery, update, Aluno.class);
		return this.getByNumero(aluno.getNumero());
	}
}
