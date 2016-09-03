package br.eb.ime.comp.pfc.sgf.models;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Xerife {

	@DBRef
	private Aluno xerife;
	
	private String assinatura;

	@JsonIgnore
	@Transient
	private Boolean assinado;
	
	public Xerife() {
	
	}

	public Xerife(Aluno aluno) {
		super();
		this.xerife = aluno;
		this.assinatura = null;
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
	 * @return the xerife
	 */
	public Aluno getXerife() {
		return xerife;
	}

	/**
	 * @param xerife the xerife to set
	 */
	public void setXerife(Aluno xerife) {
		this.xerife = xerife;
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
		return "{\"xerife\":  \"" + xerife + "\", \"assinatura\":  \"" + assinatura + "\"}";
	}
	
	
	
	
}
