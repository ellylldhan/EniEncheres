/**
 * 
 */
package fr.eni.encheres.bll;

import java.util.List;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class EnchereManager {

	 private static Logger LOGGER = MonLogger.getLogger("EnchereManager");
	    private static EnchereManager INSTANCE;
	    
	    private EnchereDAO enchereDAO= DAOFactory.getEnchereDAO();

		private EnchereManager(){
			
		}
	    
	    public static EnchereManager getInstance() {
	    	
	    	if (INSTANCE == null) {
				INSTANCE = new EnchereManager();
			}
	    	
	    	return INSTANCE;
	    }
	    
	    public List<Enchere> selectAllByIdArticle(int idArticle) {
	        List<Enchere> encheres= null;
	        try {
	        	encheres = enchereDAO.selectAllByIdArticle(idArticle);
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager selectAllByIdArticle() : " + e.getMessage());
	        }
	        return encheres;
	    }
	    
	
}
