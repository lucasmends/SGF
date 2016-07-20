package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.web.Utils;
import br.eb.ime.comp.pfc.sgf.web.service.AlunoService;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	
	@Autowired
	private AlunoService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u){
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		
		if(!user.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]))
			return "redirect:/403";
		
		List<Aluno> alunos = service.getAll();
		model.addAttribute("title", "Alunos");
		model.addAttribute("alunos",alunos);
		return "aluno/index";
	}
	
	@RequestMapping(value = "/numero/{numero}", method = RequestMethod.GET)
	public String getByNumero(@PathVariable("numero") String numero, Model model, Principal u){
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		
		if(user.getAuthorities().contains(Utils.roles[Utils.ALUNO])){
			//se um aluno tenta acessar a página de outro aluno
			if(!user.getName().equals(numero))
				return "redirect:/403";
		}

		Aluno aluno = service.getByNumero(numero);
		model.addAttribute("aluno",aluno);
		model.addAttribute("title", "Aluno");
		return "aluno/aluno";
	}
	
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model, Principal u){
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		
		if(!user.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]))
			return "redirect:/403";
		
		List<Aluno> alunos = service.getAll();
		model.addAttribute("title", "Alunos");
		model.addAttribute("alunos",alunos);
		return "aluno/all";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newAluno(Model model, Principal u){
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		if(!user.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]))
			return "redirect:/403";
		
		model.addAttribute("title", "Novo Aluno");
		return "aluno/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String createAluno(@RequestParam("nome") String nome, @RequestParam("email") String email, 
			@RequestParam("numero") String numero, @RequestParam("password") String password, Principal u){
		
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		if(!user.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]))
			return "redirect:/403";
		
		Aluno aluno = new Aluno(numero, nome, email, password);
		service.create(aluno);
		return "redirect:" + "/aluno";
	}
	
	@RequestMapping(value = "/edit/{numero}", method = RequestMethod.GET)
	public String editAluno(@PathVariable("numero") String numero, Model model, Principal u){
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		if(!user.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]))
			return "redirect:/403";
		
		Aluno aluno = service.getByNumero(numero);
		model.addAttribute("aluno", aluno);
		model.addAttribute("title", "Editar Aluno");
		return "aluno/edit";
	}
	
	@RequestMapping(value = "/edit/{numero}", method = RequestMethod.POST)
	public String saveAluno(@PathVariable("numero") String numero,
			@RequestParam("nome") String nome, @RequestParam("email") String email, 
			@RequestParam("password") String password, Principal u){
		UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) u;
		if(!user.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]))
			return "redirect:/403";
		
		Aluno aluno = service.getByNumero(numero);
		aluno.setEmail(email);
		aluno.setNome(nome);
		service.update(aluno);
		return "redirect:" + "/aluno";
	}
}
