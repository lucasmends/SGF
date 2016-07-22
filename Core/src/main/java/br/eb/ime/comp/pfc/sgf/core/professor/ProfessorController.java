/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.core.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Professor;

/**
 * @author lucasmendes
 *
 */
@RestController
@RequestMapping("/")
public class ProfessorController {
	
	@Autowired
	private ProfessorRepository repo;


	@RequestMapping(method = RequestMethod.GET)
	public List<Professor> getAll(){
		return repo.getAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Professor create(@RequestBody Professor professor){
		return repo.create(professor);
	}
	
	/**
	 * TODO
	 * 
	 * @param email
	 * @param professor
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public Professor update(@RequestBody Professor professor){

		return repo.save(professor);
	}
	
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public Professor getByEmail(@RequestBody Professor professor){
		return repo.findByEmail(professor.getEmail());
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public Professor getById(@PathVariable("id") String id){
		return repo.findById(id);
	}
	
	/**
	 * 
	 * Adiciona uma engenharia para o professor
	 * 
	 * @param email o campo unico que indentifica o professor
	 * @param engenharia a engenharia a ser adicionada
	 * @return
	 */
	@RequestMapping(value = "/email/engenharia/{engenharia}", method = RequestMethod.PUT)
	public Professor addEngenharia(@RequestBody Professor professor_, @PathVariable("engenharia") String engenharia){
		Professor professor = repo.findByEmail(professor_.getEmail());
		
		if(professor.equals(null))
			return null;
		
		return repo.save(professor.addEngenharia(engenharia));
		// será que não dá pra usar apenas o PUT padrão, passando o nova lista de engenharias e dando save? acho que funciona
	}
	
}
