package br.eb.ime.comp.pfc.sgf.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Tempo {
	
	private String ordem;
	
	@DBRef
	private Disciplina disciplina;
	
	private List<Falta> faltas;
	
	private Assinatura assinatura;
	
	public Tempo(){
		
	}

	public Tempo(String ordem, Disciplina disciplina, List<Falta> faltas) {
		super();
		this.ordem = ordem;
		this.disciplina = disciplina;
		this.faltas = faltas;
	}

	/**
	 * @return the ordem
	 */
	public String getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem the ordem to set
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
	 * @param disciplina the disciplina to set
	 */
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	/**
	 * @return the faltas
	 */
	public List<Falta> getFaltas() {
		if(this.faltas == null)
			this.faltas = new ArrayList<Falta>();
		return faltas;
	}

	/**
	 * @param faltas the faltas to set
	 */
	public void setFaltas(List<Falta> faltas) {
		this.faltas = faltas;
	}

	/**
	 * @return the assinatura
	 */
	public Assinatura getAssinatura() {
		return assinatura;
	}

	/**
	 * @param assinatura the assinatura to set
	 */
	public void setAssinatura(Assinatura assinatura) {
		this.assinatura = assinatura;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"ordem\":  \"" + ordem + "\", \"disciplina\":  \"" + disciplina + "\", \"faltas\":  \"" + faltas
				+ "\", \"assinatura\":  \"" + assinatura + "\"}";
	}


	
}
