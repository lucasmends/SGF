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
@Document(collection = "professor")
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

	public Professor() {

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
	 * @param nome
	 *            the nome to set
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
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public void setCoordenador(String coordenador) {
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
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return a lista das engenharias
	 */
	public Set<String> getEngenharias() {
		return !(this.engenharias == null) ? this.engenharias : null;
	}

	/**
	 * 
	 * A lista de engenharias é um conjunto onde os elementos são distintos
	 * 
	 * @param engenharia
	 *            a engenharia a ser adicionada
	 * @return o próprio objeto
	 */
	public Professor addEngenharia(String engenharia) {
		if (this.engenharias == null)
			this.engenharias = new HashSet<String>();
		engenharias.add(engenharia);
		return this;
	}

	public Professor addEngenharias(String[] engenharias) {
		if (this.engenharias == null)
			this.engenharias = new HashSet<String>();
		for (String eng : engenharias)
			this.engenharias.add(eng);
		return this;
	}

	public Professor setEngenharias(String[] engenharias) {
		this.engenharias = new HashSet<String>();
		for (String eng : engenharias)
			this.engenharias.add(eng);
		return this;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public boolean isCoordenador(){
		return this.coordenador.equals(Professor.IS_COORDEADOR);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "P{\"id\": \"" + id + ", \"nome\": \"" + nome + "\", \"email\": \"" + email + "\", \"coordenador\": \"" + coordenador
				+ "\", \"password\": \"" + password + "\", \"engenharias\": \"" + engenharias + "\"}";
	}
	
	
}