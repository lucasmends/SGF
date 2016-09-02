package br.eb.ime.comp.pfc.sgf.web;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class User {
	private final Collection<GrantedAuthority> authorities;
	private final String name;
	private final boolean aluno;
	private final boolean professor;
	private final boolean coordenador;
	
	public User(AbstractAuthenticationToken u){
		this.authorities = u.getAuthorities();
		this.name = u.getName();
		this.aluno = this.getAuthorities().contains(Utils.roles[Utils.ALUNO]);
		this.professor = this.getAuthorities().contains(Utils.roles[Utils.PROFESSOR]);
		this.coordenador = this.getAuthorities().contains(Utils.roles[Utils.COORDENADOR]);
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
		return this.aluno;
	}
	
	public boolean isProfessor(){
		return professor;
	}
	
	public boolean isCoordenador(){
		return coordenador;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"authorities\":  \"" + authorities + "\", \"name\":  \"" + name + "\", \"aluno\":  \"" + aluno
				+ "\", \"professor\":  \"" + professor + "\", \"coordenador\":  \"" + coordenador + "\"}";
	}
	
}
