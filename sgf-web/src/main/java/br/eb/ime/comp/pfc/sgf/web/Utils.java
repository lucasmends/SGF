package br.eb.ime.comp.pfc.sgf.web;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.eb.ime.comp.pfc.sgf.models.Client;

public class Utils {
	
	public static final GrantedAuthority[] roles = {new SimpleGrantedAuthority(Client.ROLE_ALUNO),
													new SimpleGrantedAuthority(Client.ROLE_PROFESSOR),
													new SimpleGrantedAuthority(Client.ROLE_COORDENADOR)};
	
	public static final int ALUNO = 0;
	public static final int PROFESSOR = 1;
	public static final int COORDENADOR = 2;
}
