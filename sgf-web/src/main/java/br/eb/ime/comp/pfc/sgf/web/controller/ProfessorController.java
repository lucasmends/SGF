package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;
import java.util.Comparator;
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
	public String index(Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isProfessor())
			return "redirect:/403";
		
		List<Professor> professores = service.getAll();
		//ordenar por nome
		professores.sort(new Comparator<Professor>() {

			@Override
			public int compare(Professor o1, Professor o2) {
				return o1.getNome().compareTo(o2.getNome());
			}
		});
		
		model.addAttribute("title", "Professores");
		model.addAttribute("professores", professores);

		
		model.addAttribute("user", user);
		return "professor/index";
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String id, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Professor professor = service.getById(id);
		model.addAttribute("professor", professor);
		model.addAttribute("title", "Professor");

		model.addAttribute("user", user);
		return "professor/professor";
	}

	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model) {
		return "redirect:/professor/";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newProfessor(Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isCoordenador())
			return "redirect:/403";
		
		model.addAttribute("title", "Novo Professor");

		model.addAttribute("user", user);

		return "professor/new";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createProfessor(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("coordenador") String coordenador, @RequestParam("engenharias") String[] engenharias,
			@RequestParam("password") String password, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isCoordenador())
			return "redirect:/403";
		
		Professor professor = new Professor(nome, email, "false", password);
		professor.setEngenharias(engenharias);
		if (coordenador != null)
			professor.setCoordenador(coordenador);

		String id = service.create(professor).getId();
		return "redirect:" + "/professor/id/" + id;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editProfessor(@PathVariable("id") String id, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Professor professor = service.getById(id);
		
		if(!user.isCoordenador())
			if( !(user.isProfessor() && user.getName().equals(professor.getEmail())) )
				return "redirect:/403";	
		
		model.addAttribute("professor", professor);
		model.addAttribute("title", "Editar Professor");

		model.addAttribute("user", user);
		return "professor/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveProfessor(@PathVariable("id") String id, @RequestParam("nome") String nome,
			@RequestParam("engenharias") String[] engenharias, @RequestParam("password") String password, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Professor professor = service.getById(id);
		
		if(!user.isCoordenador())
			if( !(user.isProfessor() && user.getName().equals(professor.getEmail())) )
				return "redirect:/403";	
		
		professor.setNome(nome);
		professor.setCoordenador(professor.getCoordenador());
		if (user.getName().equals(professor.getEmail()))
			if (!password.equals(""))
				professor.setPassword(password);
		professor.setEngenharias(engenharias);
	
		service.update(professor);
		return "redirect:" + "/professor/id/" + id;
	}
}
