package br.eb.ime.comp.pfc.sgf.core.materia;

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
 * São utilizados os conceitos de verbos do HTTP, sendo que requisições POST tem significado de criação
 * GET de requisição de recurso, PUT atualição e DELETE de exclusão
 * 
 * @author igorcesar
 *
 */
@RestController
@RequestMapping("/")
public class MateriaController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private MateriaRepository repo;
	
	/**
	 * Criação de um Materia
	 * 
	 * @param materia A materia formatada em JSON no corpo da requisição
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Materia create(@RequestBody Materia materia){
		return repo.create(materia);
	}
	
	/**
	 * 
	 * @param materia
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Materia update(@RequestBody Materia materia, @PathVariable("id") String id){

		Materia old = repo.getById(id);
		
		//se não encontra, cria
		if(old.equals(null)){
			return repo.create(materia);	
		}
		//se o id informado não corresponder a Materia informada
		if(!old.getId().equals(materia.getId()))
			return null;
		
		return repo.save(materia);
	}
	
	/**
	 * TODO Javadoc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Materias> getAll(){
		return repo.getAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Materia getByNome(@PathVariable("id") String id){
		return repo.getById(id);
	}
	
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public Materia getByNome(@PathVariable("nome") String nome){
		return repo.getByNome(nome);
	}
	
}
