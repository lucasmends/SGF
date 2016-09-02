package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Xerife {

	@DBRef
	private Aluno xerife;
	
	private String assinatura;

	public Xerife() {
	
	}

	public Xerife(Aluno aluno) {
		super();
		this.xerife = aluno;
		this.assinatura = null;
	}

	/**
	 * @return the aluno
	 */
	public Aluno getAluno() {
		return xerife;
	}

	/**
	 * @param aluno the aluno to set
	 */
	public void setAluno(Aluno aluno) {
		this.xerife = aluno;
	}

	/**
	 * @return the assinatura
	 */
	public String getAssinatura() {
		return assinatura;
	}

	/**
	 * @param assinatura the assinatura to set
	 */
	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"xerife\":  \"" + xerife + "\", \"assinatura\":  \"" + assinatura + "\"}";
	}
	
	
	
	
}
