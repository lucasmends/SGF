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

import br.eb.ime.comp.pfc.sgf.models.Aluno;

@Service
public class AlunoService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Aluno getByNumero(String numero){
		return restTemplate.getForObject(ServiceName.aluno + "/{numero}", Aluno.class, numero);
	}
	
	public Aluno create(Aluno aluno){
		return restTemplate.postForObject(ServiceName.aluno , aluno, Aluno.class);
	}
	
	public Aluno update(Aluno aluno){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Aluno> entity = new HttpEntity<Aluno>(aluno, headers);
		
		ResponseEntity<Aluno> response = restTemplate.exchange(ServiceName.aluno, HttpMethod.PUT, entity, Aluno.class);
		
		if(response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		return null;
	}
	
	public List<Aluno> getAll(){
		ResponseEntity<Aluno[]> response = restTemplate.getForEntity(ServiceName.aluno, Aluno[].class);
		return Arrays.asList(response.getBody());
	}
}
