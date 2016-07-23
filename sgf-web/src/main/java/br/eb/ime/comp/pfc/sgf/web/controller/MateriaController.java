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

import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.web.User;
import br.eb.ime.comp.pfc.sgf.web.service.MateriaService;
import br.eb.ime.comp.pfc.sgf.web.service.ProfessorService;

@Controller
@RequestMapping("/materia")
public class MateriaController {
	
	@Autowired
	private MateriaService service;
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isProfessor())
			return "redirect:/403";
		
		List<Materia> materias = service.getAll();
		//ordenar por nome
		materias.sort(new Comparator<Materia>() {

			@Override
			public int compare(Materia o1, Materia o2) {
				return o1.getNome().compareTo(o2.getNome());
			}
		});
		
		model.addAttribute("title", "Materias");
		model.addAttribute("materias", materias);
		model.addAttribute("user", user);
		return "materia/index";
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getByNome(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isProfessor())
			return "redirect:/403";
		
		Materia materia = service.getById(id);
		model.addAttribute("materia",materia);
		model.addAttribute("title", "Materia");
		model.addAttribute("user", user);
		
		return "materia/materia";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model){
		return "redirect:/materia/";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newMateria(Model model, Materia materia, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isCoordenador())
			return "redirect:/403";
		
		List<Professor> professores = professorService.getAll();
		model.addAttribute("professores", professores);
		model.addAttribute("title", "Nova Materia");
		model.addAttribute("user", user);
		return "materia/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createMateria(@RequestParam("nome") String nome, @RequestParam("id") String id, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isCoordenador())
			return "redirect:/403";
		
		Professor professor = professorService.getById(id);
		Materia materia = new Materia(nome, professor);
		service.create(materia);
		return "redirect:" + "/materia/";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editMateria(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isCoordenador())
			return "redirect:/403";
		
		Materia materia = service.getById(id);
		List<Professor> professores = professorService.getAll();
		model.addAttribute("professores", professores);
		model.addAttribute("materia", materia);
		model.addAttribute("title", "Editar Materia");
		model.addAttribute("user", user);
		return "materia/edit";
	}
	
	@RequestMapping(value = "/edit/{materiaId}", method = RequestMethod.POST)
	public String saveMateria(@PathVariable("materiaId") String materiaId, @RequestParam("id") String id, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isCoordenador())
			return "redirect:/403";
		
		Materia materia = service.getById(materiaId);
		Professor professor = professorService.getById(id);
		materia.setProfessor(professor);
		service.update(materia);
		
		return "redirect:" + "/materia/";
	}
}
