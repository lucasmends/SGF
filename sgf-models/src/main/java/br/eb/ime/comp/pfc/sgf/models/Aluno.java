package br.eb.ime.comp.pfc.sgf.models;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Definição da classe da entidade Aluno, que é a base desse microserviço
 * 
 * @author lucasmendes
 *
 */
@Document(collection="aluno")
public class Aluno {

	@Id
	private String id;
	
	@Indexed(unique = true)
	private String numero;
	
	private String nome;
	
	@Indexed(unique = true)
	private String email;
	
	/**
	 * Armazenado como hash
	 */
	private String password;
	

	public Aluno(){
		
	}
	
	public Aluno(String numero, String nome, String email, String password){
		this.numero = numero;
		this.nome = nome;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * @return o ID do aluno
	 */
	public String getId() {
		return id;
	}

	public String getNome(){
		return nome;
	}

	/**
	 * @return o número do aluno
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 *  Seta o número do aluno
	 * @param numero
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	/**
	 *  Seta o nome do aluno
	 * @param numero
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * 
	 * @return o email do aluno
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Seta o email do aluno
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return o password em hash do aluno
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *  Grava o password que deve estar em hash do aluno
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"id\": \"" + id + "\", \"numero\": \"" + numero + "\", \"nome\" :\"" + nome + "\", \"email\": \"" + email + 
				"\", \"password\": \""	+ password + "\"}";
	}

	public String assinatura(){
		return DigestUtils.sha1Hex(this.id);
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
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	
	
}
