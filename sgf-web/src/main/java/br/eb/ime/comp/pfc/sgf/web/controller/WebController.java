package br.eb.ime.comp.pfc.sgf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(){
		return "index";
	}
	
	@RequestMapping(value = "/aluno", method = RequestMethod.GET)
	public String aluno(){
		return "redirect:aluno/";
	}
}
