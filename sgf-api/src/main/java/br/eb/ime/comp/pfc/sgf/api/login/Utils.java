package br.eb.ime.comp.pfc.sgf.api.login;

import java.util.regex.Pattern;

public class Utils {
	private static Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Verifica se a string passada é um email válido
	 * 
	 * @param email o email que deseja ser verificado
	 * @return true se é um email válido e false se nãoß
	 */
	public static boolean isEmail(String email){
		return p.matcher(email).find();
	}
	
	/**
	 * Verifica se a string passada é um número válido
	 * 
	 * @param number a string que deseja se verificada
	 * @return true se for número e false se não
	 */
	public static boolean isNumber(String number){
		try{
			Integer.parseInt(number);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
}
