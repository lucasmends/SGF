package br.eb.ime.comp.pfc.sgf.core;

public class GeneralUtils {
	/**
	 * Pega os indices de início e fim de um dado atributo.
	 * Por exemplo, se passado 'materias' em name, e '{', '}' como open e close, respectivamente,
	 * retorna os índices do { inicial e do } final de "'materias': { ... }" que está contindo em json
	 * 
	 * @param json a string que contém o atributo
	 * @param name o nome do atributo
	 * @param open o caracter que abre o atributo
	 * @param close o caracter que fecha o atributo
	 * @return os índices de início e fim
	 */
	public static int[] getInd(String json, String name, char open, char close) {
		int idStringMaterias = json.indexOf(name);
		if (idStringMaterias > 0) {
			int idFirst = json.substring(idStringMaterias).indexOf(open) + idStringMaterias;
			int idLast = idFirst;
			int count = 1;
			for (int i = idFirst + 1; i < json.length(); i++) {
				if (json.charAt(i) == open)
					count++;
				else if (json.charAt(i) == close) {
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
