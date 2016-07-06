/**
 * 
 */
package br.eb.ime.comp.pfc.sgf.models;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lucasmendes
 *
 */
@Document(collection="turma")
public class Turma {

	@Id
	private String id;
	
	private String ano;
	
	private String engenharia;
	
	private Set<Materia> materias;
	
	private Set<Aluno> alunos;

	public Turma(){
		
	}
	
	public Turma(String ano, String engenharia) {
		super();
		this.ano = ano;
		this.engenharia = engenharia;
	}

	/**
	 * @return the engenharia
	 */
	public String getEngenharia() {
		return engenharia;
	}

	/**
	 * @param engenharia the engenharia to set
	 */
	public void setEngenharia(String engenharia) {
		this.engenharia = engenharia;
	}

	/**
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @return the materias
	 */
	public Set<Materia> getMaterias() {
		return materias;
	}

	/**
	 * @return the alunos
	 */
	public Set<Aluno> getAlunos() {
		return alunos;
	}
}
