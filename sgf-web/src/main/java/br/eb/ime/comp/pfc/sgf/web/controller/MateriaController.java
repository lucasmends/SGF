package br.eb.ime.comp.pfc.sgf.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.web.service.MateriaService;
import br.eb.ime.comp.pfc.sgf.web.service.ProfessorService;

@Controller
@RequestMapping("/materia")
public class MateriaController {
	
	@Autowired
	private MateriaService service;
	private ProfessorService professorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		List<Materia> materias = service.getAll();
		model.addAttribute("title", "Materias");
		model.addAttribute("Materias", materias);
		return "materia/index";
	}
	
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public String getByNome(@PathVariable("nome") String nome, Model model){
		Materia materia = service.getByNome(nome);
		model.addAttribute("materia",materia);
		model.addAttribute("title", "Materia");
		return "materia/materia";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model){
		List<Materia> materias = service.getAll();
		model.addAttribute("title", "Materias");
		model.addAttribute("materias", materias);
		return "materia/all";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newMateria(Model model){
		List<Professor> professores = professorService.getAll();
		model.addAttribute("professores", professores);
		model.addAttribute("title", "Nova Materia");
		return "materia/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createMateria(@RequestParam("nome") String nome, @RequestParam("professor") Professor professor){
		Materia materia  = new Materia(nome, professor);
		service.create(materia);
		return "redirect:" + "/materia/";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editMateria(@PathVariable("id") String id, Model model){
		Materia materia = service.getById(id);
		List<Professor> professores = professorService.getAll();
		model.addAttribute("professores", professores);
		model.addAttribute("materia", materia);
		model.addAttribute("title", "Editar Materia");
		return "materia/edit";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveMateria(@PathVariable("id") String id, @RequestParam("professor") Professor professor){
		Materia materia = service.getById(id);
		materia.setProfessor(professor);
		service.update(materia);
		return "redirect:" + "/materia/";
	}
}
