/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
	private List<Disciplina> disciplinas;
	
	@DBRef
	private List<Aluno> alunos;
	
	public Turma(){
		
	}
	
	public Turma(String ano, String engenharia) {
		this.ano = ano;
		this.engenharia = engenharia;
		this.disciplinas = new ArrayList<Disciplina>();
		this.alunos = new ArrayList<Aluno>();
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
	public List<Disciplina> getDisciplinas() {
		if(this.disciplinas == null)
			this.disciplinas = new ArrayList<Disciplina>();
		return disciplinas;
	}

	/**
	 * @param disciplinas the disciplinas to set
	 */
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	/**
	 * @param alunos the alunos to set
	 */
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Turma addDisciplina(Disciplina disciplina){
		if(this.disciplinas == null)
			this.disciplinas = new ArrayList<Disciplina>();
		this.disciplinas.add(disciplina);
		return this;
	}
	
	/**
	 * @return the alunos
	 */
	public List<Aluno> getAlunos() {
		if(this.alunos == null)
			this.alunos = new ArrayList<Aluno>();
		return alunos;
	}
	
	public Turma addAluno(Aluno aluno){
		if(this.alunos == null)
			this.alunos = new ArrayList<Aluno>();
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
