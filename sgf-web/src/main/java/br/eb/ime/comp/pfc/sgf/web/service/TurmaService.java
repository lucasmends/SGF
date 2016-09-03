package br.eb.ime.comp.pfc.sgf.web.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.models.Turma;
import br.eb.ime.comp.pfc.sgf.web.Utils;

@Service
public class TurmaService {
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@Autowired
	private ProfessorService professorService;
	
	public Turma getById(String id){
		return restTemplate.getForObject(ServiceName.turma + "/{id}", Turma.class, id);
	}
	
	public Turma getByAluno(Aluno aluno){
		List<Turma> turmas = this.getAll();
		for(Turma turma : turmas){
			if(turma.getAlunos().contains(aluno))
				return turma;
		}
		return null;
	}
	
	public Turma create(Turma turma){
		return restTemplate.postForObject(ServiceName.turma , turma, Turma.class);
	}
	
	public Turma update(Turma turma){
		return Utils.exchange(ServiceName.turma, turma, restTemplate, HttpMethod.PUT, Turma.class);
	}
	
	public List<Turma> getAll(){
		ResponseEntity<Turma[]> response = restTemplate.getForEntity(ServiceName.turma, Turma[].class);
		return Arrays.asList(response.getBody());
	}
	
	public Professor getCoordenador(Turma turma){
		String engenharia = turma.getEngenharia();
		for(Professor professor : professorService.getAll()){
			if(professor.getEngenharias().contains(engenharia)){
				if(professor.isCoordenador())
					return professor;
			}
		}
		return null;
	}
}
