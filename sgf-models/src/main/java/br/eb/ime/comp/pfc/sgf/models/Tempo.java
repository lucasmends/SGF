package br.eb.ime.comp.pfc.sgf.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Tempo {

	private String ordem;

	@DBRef
	private Disciplina disciplina;

	@JsonIgnore
	@Transient
	private List<Disciplina> disciplinasAvulsas;

	@DBRef
	private List<Aluno> faltas;

	@JsonIgnore
	@Transient
	private List<Aluno> alunosAvulsos;

	private Assinatura assinatura;

	@JsonIgnore
	@Transient
	private Boolean saved;

	@JsonIgnore
	@Transient
	private Boolean professorDoTempo;

	public Tempo() {

	}

	public Tempo(String ordem, List<Disciplina> disciplinasAvulsas, List<Aluno> alunosAvulsos) {
		this.ordem = ordem;
		this.disciplinasAvulsas = disciplinasAvulsas;
		this.alunosAvulsos = alunosAvulsos;
	}

	public Tempo(String ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return the ordem
	 */
	public String getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem
	 *            the ordem to set
	 */
	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return the disciplina
	 */
	public Disciplina getDisciplina() {
		return disciplina;
	}

	/**
	 * @param disciplina
	 *            the disciplina to set
	 */
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
		if (this.disciplina != null) {
			Assinatura assinatura = new Assinatura();
			assinatura.setIdProfessor(this.disciplina.getProfessor().getId());
			this.assinatura = assinatura;
		}
	}

	/**
	 * @return the faltas
	 */
	public List<Aluno> getFaltas() {
		if (this.faltas == null)
			this.faltas = new ArrayList<Aluno>();
		return faltas;
	}

	/**
	 * @param faltas
	 *            the faltas to set
	 */
	public void setFaltas(List<Aluno> faltas) {
		this.faltas = faltas;
	}

	/**
	 * @return the assinatura
	 */
	public Assinatura getAssinatura() {
		return assinatura;
	}

	/**
	 * @param assinatura
	 *            the assinatura to set
	 */
	public void setAssinatura(Assinatura assinatura) {
		this.assinatura = assinatura;
	}

	/**
	 * @return the alunosAvulsos
	 */
	public List<Aluno> getAlunosAvulsos() {
		if (this.alunosAvulsos == null)
			this.alunosAvulsos = new ArrayList<Aluno>();
		return this.alunosAvulsos;
	}

	/**
	 * @param alunosAvulsos
	 *            the alunosAvulsos to set
	 */
	public void setAlunosAvulsos(List<Aluno> alunosAvulsos) {
		this.alunosAvulsos = alunosAvulsos;
	}

	/**
	 * @return the disciplinasAvulsas
	 */
	public List<Disciplina> getDisciplinasAvulsas() {
		if (disciplinasAvulsas == null)
			disciplinasAvulsas = new ArrayList<Disciplina>();
		return disciplinasAvulsas;
	}

	/**
	 * @param disciplinasAvulsas
	 *            the disciplinasAvulsas to set
	 */
	public void setDisciplinasAvulsas(List<Disciplina> disciplinasAvulsas) {
		this.disciplinasAvulsas = disciplinasAvulsas;
	}

	/**
	 * @return the salved
	 */
	public boolean isSaved() {
		if (this.saved == null)
			this.saved = false;
		return saved;
	}

	/**
	 * @param salved
	 *            the salved to set
	 */
	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	/**
	 * @return the professorDoTempo
	 */
	public Boolean getProfessorDoTempo() {
		if (professorDoTempo == null)
			professorDoTempo = false;
		return professorDoTempo;
	}

	/**
	 * @param professorDoTempo
	 *            the professorDoTempo to set
	 */
	public void setProfessorDoTempo(Boolean professorDoTempo) {
		this.professorDoTempo = professorDoTempo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"ordem\":  \"" + ordem + "\", \"disciplina\":  \"" + disciplina + "\", \"faltas\":  \"" + faltas
				+ "\", \"assinatura\":  \"" + assinatura + "\"}";
	}

}
