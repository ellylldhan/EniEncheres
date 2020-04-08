/**
 * 
 */
package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de contrôler les accès à la table CATEGORIES de la base de données.
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class CategorieManager {

//	private Logger logger = MonLogger.getLogger(getClass().getName());
    private static CategorieManager instance;
    
    private CategorieDAO categorieDAO = DAOFactory.getCategorieDAO();

	private CategorieManager() throws DalException {
		
	}
    
	/**
	 * Méthode en charge de retourner une instance de la classe.
	 * @return
	 * @throws DalException
	 * @throws BllException
	 */
	public static CategorieManager getInstance() throws DalException, BllException {
    	
    	if (instance == null) {
			return new CategorieManager();
		}
    	
    	return instance;
    }
    
    /**
     * Méthode en charge de retourner une liste de toutes les catégories.
     * @return
     */
    public List<Categorie> getCategories() {
    	
        StackTraceElement stack = new Throwable().getStackTrace()[0];
        String nomMethodeCourante = stack.getMethodName();
        String nomClasseCourante = stack.getClassName();
        
    	
        List<Categorie> categories = null;
        try {
        	categories = categorieDAO.selectAll();
        } catch (DalException e) {
//            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
            e.printStackTrace();
        }
        return categories;
    }
    
    /**
     * Méthode en charge d'ajouter une catégorie
     * @param categorie
     * @return
     * @throws BllException
     */
    public int addCategorie(Categorie categorie) throws BllException {
        try {
        	categorieDAO.insert(categorie);
        } catch (DalException e) {
//            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
        	e.printStackTrace();

        }
        return categorie.getNoCategorie();

    }
    
    /**
     * Méthode en charge de mettre à jour une catégorie
     * @param categorie
     * @throws BllException
     */
    public void updateCategorie(Categorie categorie) throws BllException {
       
        try {
            categorieDAO.update(categorie);
        } catch (DalException e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * Méthode en charge de supprimer une catégorie selon son id
     * @param noCategorie
     * @throws BllException
     */
    public void removeCategorie(int noCategorie) throws BllException {

        try {
            categorieDAO.delete(noCategorie);
        } catch (DalException e) {
//            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
        	e.printStackTrace();
        }

    }
    
    /**
     * Méthode en charge de récupérer une catégorie selon son id
     * @param noCategorie
     * @return
     * @throws BllException
     */
    public Categorie getCategorie(int noCategorie) throws BllException {
      
        try {
        	return categorieDAO.selectById(noCategorie);
        } catch (DalException e) {
//            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
            e.printStackTrace();
        }
        return null;
    }

}
