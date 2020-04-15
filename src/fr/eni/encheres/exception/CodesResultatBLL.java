/**
 * 
 */
package fr.eni.encheres.exception;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 8 avr. 2020
 */
public abstract class CodesResultatBLL {
	/**
	 * Aucun enregistrement trouvé 
	 */
	public static final int DATE_EXPIRE = 1003;
	/**
	 * Aucun enregistrement trouvé 
	 */
	public static final int Select_OBJET_NOTFOUND = 1004;
	
	/**
	 * Information manquante
	 */
	public static final int OBJET_NOTCONFORM = 1005;
	
	/**
	 * Problème lié à l'insertion des données
	 */
	public static final int INSERT_OBJET_NOTINSERT= 1006;
	
	
	/**
	 * Problème lié à la mise à jour des données
	 */
	public static final int UPDATE_OBJET_NOTUPDATE= 1007;
	
	/**
	 * Vérifie si la valeur peut être insérer
	 */
	//public static final int CHECK_INSERT_OK = 1008;

	/*
	 * Utilisateur non trouvé en BDD
	 */
	public static final int USER_NOT_FOUND = 1009;

	/**
	 * Pas asser de crédit
	 */
	public static final int MISSING_CREDIT = 1010;
	
	/**
	 * L'utiliateur à déjà le dernier à  avoir enchéri
	 */
	public static final int USER_LAST_ENCHERIR = 1011;
	
	/**
	 * Login ou mot de passe incorrect
	 */
	public static final int WRONG_USER_INPUTS = 1012;

	/**
	 * Utilisateur non connecté
	 */
	public static final int USER_NOT_CONNECTED = 1013;
}
