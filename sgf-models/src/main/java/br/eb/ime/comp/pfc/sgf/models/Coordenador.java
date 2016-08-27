package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Coordenador {

	@DBRef
	private Professor coordenador;
	
	private String assinatura;

	public Coordenador() {
	}

	public Coordenador(Professor coordenador, String assinatura) {
		this.coordenador = coordenador;
		this.assinatura = assinatura;
	}

	/**
	 * @return the coordenador
	 */
	public Professor getCoordenador() {
		return coordenador;
	}

	/**
	 * @param coordenador the coordenador to set
	 */
	public void setCoordenador(Professor coordenador) {
		this.coordenador = coordenador;
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
		return "{\"coordenador\":  \"" + coordenador + "\", \"assinatura\":  \"" + assinatura + "\"}";
	}
	
	
	
}
