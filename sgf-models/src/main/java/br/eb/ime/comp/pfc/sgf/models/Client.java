package br.eb.ime.comp.pfc.sgf.models;

import java.util.List;

public class Client {

	private String username;
	
	private String password;
	
	private List<String> roles;
	
	public Client() {
	}
	
	
	public Client(String username, String password, List<String> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}
	
	
}