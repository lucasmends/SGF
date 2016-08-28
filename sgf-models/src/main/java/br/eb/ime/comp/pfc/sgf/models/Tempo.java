package br.eb.ime.comp.pfc.sgf.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Tempo {
	
	private String ordem;
	
	@DBRef
	private Materia materia;
	
	private List<Falta> faltas;
	
	private Assinatura assinatura;
	
	public Tempo(){
		
	}

	public Tempo(String ordem, Materia materia, List<Falta> faltas) {
		super();
		this.ordem = ordem;
		this.materia = materia;
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
	 * @return the materia
	 */
	public Materia getMateria() {
		return materia;
	}

	/**
	 * @param materia the materia to set
	 */
	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	/**
	 * @return the faltas
	 */
	public List<Falta> getFaltas() {
		if(this.faltas == null)
			this.faltas = new ArrayList<>();
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
		return "{\"ordem\":  \"" + ordem + "\", \"materia\":  \"" + materia + "\", \"faltas\":  \"" + faltas
				+ "\", \"assinatura\":  \"" + assinatura + "\"}";
	}


	
}
