package br.eb.ime.comp.pfc.sgf.web.service;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Disciplina;
import br.eb.ime.comp.pfc.sgf.web.Utils;

@Service
public class DisciplinaService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Disciplina getById(String id){
		return restTemplate.getForObject(ServiceName.disciplina + "/{id}", Disciplina.class, id);
	}
	
	public Disciplina getByNome(String nome){
		return restTemplate.getForObject(ServiceName.disciplina + "/nome/{nome}", Disciplina.class, nome);
	}
	
	public Disciplina create(Disciplina disciplina){

		return restTemplate.postForObject(ServiceName.disciplina , disciplina, Disciplina.class);
	}
	
	public Disciplina update(Disciplina disciplina){
		return Utils.exchange(ServiceName.disciplina, disciplina, restTemplate, HttpMethod.PUT, Disciplina.class);
	}
	
	public List<Disciplina> getAll(){
		ResponseEntity<Disciplina[]> response = restTemplate.getForEntity(ServiceName.disciplina, Disciplina[].class);
		return Arrays.asList(response.getBody());
	}
}
