package br.eb.ime.comp.pfc.sgf.web.service;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Ata;
import br.eb.ime.comp.pfc.sgf.web.Utils;

@Service
public class AtaService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Ata getById(String id){
		return restTemplate.getForObject(ServiceName.ata + "/{id}", Ata.class, id);
	}
	
	public List<Ata> getByTurma(String id){
		ResponseEntity<Ata[]> response = restTemplate.getForEntity(ServiceName.ata + "/turma/{id}", Ata[].class, id);
		return Arrays.asList(response.getBody());
	}
	
	public List<Ata> getByProfessor(String id){
		ResponseEntity<Ata[]> response = restTemplate.getForEntity(ServiceName.ata + "/professor/{id}", Ata[].class, id);
		return Arrays.asList(response.getBody());
	}
	
	public List<Ata> getByXerife(String id){
		ResponseEntity<Ata[]> response = restTemplate.getForEntity(ServiceName.ata + "/xerife/{id}", Ata[].class, id);
		return Arrays.asList(response.getBody());
	}
	
	public List<Ata> getByCoordenador(String id){
		ResponseEntity<Ata[]> response = restTemplate.getForEntity(ServiceName.ata + "/coordenador/{id}", Ata[].class, id);
		return Arrays.asList(response.getBody());
	}
	
	public Ata create(Ata ata){

		return restTemplate.postForObject(ServiceName.ata , ata, Ata.class);
	}
	
	public Ata update(Ata ata){
		return Utils.exchange(ServiceName.ata, ata, restTemplate, HttpMethod.PUT, Ata.class);
	}
}
