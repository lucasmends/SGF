package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="materia")
public class Materia {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String nome;
	
	@DBRef
	private Professor professor;

	public Materia(){
		
	}
	
	public Materia(String nome, Professor professor) {
		this.nome = nome;
		this.professor = professor;
		/**
		 * precisamos criar o id
		 */
	}

	/**
	 * @return the professor
	 */
	public Professor getProfessor() {
		return professor;
	}

	/**
	 * @param professor the professor to set
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
}
