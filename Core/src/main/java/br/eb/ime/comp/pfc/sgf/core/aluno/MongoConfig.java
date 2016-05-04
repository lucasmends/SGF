package br.eb.ime.comp.pfc.sgf.core.aluno;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

/**
 * Classe que contém as configurações da conexão com o banco de dados
 * 
 * @author lucasmendes
 *
 */
@Configurable
public class MongoConfig{

	/**
	 *  Bean que contém a fábrica de conexão
	 *  
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient("127.0.0.1", 27017), "sgf");
	}
	
	/**
	 * Bean que cria o mongoTemplate que é usado para as operações com o MongoDB
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		return new MongoTemplate(mongoDbFactory());
	}  

}
