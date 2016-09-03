package br.eb.ime.comp.pfc.sgf.web;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.eb.ime.comp.pfc.sgf.models.Client;
import br.eb.ime.comp.pfc.sgf.models.Coordenador;
import br.eb.ime.comp.pfc.sgf.models.Tempo;
import br.eb.ime.comp.pfc.sgf.models.Xerife;


public class Utils {
	
	public static final GrantedAuthority[] roles = {new SimpleGrantedAuthority(Client.ROLE_ALUNO),
													new SimpleGrantedAuthority(Client.ROLE_PROFESSOR),
													new SimpleGrantedAuthority(Client.ROLE_COORDENADOR)};
	
	public static final int ALUNO = 0;
	public static final int PROFESSOR = 1;
	public static final int COORDENADOR = 2;
	
	public static<T> T exchange(String url, T obj, RestTemplate restTemplate, HttpMethod method, Class<T> cls){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<T> entity = new HttpEntity<T>(obj, headers);
		
		ResponseEntity<T> response = restTemplate.exchange(url, method, entity, cls);
		
		if(response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		return null;
	}
	
	public static boolean recebeuAssinaturaXerife(Xerife xerife){
		return xerife.getAssinado();
	}
	
	public static boolean recebeuAssinaturaProfessor(Tempo tempo){
		return tempo.isSaved();
	}
	
	public static boolean recebeuAssinaturaCoordenador(Coordenador coordenador){
		return coordenador.getAssinado();
	}
}
