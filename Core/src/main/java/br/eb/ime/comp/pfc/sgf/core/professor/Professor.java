/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.core.professor;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lucasmendes
 *
 */
@Document(collection="aluno")
public class Professor {
	
	@Id
	private String id;
	
	private String nome;
	
	private String email;
	
	private Boolean coordenador;
	
	/**
	 * Armazenado como hash
	 */
	private String password;
	
	private Set<String> engenharias;
	
	
	public Professor(String nome, String email, Boolean coordenador, String password) {
		this.nome = nome;
		this.email = email;
		this.coordenador = coordenador;
		this.password = password;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return se é coordenador
	 */
	public Boolean isCoordenador() {
		return this.coordenador;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return a lista das engenharias
	 */
	public Set<String> getEngenharias(){
		return this.engenharias;
	}
	
	/**
	 * @param engenharia a engenharia a ser adicionada
	 */
	public void addEngenharia(String engenharia) {
		this.engenharias.add(engenharia);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
}
