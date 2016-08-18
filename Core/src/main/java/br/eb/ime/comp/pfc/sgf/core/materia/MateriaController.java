package br.eb.ime.comp.pfc.sgf.core.materia;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Materia;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Materia
 * São utilizados os conceitos de verbos do HTTP, sendo que requisições POST tem
 * significado de criação GET de requisição de recurso, PUT atualição e DELETE
 * de exclusão
 * 
 * @author igorcesar
 *
 */
@RestController
@RequestMapping("/")
public class MateriaController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente
	 * pelo Spring
	 */
	@Autowired
	private MateriaRepository repo;
	
	/**
	 * Criação de um Materia
	 * 
	 * @param materia A materia formatada em JSON no corpo da requisição
	 * @return a materia criada em JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Materia create(@RequestBody Materia materia){
		/*Gson gson = new GsonBuilder().create();
		
		Materia materia = gson.fromJson(materiaJSON, Materia.class);*/
			
		return repo.create(materia);
	}

	/**
	 * Atualiza uma matéria
	 * 
	 * @param materia a matéria com as informações atualizadas
	 * @return a matéria atualizada em JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Materia update(@RequestBody Materia materia){	
		
		/*Gson gson = new GsonBuilder().create();
		
		Materia materia = gson.fromJson(materiaJSON, Materia.class);*/
		
		return repo.save(materia);
	}

	/**
	 * Retorna todas as matérias do banco de dados
	 * 
	 * @return a lista com todas as matérias em JSON
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Materia> getAll() {
		return repo.getAll();
	}

	/**
	 * Prucura uma matéria pelo seu id
	 * 
	 * @param id o id da matéria
	 * @return a matéria encontrada em JSON, ou null se não foi achado
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Materia getById(@PathVariable("id") String id) {
		return repo.getById(id);
	}

	/**
	 * Procura uma matéria pelo nome
	 * 
	 * @param nome o nome da matéria
	 * @return a matéria encontrada em JSON, ou null se não foi achado
	 */
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public Materia getByNome(@PathVariable("nome") String nome) {
		return repo.getByNome(nome);
	}

}
