/**
 * 
 */
package fr.eni.encheres.bo;

import java.io.Serializable;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class Categorie implements Serializable{
	
	private int noCategorie;
	private String libelle;
	
	/**
	 * Getter pour noCategorie.
	 * @return the noCategorie
	 */
	public int getNoCategorie() {
		return noCategorie;
	}
	
	/**
	 * Setter pour noCategorie.
	 * @param noCategorie the noCategorie to set
	 */
	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}
	
	/**
	 * Getter pour libelle.
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	
	/**
	 * Setter pour libelle.
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Constructeur
	 * @param libelle
	 */
	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Constructeur
	 * @param noCategorie
	 * @param libelle
	 */
	public Categorie(int noCategorie, String libelle) {
		this(libelle);
		this.noCategorie = noCategorie;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + "]";
	}
	
}
