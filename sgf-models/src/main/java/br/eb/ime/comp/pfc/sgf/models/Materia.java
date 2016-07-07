package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Materia {
	
	private String id;
	
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
