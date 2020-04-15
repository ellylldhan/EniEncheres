package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.DalException;

public interface UtilisateurDAO extends DAO<Utilisateur> {
	
	/**
	 * Méthode en charge de modifier le nombre de credit
	 * @param utilisateur
	 * @return
	 * @throws DalException
	 */
	public void updateCredit(Utilisateur u)  throws DalException ;
	
}
