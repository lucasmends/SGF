package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Falta {
	
	@DBRef
	private Aluno aluno;
	
	private String motivo;
	
	public Falta(){
		
	}
	
	public Falta(Aluno aluno, String motivo) {
		super();
		this.aluno = aluno;
		this.motivo = motivo;
	}

	/**
	 * @return the aluno
	 */
	public Aluno getAluno() {
		return aluno;
	}

	/**
	 * @param aluno the aluno to set
	 */
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"aluno\":  \"" + aluno + "\", \"motivo\":  \"" + motivo + "\"}";
	}
	
	

}
