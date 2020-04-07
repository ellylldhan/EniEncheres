/**
 * 
 */
package fr.eni.encheres.bll;

import java.util.logging.Logger;

import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class EnchereManager {

	 private static Logger LOGGER = MonLogger.getLogger("CategorieManager");
	    private static CategorieManager INSTANCE;
	    
	    private CategorieDAO categorieDAO = new CategorieDAOJdbcImpl();

		private CategorieManager() throws DalException {
		}
	    
	    public static CategorieManager getInstance() throws DalException {
	    	
	    	if (INSTANCE == null) {
				INSTANCE = new CategorieManager();
			}
	    	
	    	return INSTANCE;
	    }
	
}
