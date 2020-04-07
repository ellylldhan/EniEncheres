/**
 * 
 */
package fr.eni.encheres.exception;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public abstract class CodesResultatDAL {

	/**
	 * Echec général quand tentative d'ajouter un objet null
	 */
	public static final int INSERT_OBJET_NULL=10000;
	
	/**
	 * Echec général quand erreur non gérée à l'insertion 
	 */
	public static final int INSERT_OBJET_ECHEC=10001;
	
	/**
	 * Echec général quand la récupération de données a recontré un problème 
	 */
	public static final int SELECT_OBJET_ECHEC=10002;
	
	/**
	 * Echec général quand la mise à jour de données à recontré un problème 
	 */
	public static final int UPDATE_OBJET_ECHEC=1003;
}
