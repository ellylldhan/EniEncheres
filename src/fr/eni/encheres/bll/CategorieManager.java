/**
 * 
 */
package fr.eni.encheres.bll;

import java.util.List;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbImpl;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class CategorieManager {

    private static Logger LOGGER = MonLogger.getLogger("GarageManager");
    private static CategorieManager INSTANCE;
    
    private CategorieDAO categorieDAO = new CategorieDAOJdbImpl();

	private CategorieManager() throws DalException {
	}
    
    public static CategorieManager getInstance() throws DalException {
    	
    	if (INSTANCE == null) {
			INSTANCE = new CategorieManager();
		}
    	
    	return INSTANCE;
    }
    
    public List<Categorie> getCategories() {
        List<Categorie> categories = null;
        try {
        	categories = categorieDAO.selectAll();
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager getCategories() : " + e.getMessage());
        }
        return categories;
    }
    
    public int addCategorie(Categorie categorie) { //TODO throws BLLException

        try {
        	categorieDAO.insert(categorie);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager addCategorie(Categorie categorie) : " + e.getMessage());
        }
        return categorie.getNoCategorie();

    }
    
    public void updateCategorie(Categorie categorie) { //TODO throws BLLException

        try {
            categorieDAO.update(categorie);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager updateCategorie(Categorie categorie) : " + e.getMessage());
        }
    }
    
    public void removeCategorie(int noCategorie) { //TODO throws BLLException

        try {
            categorieDAO.delete(noCategorie);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager removeCategorie(int noCategorie) : " + e.getMessage());
        }

    }
    
    public Categorie getCategorie(int noCategorie) { //TODO throws BLLException

    	Categorie categorie = null;
        try {
        	categorie = categorieDAO.selectById(noCategorie);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager getCategorie(int noCategorie) : " + e.getMessage());
        }
        return categorie;
    }

}
