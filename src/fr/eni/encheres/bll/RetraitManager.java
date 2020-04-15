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
import fr.eni.encheres.exception.BusinessException;
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
    public List<Retrait> getRetraits()throws BusinessException {
        List<Retrait> retraits = null;

        	retraits = retraitDAO.selectAll();
       
        return retraits;
    }
    
    /**
     * Méthode en charge d'ajouter un retrait
     * @param retrait
     * @return
     * @throws BllException
     */
    public int addRetrait(Retrait retrait) throws BusinessException {


        	retraitDAO.insert(retrait);

        return retrait.getArticle().getNoArticle();

    }
    
    /**
     * Méthode en charge de mettre à jour un retrait
     * @param retrait
     * @throws BllException
     */
    public void updateRetrait(Retrait retrait) throws BusinessException {


            retraitDAO.update(retrait);

    }
    
    /**
     * Méthode en charge de supprimer un retrait selon le numéro d'article
     * @param noArticle
     * @throws BllException
     */
    public void removeRetrait(int noArticle) throws BusinessException {


        	retraitDAO.delete(noArticle);


    }
    
    /**
     * Méthode en charge de récupérer un retrait selon le numéro d'article
     * @param noArticle
     * @return
     * @throws BllException
     */
    public Retrait getRetrait(int noArticle) throws BusinessException {

    	Retrait retrait = null;

        	retrait = retraitDAO.selectById(noArticle);

        return retrait;
    }

}
