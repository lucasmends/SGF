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

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.models.Disciplina;
import br.eb.ime.comp.pfc.sgf.models.Turma;
import br.eb.ime.comp.pfc.sgf.models.Ata;

import br.eb.ime.comp.pfc.sgf.web.User;
import br.eb.ime.comp.pfc.sgf.web.service.AlunoService;
import br.eb.ime.comp.pfc.sgf.web.service.ProfessorService;
import br.eb.ime.comp.pfc.sgf.web.service.DisciplinaService;
import br.eb.ime.comp.pfc.sgf.web.service.TurmaService;
import br.eb.ime.comp.pfc.sgf.web.service.AtaService;

@Controller
@RequestMapping("/disciplina")
public class AtaController {
	
	@Autowired
	private DisciplinaService service;
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isProfessor())
			return "redirect:/403";
		
		List<Disciplina> disciplinas = service.getAll();
		//ordenar por nome
		disciplinas.sort(new Comparator<Disciplina>() {

			@Override
			public int compare(Disciplina o1, Disciplina o2) {
				return o1.getNome().compareTo(o2.getNome());
			}
		});
		
		model.addAttribute("title", "Disciplinas");
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("user", user);
		return "disciplina/index";
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getByNome(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isProfessor())
			return "redirect:/403";
		
		Disciplina disciplina = service.getById(id);
		model.addAttribute("disciplina",disciplina);
		model.addAttribute("title", "Disciplina");
		model.addAttribute("user", user);
		
		return "disciplina/disciplina";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model){
		return "redirect:/disciplina/";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newDisciplina(Model model, Disciplina disciplina, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isCoordenador())
			return "redirect:/403";
		
		List<Professor> professores = professorService.getAll();
		model.addAttribute("professores", professores);
		model.addAttribute("title", "Nova Disciplina");
		model.addAttribute("user", user);
		return "disciplina/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createDisciplina(@RequestParam("nome") String nome, @RequestParam("id") String id, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isCoordenador())
			return "redirect:/403";
		
		Professor professor = professorService.getById(id);
		Disciplina disciplina = new Disciplina(nome, professor);
		service.create(disciplina);
		return "redirect:" + "/disciplina/";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editDisciplina(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isCoordenador())
			return "redirect:/403";
		
		Disciplina disciplina = service.getById(id);
		List<Professor> professores = professorService.getAll();
		model.addAttribute("professores", professores);
		model.addAttribute("disciplina", disciplina);
		model.addAttribute("title", "Editar Disciplina");
		model.addAttribute("user", user);
		return "disciplina/edit";
	}
	
	@RequestMapping(value = "/edit/{disciplinaId}", method = RequestMethod.POST)
	public String saveDisciplina(@PathVariable("disciplinaId") String disciplinaId, @RequestParam("id") String id, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isCoordenador())
			return "redirect:/403";
		
		Disciplina disciplina = service.getById(disciplinaId);
		Professor professor = professorService.getById(id);
		disciplina.setProfessor(professor);
		service.update(disciplina);
		
		return "redirect:" + "/disciplina/";
	}
}
