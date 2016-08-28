package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.eb.ime.comp.pfc.sgf.web.User;

@Controller
public class WebController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		User user = new User((UsernamePasswordAuthenticationToken) u);
		model.addAttribute("title", "Sistema de Gerenciamento de Faltas");
		model.addAttribute("user", user);
		return "index";
	}
	
	@RequestMapping(value = "/aluno", method = RequestMethod.GET)
	public String aluno(){
		return "redirect:aluno/";
	}
	
	@RequestMapping(value = "/professor", method = RequestMethod.GET)
	public String professor(){
		return "redirect:professor/";
	}
	
	@RequestMapping(value = "/disciplina", method = RequestMethod.GET)
	public String disciplina(){
		return "redirect:disciplina/";
	}
	
	@RequestMapping(value = "/turma", method = RequestMethod.GET)
	public String turma(){
		return "redirect:turma/";
	}
}
