package br.eb.ime.comp.pfc.sgf.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Client;

@Service
public class LoginService {

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	public Client getClient(String id){
		return restTemplate.getForObject(ServiceName.login + "/{id}", Client.class, id);
	}
	
}
