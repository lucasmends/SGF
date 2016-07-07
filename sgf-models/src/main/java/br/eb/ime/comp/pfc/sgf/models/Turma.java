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
		super();
		this.ano = ano;
		this.engenharia = engenharia;
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
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @return the materias
	 */
	public Set<Materia> getMaterias() {
		return materias;
	}

	public Turma addMateria(Materia materia){
		if(this.materias.equals(null))
			this.materias = new HashSet<Materia>();
		this.materias.add(materia);
		return this;
	}
	
	/**
	 * @return the alunos
	 */
	public Set<Aluno> getAlunos() {
		return alunos;
	}
	
	public Turma addAluno(Aluno aluno){
		if(this.alunos.equals(null))
			this.alunos = new HashSet<Aluno>();
		this.alunos.add(aluno);
		return this;
	}
}
