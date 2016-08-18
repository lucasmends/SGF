package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;
import java.util.ArrayList;
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
	public String index(Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isProfessor())
			return "redirect:/403";

		List<Turma> turmas = service.getAll();
		// ordem por ano
		turmas.sort((o1, o2) -> o1.getAno().compareTo(o2.getAno()));

		model.addAttribute("title", "Turmas");
		model.addAttribute("turmas", turmas);
		model.addAttribute("user", user);
		return "turma/index";
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String id, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Turma turma = service.getById(id);
		model.addAttribute("turma", turma);
		model.addAttribute("title", "Turma");
		model.addAttribute("user", user);
		return "turma/turma";
	}

	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model) {
		return "redirect:/turma";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTurma(Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isCoordenador())
			return "redirect:/403";

		List<Aluno> alunos = alunoService.getAll();
		List<Materia> materias = materiaService.getAll();
		model.addAttribute("alunos", alunos);
		model.addAttribute("materias", materias);
		model.addAttribute("title", "Nova Turma");
		model.addAttribute("user", user);
		return "turma/new";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createTurma(@RequestParam("ano") String ano, @RequestParam("engenharia") String engenharia,
			@RequestParam("materias") String[] materias, @RequestParam("alunos") String[] alunos, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isCoordenador())
			return "redirect:/403";

		Turma turma = new Turma(ano, engenharia);
		for (String id : materias) {
			turma.addMateria(materiaService.getById(id));
		}
		for (String numero : alunos) {
			turma.addAluno(alunoService.getByNumero(numero));
		}
		service.create(turma);
		return "redirect:" + "/turma/";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editTurma(@PathVariable("id") String id, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isCoordenador())
			return "redirect:/403";

		Turma turma = service.getById(id);

		List<Materia> materiasAvulsas = new ArrayList<>();
		boolean flag = true;
		for (Materia materia : materiaService.getAll()) {
			for (Materia m : turma.getMaterias())
				if (m.getId().equals(materia.getId())) {
					flag = false;
					break;
				}
			if (flag)
				materiasAvulsas.add(materia);
			flag = true;
		}
		List<Aluno> alunosAvulsos = new ArrayList<>();

		for (Aluno aluno : alunoService.getAll()) {
			for (Aluno a : turma.getAlunos())
				if (a.getId().equals(aluno.getId())) {
					flag = false;
					break;
				}
			if (flag)
				alunosAvulsos.add(aluno);
			flag = true;
		}
		System.out.println(turma.getAno());
		model.addAttribute("turma", turma);
		model.addAttribute("materiasAvulsas", materiasAvulsas);
		model.addAttribute("alunosAvulsos", alunosAvulsos);
		model.addAttribute("title", "Editar Turma");
		model.addAttribute("user", user);
		return "turma/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveTurma(@RequestParam("ano") String ano, @RequestParam("materias") String[] materias,
			@PathVariable("id") String id, @RequestParam("alunos") String[] alunos, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isCoordenador())
			return "redirect:/403";

		Turma turma = service.getById(id);
		System.out.println(id);
		turma.setAno(ano);
		turma.setAlunos(null);
		turma.setMaterias(null);
		for (String idMateria : materias)
			turma.addMateria(materiaService.getById(idMateria));
		for (String numero : alunos)
			turma.addAluno(alunoService.getByNumero(numero));
		service.update(turma);
		return "redirect:" + "/turma/";
	}

	@RequestMapping(value = "/edit/alunos/{id}", method = RequestMethod.GET)
	public String addAluno(@PathVariable("id") String id, Model model, Principal u) {
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
	public String saveAluno(@PathVariable("id") String id, @RequestParam("numero") String numero, Model model,
			Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Turma turma = service.getById(id);
		Aluno aluno = alunoService.getByNumero(numero);
		turma.addAluno(aluno);
		service.update(turma);
		return "redirect:" + "/turma/edit/alunos" + id;
	}

	@RequestMapping(value = "/edit/materias/{id}", method = RequestMethod.GET)
	public String addMateria(@PathVariable("id") String id, Model model, Principal u) {
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
	public String saveMateria(@PathVariable("id") String id, @RequestParam("materiaId") String materiaId, Model model,
			Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Turma turma = service.getById(id);
		Materia materia = materiaService.getById(materiaId);
		turma.addMateria(materia);
		service.update(turma);
		return "redirect:" + "/turma/edit/materias" + id;
	}
}
