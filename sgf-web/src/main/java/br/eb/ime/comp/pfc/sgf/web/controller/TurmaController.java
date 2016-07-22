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

import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Turma;

import br.eb.ime.comp.pfc.sgf.web.service.MateriaService;
import br.eb.ime.comp.pfc.sgf.web.User;
import br.eb.ime.comp.pfc.sgf.web.service.AlunoService;
import br.eb.ime.comp.pfc.sgf.web.service.TurmaService;

@Controller
@RequestMapping("/turma")
public class TurmaController {
	
	@Autowired
	private MateriaService materiaService;
	@Autowired
	private AlunoService alunoService;
	@Autowired
	private TurmaService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		List<Turma> turmas = service.getAll();
		System.out.println(turmas.size());
		model.addAttribute("title", "Turmas");
		model.addAttribute("turmas", turmas);
		model.addAttribute("user", user);
		return "turma/index";
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		model.addAttribute("turma",turma);
		model.addAttribute("title", "Turma");
		model.addAttribute("user", user);
		return "turma/turma";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		List<Turma> turmas = service.getAll();
		model.addAttribute("title", "Turma");
		model.addAttribute("turmas", turmas);
		model.addAttribute("user", user);
		return "turma/all";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTurma(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		List<Aluno> alunos = alunoService.getAll();
		List<Materia> materias = materiaService.getAll();
		model.addAttribute("alunos", alunos);
		model.addAttribute("materias", materias);
		model.addAttribute("title", "Nova Turma");
		model.addAttribute("user", user);
		return "turma/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createTurma(@RequestParam("ano") String ano, @RequestParam("engenharia") String engenharia, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma  = new Turma(ano, engenharia);
		service.create(turma);
		return "redirect:" + "/turma/";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editTurma(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		model.addAttribute("turma", turma);
		model.addAttribute("title", "Editar Turma");
		model.addAttribute("user", user);
		return "turma/edit";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveTurma(@PathVariable("id") String id, 
			@RequestParam("ano") String ano, @RequestParam("engenharia") String engenharia, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		turma.setEngenharia(engenharia);
		turma.setAno(ano);
		service.update(turma);
		return "redirect:" + "/turma/";
	}
	
	@RequestMapping(value = "/edit/alunos/{id}", method = RequestMethod.GET)
	public String addAluno(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		List<Aluno> alunos = alunoService.getAll();
		model.addAttribute("turma", turma);
		model.addAttribute("alunos", alunos);
		model.addAttribute("title", "Adicionar alunos");
		model.addAttribute("user", user);
		return "turma/alunos";
	}
	
	@RequestMapping(value = "/edit/alunos/{id}", method = RequestMethod.POST)
	public String saveAluno(@PathVariable("id") String id, @RequestParam("numero") String numero, 
			Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		Aluno aluno = alunoService.getByNumero(numero);
		turma.addAluno(aluno);
		service.update(turma);
		return "redirect:" + "/turma/edit/alunos" + id;
	}
	
	@RequestMapping(value = "/edit/materias/{id}", method = RequestMethod.GET)
	public String addMateria(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		List<Materia> materias = materiaService.getAll();
		model.addAttribute("turma", turma);
		model.addAttribute("materias", materias);
		model.addAttribute("title", "Adicionar materias");
		model.addAttribute("user", user);
		return "turma/materias";
	}
	
	@RequestMapping(value = "/edit/materias/{id}", method = RequestMethod.POST)
	public String saveMateria(@PathVariable("id") String id, @RequestParam("materiaId") String materiaId, 
			Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		Turma turma = service.getById(id);
		Materia materia = materiaService.getById(materiaId);
		turma.addMateria(materia);
		service.update(turma);
		return "redirect:" + "/turma/edit/materias" + id;
	}
}
