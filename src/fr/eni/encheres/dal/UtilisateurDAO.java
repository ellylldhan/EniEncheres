package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * The interface Utilisateur dao.
 */
public interface UtilisateurDAO extends DAO<Utilisateur> {
    /**
     * Select by pseudo utilisateur.
     *
     * @param p the p
     * @return the utilisateur
     * @throws DalException the dal exception
     */
    public Utilisateur selectByArg(String a) throws BusinessException;

    /**
     * Méthode en charge de modifier le nombre de credit
     * @param utilisateur
     * @return
     * @throws DalException
     */
    public void updateCredit(Utilisateur u)  throws BusinessException ;
}
