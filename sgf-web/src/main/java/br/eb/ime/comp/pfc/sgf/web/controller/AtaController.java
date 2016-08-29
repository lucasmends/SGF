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
@RequestMapping("/ata")
public class AtaController {
	
	@Autowired
	private AlunoService alunoService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private TurmaService turmaService;
	@Autowired
	private AtaService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		List<Ata> atas = null;
		
		if(user.isCoordenador()) {
			String email = user.getName();
			Professor coordenador = professorService.getByEmail(email);
			atas = service.getByCoordenador(coordenador.getId());
		}
		else
			if(user.isAluno()) {
				String numero = user.getName();
				Aluno xerife = alunoService.getByNumero(numero);
				atas = service.getByXerife(xerife.getId());
			}
			else
				return "redirect:/403";
		
		model.addAttribute("title", "Atas");
		model.addAttribute("atas", atas);
		model.addAttribute("user", user);
		return "ata/index";
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isProfessor())
			return "redirect:/403";
		
		Ata ata = service.getById(id);
		model.addAttribute("ata",ata);
		model.addAttribute("title", "Ata");
		model.addAttribute("user", user);
		
		return "ata/ata";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model){
		return "redirect:/ata/";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newAta(Model model, Ata ata, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isAluno())
			return "redirect:/403";
		
		//List<Turma> turmas = turmaService.getAll();
		// obter turma do xerife
		String numero = user.getName();
		Aluno xerife = alunoService.getByNumero(numero);
		Turma turmaDoXerife = turmaService.getByAluno(xerife);
		/*for(int i=0; i<turmas.size(); i++) {
			List<Aluno> alunosDaTurma = turmas.get(i).getAlunos();
			for(int j=0; j<alunosDaTurma.size(); j++) {
				if(alunosDaTurma.get(j).getId() == xerife.getId()) {
					turmaDoXerife = turmas.get(i);
				}
			}
		}*/
		
		if(turmaDoXerife == null)
			return "redirect:/403";
		
		List<Aluno> alunos = turmaDoXerife.getAlunos();
		List<Disciplina> disciplinas = turmaDoXerife.getDisciplinas();
		
		ata = new Ata();
		ata.setTurma(turmaDoXerife.getId());
		
		model.addAttribute("ata", ata);
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("alunos", alunos);
		model.addAttribute("title", "Nova Ata de Faltas");
		model.addAttribute("user", user);
		return "ata/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createDisciplina(@RequestParam("ata") Ata ata, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		
		if(!user.isAluno())
			return "redirect:/403";
		
		service.create(ata);
		return "redirect:" + "/ata/";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editDisciplina(@PathVariable("id") String id, Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isAluno())
			return "redirect:/403";
		
		Ata ata = service.getById(id);
		
		Turma turmaDoXerife = turmaService.getById(ata.getIdTurma());
		List<Aluno> alunos = turmaDoXerife.getAlunos();
		List<Disciplina> disciplinas = turmaDoXerife.getDisciplinas();
		
		model.addAttribute("ata", ata);
		model.addAttribute("disciplinas", disciplinas);
		model.addAttribute("alunos", alunos);
		model.addAttribute("title", "Editar Ata de Faltas");
		model.addAttribute("user", user);
		return "ata/edit";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveDisciplina(@PathVariable("id") String id, @RequestParam("ata") Ata ata, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if(!user.isAluno())
			return "redirect:/403";
		
		service.update(ata);
		
		return "redirect:" + "/ata/";
	}
}
