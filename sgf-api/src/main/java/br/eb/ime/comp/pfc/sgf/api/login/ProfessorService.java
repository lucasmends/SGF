package br.eb.ime.comp.pfc.sgf.api.login;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Professor;

@Service
public class ProfessorService {
	
	private static String professorService = "http://PROFESSOR-SERVICE";
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Professor getByEmail(String email){
		return restTemplate.getForObject(professorService + "/{email}", Professor.class, email);
	}
	
	public List<Professor> getAll(){
		ResponseEntity<Professor[]> response = restTemplate.getForEntity(professorService, Professor[].class);
		return Arrays.asList(response.getBody());
	}
}
