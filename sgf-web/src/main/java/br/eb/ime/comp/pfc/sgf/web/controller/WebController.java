package br.eb.ime.comp.pfc.sgf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("title", "Sistema de Gerenciamento de Faltas");
		return "index";
	}
	
	@RequestMapping(value = "/aluno", method = RequestMethod.GET)
	public String aluno(){
		return "redirect:aluno/";
	}
}
