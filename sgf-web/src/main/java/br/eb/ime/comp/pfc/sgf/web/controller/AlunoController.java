package br.eb.ime.comp.pfc.sgf.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.web.service.AlunoService;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private AlunoService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(){
		return "aluno/index";
	}
	
	@RequestMapping(value = "/numero/{numero}", method = RequestMethod.GET)
	public String getByNumero(@PathVariable("numero") String numero, Model model){
		Aluno aluno = service.getByNumero(numero);
		model.addAttribute("aluno",aluno);
		return "aluno/aluno";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model){
		List<Aluno> alunos = service.getAll();
		model.addAttribute("alunos",alunos);
		return "aluno/all";
	}
}
