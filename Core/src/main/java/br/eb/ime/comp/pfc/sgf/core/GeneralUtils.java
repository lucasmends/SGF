package br.eb.ime.comp.pfc.sgf.core;

public class GeneralUtils {
	/**
	 * Pega os indices de início e fim de um dado atributo
	 * 
	 * @param turmaJSON a string que contém o atributo
	 * @param name o nome do atributo
	 * @param open o caracter que abre o atributo
	 * @param close o caracter que fecha o atributo
	 * @return os índices de início e fim
	 */
	public static int[] getInd(String turmaJSON, String name, char open, char close) {
		int idStringMaterias = turmaJSON.indexOf(name);
		if (idStringMaterias > 0) {
			int idFirst = turmaJSON.substring(idStringMaterias).indexOf("{") + idStringMaterias;
			int idLast = idFirst;
			int count = 1;
			for (int i = idFirst + 1; i < turmaJSON.length(); i++) {
				if (turmaJSON.charAt(i) == open)
					count++;
				else if (turmaJSON.charAt(i) == close) {
					count--;
					if (count == 0) {
						idLast = i + 1;
						break;
					}
				}
			}
			int[] result = { idFirst, idLast };
			return result;
		}
		return null;
	}
}
