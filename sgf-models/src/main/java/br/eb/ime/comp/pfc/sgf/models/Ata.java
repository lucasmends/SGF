package br.eb.ime.comp.pfc.sgf.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ata")
public class Ata {

	@Id
	private String id;
	
	private String data;
	
	private String idTurma;
	
	private List<Tempo> tempos;

	private Xerife xerife;
	
	private Coordenador coordenador;
	
	public Ata(){
		
	}

	public Ata(String id, String data, String idTurma, List<Tempo> tempos, Xerife chefe, Coordenador coordenador) {
		super();
		this.id = id;
		this.data = data;
		this.idTurma = idTurma;
		this.tempos = tempos;
		this.xerife = chefe;
		this.coordenador = coordenador;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the turma
	 */
	public String getIdTurma() {
		return idTurma;
	}

	/**
	 * @param turma the turma to set
	 */
	public void setTurma(String turma) {
		this.idTurma = turma;
	}

	/**
	 * @return the tempos
	 */
	public List<Tempo> getTempos() {
		return tempos;
	}

	/**
	 * @param tempos the tempos to set
	 */
	public void setTempos(List<Tempo> tempos) {
		if(this.tempos == null)
			this.tempos = new ArrayList<Tempo>();
		this.tempos = tempos;
	}

	/**
	 * @return the chefe
	 */
	public Xerife getXerife() {
		return xerife;
	}

	/**
	 * @param chefe the chefe to set
	 */
	public void setXerife(Xerife xerife) {
		this.xerife = xerife;
	}

	/**
	 * @return the coordenador
	 */
	public Coordenador getCoordenador() {
		return coordenador;
	}

	/**
	 * @param coordenador the coordenador to set
	 */
	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"id\":  \"" + id + "\", \"data\":  \"" + data + "\", \"idTurma\":  \"" + idTurma + "\", \"tempos\":  \""
				+ tempos + "\", \"xerife\":  \"" + xerife + "\", \"coordenador\":  \"" + coordenador + "\"}";
	}
	
	
	
}
