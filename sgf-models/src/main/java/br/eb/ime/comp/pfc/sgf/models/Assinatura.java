package br.eb.ime.comp.pfc.sgf.models;

public class Assinatura {

	private String idProfessor;
	
	private String assinatura;
	
	public Assinatura(){
		
	}

	public Assinatura(String professor, String assinatura) {
		super();
		this.idProfessor = professor;
		this.assinatura = assinatura;
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
	 * @return the idProfessor
	 */
	public String getIdProfessor() {
		return idProfessor;
	}

	/**
	 * @param idProfessor the idProfessor to set
	 */
	public void setIdProfessor(String idProfessor) {
		this.idProfessor = idProfessor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"idProfessor\":  \"" + idProfessor + "\", \"assinatura\":  \"" + assinatura + "\"}";
	}
	
	
}
