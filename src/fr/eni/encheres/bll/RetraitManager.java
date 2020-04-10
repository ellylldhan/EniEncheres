/**
 * 
 */
package fr.eni.encheres.bll;

import java.util.List;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.RetraitDAO;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de contrôler les accès à la table RETRAITS de la base de données.
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class RetraitManager {
	
	private static Logger LOGGER = MonLogger.getLogger("RetraitManager");
    private static RetraitManager INSTANCE;
    
    private RetraitDAO retraitDAO = DAOFactory.getRetraitDAO();

	private RetraitManager() {
	}
    
	/**
	 * Méthode en charge de retourner une instance de la classe
	 * @return
	 * @throws DalException
	 * @throws BllException
	 */
    public static RetraitManager getInstance()  {
    	
    	if (INSTANCE == null) {
			INSTANCE = new RetraitManager();
		}
    	
    	return INSTANCE;
    }
    
    /**
     * Méthode en charge de retourner une liste de tous les retraits
     * @return
     */
    public List<Retrait> getRetraits() {
        List<Retrait> retraits = null;
        try {
        	retraits = retraitDAO.selectAll();
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager getRetraits() : " + e.getMessage());
            e.printStackTrace();
        }
        return retraits;
    }
    
    /**
     * Méthode en charge d'ajouter un retrait
     * @param retrait
     * @return
     * @throws BllException
     */
    public int addRetrait(Retrait retrait) throws BllException {

        try {
        	retraitDAO.insert(retrait);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager addRetrait(Retrait retrait) : " + e.getMessage());
        }
        return retrait.getArticle().getNoArticle();

    }
    
    /**
     * Méthode en charge de mettre à jour un retrait
     * @param retrait
     * @throws BllException
     */
    public void updateRetrait(Retrait retrait) throws BllException {

        try {
            retraitDAO.update(retrait);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager updateRetrait(Retrait retrait) : " + e.getMessage());
        }
    }
    
    /**
     * Méthode en charge de supprimer un retrait selon le numéro d'article
     * @param noArticle
     * @throws BllException
     */
    public void removeRetrait(int noArticle) throws BllException {

        try {
        	retraitDAO.delete(noArticle);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager removeRetrait(int noCategorie) : " + e.getMessage());
        }

    }
    
    /**
     * Méthode en charge de récupérer un retrait selon le numéro d'article
     * @param noArticle
     * @return Retrait
     * @throws BllException
     */
    public Retrait getRetrait(int noArticle) throws BllException {

    	Retrait retrait = null;
        try {
        	retrait = retraitDAO.selectById(noArticle);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager getRetrait(int noArticle) : " + e.getMessage());
        }
        return retrait;
    }

}
