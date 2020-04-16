/**
 * 
 */
package fr.eni.encheres.bo;

import java.io.Serializable;

import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.CodesResultatBLL;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class Utilisateur implements Serializable {

	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private int credit;
	private boolean administrateur = false;
	
	/**
	 * Getter pour noUtilisateur.
	 * @return the noUtilisateur
	 */
	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	
	/**
	 * Setter pour noUtilisateur.
	 * @param noUtilisateur the noUtilisateur to set
	 */
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
	
	/**
	 * Getter pour pseudo.
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Setter pour pseudo.
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	/**
	 * Getter pour nom.
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Setter pour nom.
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Getter pour prenom.
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * Setter pour prenom.
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * Getter pour email.
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter pour email.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter pour telephone.
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**
	 * Setter pour telephone.
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	/**
	 * Getter pour rue.
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}
	
	/**
	 * Setter pour rue.
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}
	
	/**
	 * Getter pour codePostal.
	 * @return the codePostal
	 */
	public String getCodePostal() {
		return codePostal;
	}
	
	/**
	 * Setter pour codePostal.
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	/**
	 * Getter pour ville.
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}
	
	/**
	 * Setter pour ville.
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	/**
	 * Getter pour motDePasse.
	 * @return the motDePasse
	 */
	public String getMotDePasse() {
		return motDePasse;
	}
	
	/**
	 * Setter pour motDePasse.
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	/**
	 * Getter pour credit.
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}
	
	/**
	 * Setter pour credit.
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	/**
	 * Getter pour administrateur.
	 * @return the administrateur
	 */
	public boolean isAdministrateur() {
		return administrateur;
	}
	
	/**
	 * Setter pour administrateur.
	 * @param administrateur the administrateur to set
	 */
	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	/**
	 * Constructeur
	 * @param pseudo
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param telephone
	 * @param rue
	 * @param codePostal
	 * @param ville
	 * @param motDePasse
	 * @param credit
	 * @param administrateur
	 */
	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	/**
	 * Constructeur
	 * @param noUtilisateur
	 * @param pseudo
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param telephone
	 * @param rue
	 * @param codePostal
	 * @param ville
	 * @param motDePasse
	 * @param credit
	 * @param administrateur
	 */
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		this(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
		this.noUtilisateur = noUtilisateur;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + "]";
	}
	
    public boolean checkCredit(int proposition) {
    	
    	if (proposition > this.credit) {
			return true;
		}
    	return false;
    }
}
