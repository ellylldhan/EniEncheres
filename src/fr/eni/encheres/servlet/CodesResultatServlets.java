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
	
	public static final Integer PSEUDO_UTILISATEUR_OBLIGATOIRE = 30006;
	public static final Integer PSEUDO_UTILISATEUR_UNIQUE = 30007;
	public static final Integer PRENOM_UTILISATEUR_OBLIGATOIRE = 30008;
	public static final Integer NOM_UTILISATEUR_OBLIGATOIRE = 30009;
	public static final Integer EMAIL_UTILISATEUR_OBLIGATOIRE = 30010;
	public static final Integer TELEPHONE_UTILISATEUR_OBLIGATOIRE = 30011;
	public static final Integer RUE_UTILISATEUR_OBLIGATOIRE = 30012;
	public static final Integer CODE_POSTAL_UTILISATEUR_OBLIGATOIRE = 30013;
	public static final Integer VILLE_UTILISATEUR_OBLIGATOIRE = 30014;
	public static final Integer MDP_UTILISATEUR_OBLIGATOIRE = 30015;
	public static final Integer MDP_UTILISATEUR_CONFIRMER = 30016;
	
	public static final Integer IDARTICLE_NOT_FOUND = 30017;
	public static final Integer Proposition_NOT_FOUND = 30018;
	
	// Pour résultat de recherche d'enchère par nom d'article
	public static final Integer ENCHERE_NOT_FOUND = 30019;

}
