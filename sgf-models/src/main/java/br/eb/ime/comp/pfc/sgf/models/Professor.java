/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lucasmendes
 *
 */
@Document(collection="professor")
public class Professor {
	public static String IS_COORDEADOR = "true";
	
	@Id
	private String id;
	
	private String nome;
	
	@Indexed(unique = true)
	private String email;

	private String coordenador;
	
	/**
	 * Armazenado como hash
	 */
	private String password;
	
	private Set<String> engenharias;
	
	
	public Professor(){
		
	}
	
	public Professor(String nome, String email, String coordenador, String password) {
		this.nome = nome;
		this.email = email;
		this.coordenador = coordenador;
		this.password = password;
		this.engenharias = null;
	}
	
	public Professor(String id, String nome, String email, String coordenador, String password,
			Set<String> engenharias) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.coordenador = coordenador;
		this.password = password;
		this.engenharias = engenharias;
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
	
	public void setCoordenador(String coordenador){
		this.coordenador = coordenador;
	}
	
	/**
	 * @return se é coordenador
	 */
	public String getCoordenador() {
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
		return !(this.engenharias == null)?this.engenharias:null;
	}
	
	/**
	 * 
	 * A lista de engenharias é um conjunto onde os elementos são distintos
	 * 
	 * @param engenharia a engenharia a ser adicionada
	 * @return o próprio objeto
	 */
	public Professor addEngenharia(String engenharia) {
		if(this.engenharias == null)
			this.engenharias = new HashSet<String>();
		engenharias.add(engenharia);
		return this;
	}

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
}
