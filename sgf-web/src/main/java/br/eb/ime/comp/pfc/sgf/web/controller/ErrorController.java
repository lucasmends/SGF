package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ErrorController {

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String index(Principal user, Model model){
		model.addAttribute("title", "Sem permissão");
		model.addAttribute("name", user.getName());
		model.addAttribute("role",user.getClass().toString());
		return "403";
	}
}
