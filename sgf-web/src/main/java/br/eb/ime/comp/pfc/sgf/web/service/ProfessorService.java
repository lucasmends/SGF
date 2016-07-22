package br.eb.ime.comp.pfc.sgf.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.web.Utils;

@Service
public class ProfessorService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Professor getByEmail(String email){
		Professor professor = new Professor();
		professor.setEmail(email);
		return  restTemplate.postForObject(ServiceName.professor + "/email", professor, Professor.class);
		//return Utils.exchange(ServiceName.professor + "/email", professor, restTemplate, HttpMethod.GET, Professor.class);	
	}
	
	public Professor getById(String id){
		return restTemplate.getForObject(ServiceName.professor + "/id/{id}", Professor.class, id);
	}
	
	public Professor create(Professor professor){
		return restTemplate.postForObject(ServiceName.professor , professor, Professor.class);
	}
	
	public Professor update(Professor professor){
		return Utils.exchange(ServiceName.professor, professor, restTemplate, HttpMethod.PUT, Professor.class);
	}
	
	public List<Professor> getAll(){
		ResponseEntity<Professor[]> response = restTemplate.getForEntity(ServiceName.professor, Professor[].class);
		return Arrays.asList(response.getBody());
	}
}
