/**
 * 
 */
package fr.eni.encheres.bo;

import java.time.LocalDateTime;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class Enchere {

	private Utilisateur utilisateur; 
	private Article article;
	private LocalDateTime date_enchere;
	private int montant_enchere;
	/**
	 * Getter pour utilisateur.
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	/**
	 * Setter pour utilisateur.
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	/**
	 * Getter pour article.
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}
	/**
	 * Setter pour article.
	 * @param article the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
	}
	/**
	 * Getter pour date_enchere.
	 * @return the date_enchere
	 */
	public LocalDateTime getDate_enchere() {
		return date_enchere;
	}
	/**
	 * Setter pour date_enchere.
	 * @param date_enchere the date_enchere to set
	 */
	public void setDate_enchere(LocalDateTime date_enchere) {
		this.date_enchere = date_enchere;
	}
	/**
	 * Getter pour montant_enchere.
	 * @return the montant_enchere
	 */
	public int getMontant_enchere() {
		return montant_enchere;
	}
	/**
	 * Setter pour montant_enchere.
	 * @param montant_enchere the montant_enchere to set
	 */
	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}
	
	/**
	 * Constructeur
	 * @param utilisateur
	 * @param article
	 * @param date_enchere
	 * @param montant_enchere
	 */
	public Enchere(Utilisateur utilisateur, Article article, LocalDateTime date_enchere, int montant_enchere) {
		super();
		this.utilisateur = utilisateur;
		this.article = article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}
	
	
	
}
