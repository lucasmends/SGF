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
	private Set<Disciplina> disciplinas;
	
	@DBRef
	private Set<Aluno> alunos;

	public Turma(){
		
	}
	
	public Turma(String ano, String engenharia) {
		this.ano = ano;
		this.engenharia = engenharia;
		this.disciplinas = new HashSet<Disciplina>();
		this.alunos = new HashSet<Aluno>();
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
	 * @return the disciplinas
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
	 * @return the disciplinas
	 */
	public Set<Disciplina> getDisciplinas() {
		if(this.disciplinas == null)
			this.disciplinas = new HashSet<Disciplina>();
		return disciplinas;
	}

	/**
	 * @param disciplinas the disciplinas to set
	 */
	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	/**
	 * @param alunos the alunos to set
	 */
	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Turma addDisciplina(Disciplina disciplina){
		if(this.disciplinas == null)
			this.disciplinas = new HashSet<Disciplina>();
		this.disciplinas.add(disciplina);
		return this;
	}
	
	/**
	 * @return the alunos
	 */
	public Set<Aluno> getAlunos() {
		if(this.alunos == null)
			this.alunos = new HashSet<Aluno>();
		return alunos;
	}
	
	public Turma addAluno(Aluno aluno){
		if(this.alunos == null)
			this.alunos = new HashSet<Aluno>();
		this.alunos.add(aluno);
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"id\":  \"" + id + "\", \"ano\":  \"" + ano + "\", \"engenharia\":  \"" + engenharia
				+ "\", \"disciplinas\":  \"" + disciplinas + "\", \"alunos\":  \"" + alunos + "\"}";
	}


	
}
