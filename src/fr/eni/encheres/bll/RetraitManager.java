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
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class RetraitManager {
	
	private static Logger LOGGER = MonLogger.getLogger("RetraitManager");
    private static RetraitManager INSTANCE;
    
    private RetraitDAO retraitDAO = DAOFactory.getRetraitDAO();

	private RetraitManager() throws DalException {
	}
    
    public static RetraitManager getInstance() throws DalException, BllException {
    	
    	if (INSTANCE == null) {
			INSTANCE = new RetraitManager();
		}
    	
    	return INSTANCE;
    }
    
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
    
    public int addRetrait(Retrait retrait) throws BllException {

        try {
        	retraitDAO.insert(retrait);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager addRetrait(Retrait retrait) : " + e.getMessage());
        }
        return retrait.getArticle().getNoArticle();

    }
    
    public void updateRetrait(Retrait retrait) throws BllException {

        try {
            retraitDAO.update(retrait);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager updateRetrait(Retrait retrait) : " + e.getMessage());
        }
    }
    
    public void removeRetrait(int noArticle) throws BllException {

        try {
        	retraitDAO.delete(noArticle);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans RetraitManager removeRetrait(int noCategorie) : " + e.getMessage());
        }

    }
    
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
