/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lucasmendes
 *
 */
@Document(collection="turma")
public class Turma {

	@Id
	private String id;

	private String ano;
	
	private String engenharia;
	
	@DBRef
	private Set<Materia> materias;
	
	@DBRef
	private Set<Aluno> alunos;

	public Turma(){
		
	}
	
	public Turma(String ano, String engenharia) {
		this.ano = ano;
		this.engenharia = engenharia;
		this.materias = new HashSet<>();
		this.alunos = new HashSet<>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the engenharia
	 */
	public String getEngenharia() {
		return engenharia;
	}

	/**
	 * @param engenharia the engenharia to set
	 */
	public void setEngenharia(String engenharia) {
		this.engenharia = engenharia;
	}
	
	/**
	 * @return the materias
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @return the materias
	 */
	public Set<Materia> getMaterias() {
		if(this.materias == null)
			this.materias = new HashSet<Materia>();
		return materias;
	}

	/**
	 * @param materias the materias to set
	 */
	public void setMaterias(Set<Materia> materias) {
		this.materias = materias;
	}

	/**
	 * @param alunos the alunos to set
	 */
	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Turma addMateria(Materia materia){
		if(this.materias == null)
			this.materias = new HashSet<Materia>();
		this.materias.add(materia);
		return this;
	}
	
	/**
	 * @return the alunos
	 */
	public Set<Aluno> getAlunos() {
		if(this.alunos == null)
			this.alunos = new HashSet<>();
		return alunos;
	}
	
	public Turma addAluno(Aluno aluno){
		if(this.alunos == null)
			this.alunos = new HashSet<Aluno>();
		this.alunos.add(aluno);
		return this;
	}

	
}
