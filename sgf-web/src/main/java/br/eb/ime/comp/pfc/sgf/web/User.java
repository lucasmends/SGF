package br.eb.ime.comp.pfc.sgf.web;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class User {
	private final Collection<GrantedAuthority> authorities;
	private final String name;

	public User(AbstractAuthenticationToken u){
		this.authorities = u.getAuthorities();
		this.name = u.getName();
	}
	
	/**
	 * @return the authorities
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public boolean isAluno(){
		return this.getAuthorities().contains(Utils.roles[Utils.ALUNO]);
	}
	
	public boolean isProfessor(){
		return this.getAuthorities().contains(Utils.roles[Utils.PROFESSOR]);
	}
	
	public boolean isCoordenador(){
		return this.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]);
	}
}
