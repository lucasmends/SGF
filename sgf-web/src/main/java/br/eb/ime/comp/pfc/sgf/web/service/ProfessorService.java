package br.eb.ime.comp.pfc.sgf.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Professor;

@Service
public class ProfessorService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Professor getByEmail(String email){
		return restTemplate.getForObject(ServiceName.professor + "/{email}", Professor.class, email);
	}
	
	public Professor create(Professor professor){
		return restTemplate.postForObject(ServiceName.professor , professor, Professor.class);
	}
	
	public Professor update(Professor professor){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Professor> entity = new HttpEntity<Professor>(professor, headers);
		
		ResponseEntity<Professor> response = restTemplate.exchange(ServiceName.professor, HttpMethod.PUT, entity, Professor.class);
		
		if(response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		return null;
	}
	
	public List<Professor> getAll(){
		ResponseEntity<Professor[]> response = restTemplate.getForEntity(ServiceName.professor, Professor[].class);
		return Arrays.asList(response.getBody());
	}
}
