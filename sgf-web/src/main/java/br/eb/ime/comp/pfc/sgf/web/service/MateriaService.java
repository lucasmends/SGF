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

import br.eb.ime.comp.pfc.sgf.models.Materia;

@Service
public class MateriaService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Materia getById(String id){
		return restTemplate.getForObject(ServiceName.materia + "/{id}", Materia.class, id);
	}
	
	public Materia getByNome(String nome){
		return restTemplate.getForObject(ServiceName.materia + "/nome/{nome}", Materia.class, nome);
	}
	
	public Materia create(Materia materia){

		return restTemplate.postForObject(ServiceName.materia , materia, Materia.class);
	}
	
	public Materia update(Materia materia){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Materia> entity = new HttpEntity<Materia>(materia, headers);
		
		ResponseEntity<Materia> response = restTemplate.exchange(ServiceName.materia, HttpMethod.PUT, entity, Materia.class);
		
		if(response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		return null;
	}
	
	public List<Materia> getAll(){
		ResponseEntity<Materia[]> response = restTemplate.getForEntity(ServiceName.materia, Materia[].class);
		return Arrays.asList(response.getBody());
	}
}
