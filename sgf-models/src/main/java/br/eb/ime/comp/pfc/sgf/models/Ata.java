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
	
	private List<Falta> faltas;

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
		if(tempos == null)
			tempos = new ArrayList<>();
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

	
	/**
	 * @return the faltas
	 */
	public List<Falta> getFaltas() {
		if(faltas == null)
			faltas = new ArrayList<>();
		return faltas;
	}

	/**
	 * @param faltas the faltas to set
	 */
	public void setFaltas(List<Falta> faltas) {
		this.faltas = faltas;
	}

	/**
	 * @param idTurma the idTurma to set
	 */
	public void setIdTurma(String idTurma) {
		this.idTurma = idTurma;
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
