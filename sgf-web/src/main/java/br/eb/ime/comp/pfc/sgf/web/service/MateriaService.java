package br.eb.ime.comp.pfc.sgf.web.service;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Materia;
import br.eb.ime.comp.pfc.sgf.web.Utils;

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
		return Utils.exchange(ServiceName.materia, materia, restTemplate, HttpMethod.PUT, Materia.class);
	}
	
	public List<Materia> getAll(){
		ResponseEntity<Materia[]> response = restTemplate.getForEntity(ServiceName.materia, Materia[].class);
		return Arrays.asList(response.getBody());
	}
}
