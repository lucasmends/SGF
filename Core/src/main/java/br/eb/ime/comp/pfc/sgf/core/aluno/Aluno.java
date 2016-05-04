package br.eb.ime.comp.pfc.sgf.core.aluno;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
	
	@Field
	private String numero;
	
	@Field
	private String email;
	
	/**
	 * Armazenado como hash
	 */
	@Field
	private String password;
	
	@Field
	private String secao;

	public Aluno(){
		
	}
	
	public Aluno(String numero, String email, String password, String secao){
		this.numero = numero;
		this.email = email;
		this.password = password;
		this.secao = secao;
	}
	
	/**
	 * @return o ID do aluno
	 */
	public String getId() {
		return id;
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

	/**
	 * 
	 * @return a seção do aluno
	 */
	public String getSecao() {
		return secao;
	}

	/**
	 *  Seta a seção do aluno
	 * @param secao
	 */
	public void setSecao(String secao) {
		this.secao = secao;
	}
}
