package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.web.User;
import br.eb.ime.comp.pfc.sgf.web.service.ProfessorService;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		List<Professor> professores = service.getAll();
		model.addAttribute("title", "Professores");
		model.addAttribute("professores", professores);
		
		model.addAttribute("user", user);
		return "professor/index";
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Professor professor = service.getById(id);
		model.addAttribute("professor",professor);
		model.addAttribute("title", "Professor");
		
		model.addAttribute("user", user);
		return "professor/professor";
	}
	
	/*@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public String getByEmail(@PathVariable("email") String email, Model model){
		Professor professor = service.getByEmail(email);
		model.addAttribute("professor",professor);
		model.addAttribute("title", "Professor");
		return "professor/professor";
	}*/
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		List<Professor> professores = service.getAll();
		model.addAttribute("title", "Professores");
		model.addAttribute("professores",professores);
		
		model.addAttribute("user", user);
		return "professor/all";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newProfessor(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		model.addAttribute("title", "Novo Professor");
		
		model.addAttribute("user", user);
		return "professor/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createProfessor(@RequestParam("nome") String nome, @RequestParam("email") String email, 
			@RequestParam("coordenador") String coordenador, @RequestParam("engenharia") String engenharia, 
			@RequestParam("password") String password){
		Professor professor  = new Professor(nome, email, coordenador, password);
		professor.addEngenharia(engenharia);
		String id = service.create(professor).getId();
		return "redirect:" + "/professor/id/" + id;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editProfessor(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Professor professor = service.getById(id);
		model.addAttribute("professor", professor);
		model.addAttribute("title", "Editar Professor");
		
		model.addAttribute("user", user);
		return "professor/edit";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveProfessor(@PathVariable("id") String id,
			@RequestParam("nome") String nome, @RequestParam("coordenador") String coordenador, 
			@RequestParam("engenharia") String engenharia, @RequestParam("password") String password){
		Professor professor = service.getById(id);
		professor.setNome(nome);
		professor.setCoordenador(coordenador);
		professor.setPassword(password);
		professor.addEngenharia(engenharia);
		service.update(professor);
		return "redirect:" + "/professor/id/" + id;
	}
}
