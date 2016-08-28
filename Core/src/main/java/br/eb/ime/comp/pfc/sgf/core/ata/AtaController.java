package br.eb.ime.comp.pfc.sgf.core.ata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Ata;
import br.eb.ime.comp.pfc.sgf.models.Professor;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Classe que funciona como uma RESTApi para a persistência da entidade Disciplina
 * São utilizados os conceitos de verbos do HTTP, sendo que requisições POST tem significado de criação
 * GET de requisição de recurso, PUT atualição e DELETE de exclusão
 * 
 * @author igorcesar
 *
 */
@RestController
@RequestMapping("/")
public class AtaController {

	/**
	 * Conexão com o banco MongoDB, injeção de dependência feita automaticamente pelo Spring
	 */
	@Autowired
	private AtaRepository repo;
	

	@RequestMapping(method = RequestMethod.POST)
	public Ata create(@RequestBody Ata ata){
		
		return repo.create(ata);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Ata getById(@PathVariable("id") String id){
		
		return repo.getById(id);
	}
	
	@RequestMapping(value = "/turma/{id}", method = RequestMethod.GET)
	public List<Ata> getByTurma(@PathVariable("id") String idTurma){
		return repo.getByTurma(idTurma);
	}
	
	@RequestMapping(value = "/professor/{id}", method = RequestMethod.GET)
	public List<Ata> getByProfessor(@PathVariable("id") String idProfessor){
		return repo.getByProfessor(idProfessor);
	}
	
	@RequestMapping(value = "/xerife", method = RequestMethod.GET)
	public List<Ata> getByXerife(@RequestBody Aluno xerife){
		return repo.getByXerife(xerife);
	}
	
	@RequestMapping(value = "/coordenador", method = RequestMethod.GET)
	public List<Ata> getByCoordenador(@RequestBody Professor coordenador){
		return repo.getByCoordenador(coordenador);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Ata update(@RequestBody Ata ata){
		
		return repo.save(ata);
	}
}
