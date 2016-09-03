package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Coordenador {

	@DBRef
	private Professor coordenador;
	
	private String assinatura;

	@JsonIgnore
	@Transient
	private Boolean assinado;
	
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
	
	/**
	 * @return the assinado
	 */
	public Boolean getAssinado() {
		if(assinado == null)
			assinado = false;
		return assinado;
	}

	/**
	 * @param assinado the assinado to set
	 */
	public void setAssinado(Boolean assinado) {
		this.assinado = assinado;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"coordenador\":  \"" + coordenador + "\", \"assinatura\":  \"" + assinatura + "\"}";
	}
	
	
	
}
