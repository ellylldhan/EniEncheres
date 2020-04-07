package fr.eni.encheres.bo;

import java.time.LocalDate;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class Article {

	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebut;
	private LocalDate dateFinEncheres;
	private int prixInitial;
	private int prixVente;
	private Utilisateur utilisateur;
	private Categorie categorie;
	
	/**
	 * Getter pour noArticle.
	 * @return the noArticle
	 */
	public int getNoArticle() {
		return noArticle;
	}
	
	/**
	 * Setter pour noArticle.
	 * @param noArticle the noArticle to set
	 */
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	
	/**
	 * Getter pour nomArticle.
	 * @return the nomArticle
	 */
	public String getNomArticle() {
		return nomArticle;
	}
	
	/**
	 * Setter pour nomArticle.
	 * @param nomArticle the nomArticle to set
	 */
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	
	/**
	 * Getter pour description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter pour description.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Getter pour dateDebut.
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	
	/**
	 * Setter pour dateDebut.
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	
	/**
	 * Getter pour dateFinEncheres.
	 * @return the dateFinEncheres
	 */
	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}
	
	/**
	 * Setter pour dateFinEncheres.
	 * @param dateFinEncheres the dateFinEncheres to set
	 */
	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
	
	/**
	 * Getter pour prixInitial.
	 * @return the prixInitial
	 */
	public int getPrixInitial() {
		return prixInitial;
	}
	
	/**
	 * Setter pour prixInitial.
	 * @param prixInitial the prixInitial to set
	 */
	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}
	
	/**
	 * Getter pour prixVente.
	 * @return the prixVente
	 */
	public int getPrixVente() {
		return prixVente;
	}
	
	/**
	 * Setter pour prixVente.
	 * @param prixVente the prixVente to set
	 */
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	
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
	 * Getter pour categorie.
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}
	
	/**
	 * Setter pour categorie.
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/**
	 * Constructeur
	 * @param nomArticle
	 * @param description
	 * @param dateDebut
	 * @param dateFinEncheres
	 * @param prixInitial
	 * @param prixVente
	 * @param utilisateur
	 * @param categorie
	 */
	public Article(String nomArticle, String description, LocalDate dateDebut, LocalDate dateFinEncheres,
			int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFinEncheres = dateFinEncheres;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}

	/**
	 * Constructeur
	 * @param noArticle
	 * @param nomArticle
	 * @param description
	 * @param dateDebut
	 * @param dateFinEncheres
	 * @param prixInitial
	 * @param prixVente
	 * @param utilisateur
	 * @param categorie
	 */
	public Article(int noArticle, String nomArticle, String description, LocalDate dateDebut, LocalDate dateFinEncheres,
			int prixInitial, int prixVente, Utilisateur utilisateur, Categorie categorie) {
		this(nomArticle, description, dateDebut, dateFinEncheres, prixInitial, prixVente, utilisateur, categorie);
		this.noArticle = noArticle;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Article [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebut=" + dateDebut + ", dateFinEncheres=" + dateFinEncheres + ", prixInitial=" + prixInitial
				+ ", prixVente=" + prixVente + ", utilisateur=" + utilisateur + ", categorie=" + categorie + "]";
	}
	
}
