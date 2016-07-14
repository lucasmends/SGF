package br.eb.ime.comp.pfc.sgf.api.login;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Aluno;

@Service
public class AlunoService {
	
	private static String alunoService = "http://ALUNO-SERVICE";
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Aluno getByNumero(String numero){
		return restTemplate.getForObject(alunoService + "/{numero}", Aluno.class, numero);
	}
	
	public List<Aluno> getAll(){
		ResponseEntity<Aluno[]> response = restTemplate.getForEntity(alunoService, Aluno[].class);
		return Arrays.asList(response.getBody());
	}
}
