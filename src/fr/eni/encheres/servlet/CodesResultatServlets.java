/**
 * 
 */
package fr.eni.encheres.servlet;

/**
 * Classe en charge de définir les codes erreurs de la couche Servlet - Codes compris entre 30000 et 39999
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 8 avr. 2020
 */
public abstract class CodesResultatServlets {
	
	public static final Integer NOM_ARTICLE_OBLIGATOIRE = 30000;
	public static final Integer DESCRIPTION_ARTICLE_OBLIGATOIRE = 30001;
	public static final Integer PRIX_INITIAL_ARTICLE_OBLIGATOIRE = 30002;
	public static final Integer DATE_DEBUT_ENCHERES_ARTICLE_OBLIGATOIRE = 30003;
	public static final Integer DATE_FIN_ENCHERES_ARTICLE_OBLIGATOIRE = 30004;
	public static final Integer ERREURS_CHAMPS_RETRAIT = 30005;

}
