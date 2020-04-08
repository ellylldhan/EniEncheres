/**
 * 
 */
package fr.eni.encheres.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe en charge de rencenser l'ensemble des erreurs (par leur code)
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 8 avr. 2020
 */
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private List<Integer> listeCodesErreur;
	
	public BusinessException() {
		super();
		this.listeCodesErreur=new ArrayList<>();
	}
	
	/**
	 * 
	 * @param code Code de l'erreur. 
	 * Doit avoir un message associé dans un fichier properties.
	 */
	public void ajouterErreur(int code)
	{
		if(!this.listeCodesErreur.contains(code))
		{
			this.listeCodesErreur.add(code);
		}
	}
	
	public boolean hasErreurs()
	{
		return this.listeCodesErreur.size()>0;
	}
	
	public List<Integer> getListeCodesErreur()
	{
		return this.listeCodesErreur;
	}

}
