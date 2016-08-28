package br.eb.ime.comp.pfc.sgf.core.disciplina;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Disciplina;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Disciplina
 * São utilizados os conceitos de verbos do HTTP, sendo que requisições POST tem
 * significado de criação GET de requisição de recurso, PUT atualição e DELETE
 * de exclusão
 * 
 * @author igorcesar
 *
 */
@RestController
@RequestMapping("/")
public class DisciplinaController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente
	 * pelo Spring
	 */
	@Autowired
	private DisciplinaRepository repo;
	
	/**
	 * Criação de um Disciplina
	 * 
	 * @param disciplina A disciplina formatada em JSON no corpo da requisição
	 * @return a disciplina criada em JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Disciplina create(@RequestBody Disciplina disciplina){
		/*Gson gson = new GsonBuilder().create();
		
		Disciplina disciplina = gson.fromJson(disciplinaJSON, Disciplina.class);*/
			
		return repo.create(disciplina);
	}

	/**
	 * Atualiza uma matéria
	 * 
	 * @param disciplina a matéria com as informações atualizadas
	 * @return a matéria atualizada em JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Disciplina update(@RequestBody Disciplina disciplina){	
		
		/*Gson gson = new GsonBuilder().create();
		
		Disciplina disciplina = gson.fromJson(disciplinaJSON, Disciplina.class);*/
		
		return repo.save(disciplina);
	}

	/**
	 * Retorna todas as matérias do banco de dados
	 * 
	 * @return a lista com todas as matérias em JSON
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Disciplina> getAll() {
		return repo.getAll();
	}

	/**
	 * Prucura uma matéria pelo seu id
	 * 
	 * @param id o id da matéria
	 * @return a matéria encontrada em JSON, ou null se não foi achado
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Disciplina getById(@PathVariable("id") String id) {
		return repo.getById(id);
	}

	/**
	 * Procura uma matéria pelo nome
	 * 
	 * @param nome o nome da matéria
	 * @return a matéria encontrada em JSON, ou null se não foi achado
	 */
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public Disciplina getByNome(@PathVariable("nome") String nome) {
		return repo.getByNome(nome);
	}

}
