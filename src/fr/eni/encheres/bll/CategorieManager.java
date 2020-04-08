/**
 * 
 */
package fr.eni.encheres.bll;

import java.util.List;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de contrôler les accès à la table CATEGORIES de la base de données.
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class CategorieManager {

    private static Logger LOGGER = MonLogger.getLogger("CategorieManager");
    private static CategorieManager INSTANCE;
    
    private CategorieDAO categorieDAO = DAOFactory.getCategorieDAO();

	private CategorieManager() throws DalException, BllException {
	}
    
	/**
	 * Méthode en charge de retourner une instance de la classe.
	 * @return
	 * @throws DalException
	 * @throws BllException
	 */
    public static CategorieManager getInstance() throws DalException, BllException {
    	
    	if (INSTANCE == null) {
			INSTANCE = new CategorieManager();
		}
    	
    	return INSTANCE;
    }
    
    /**
     * Méthode en charge de retourner une liste de toutes les catégories.
     * @return
     */
    public List<Categorie> getCategories() {
        List<Categorie> categories = null;
        try {
        	categories = categorieDAO.selectAll();
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager getCategories() : " + e.getMessage());
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
            LOGGER.severe("Erreur dans CategorieManager addCategorie(Categorie categorie) : " + e.getMessage());
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
            LOGGER.severe("Erreur dans CategorieManager updateCategorie(Categorie categorie) : " + e.getMessage());
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
            LOGGER.severe("Erreur dans CategorieManager removeCategorie(int noCategorie) : " + e.getMessage());
        }

    }
    
    /**
     * Méthode en charge de récupérer une catégorie selon son id
     * @param noCategorie
     * @return
     * @throws BllException
     */
    public Categorie getCategorie(int noCategorie) throws BllException {

    	Categorie categorie = null;
        try {
        	categorie = categorieDAO.selectById(noCategorie);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans CategorieManager getCategorie(int noCategorie) : " + e.getMessage());
        }
        return categorie;
    }

}
