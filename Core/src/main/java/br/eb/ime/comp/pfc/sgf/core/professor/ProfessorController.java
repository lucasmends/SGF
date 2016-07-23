/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.core.professor;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
	public Professor getByEmail(@RequestBody String professorJSON) throws ParseException{
		JSONObject professor = (JSONObject) new JSONParser().parse(professorJSON);
		return repo.findByEmail((String) professor.get("email"));
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public Professor getById(@PathVariable("id") String id){
		return repo.findById(id);
	}

	@RequestMapping(value = "/engenharia/{engenharia}", method = RequestMethod.GET)
	public List<Professor> findByEngenharia(@PathVariable("engenharia") String engenharia){
		return repo.findByEngenharia(engenharia);
	}
	
}
