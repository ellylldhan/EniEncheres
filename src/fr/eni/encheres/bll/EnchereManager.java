/**
 * 
 */
package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.logging.Logger;

import org.apache.jasper.tagplugins.jstl.core.If;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.CodesResultatBLL;
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
	    
	    private EnchereDAO enchereDAO = DAOFactory.getEnchereDAO();
	    private ArticleManager articleManager = ArticleManager.getInstance();

		private EnchereManager(){
			
		}
	    
	    public static EnchereManager getInstance() {
	    	
	    	if (INSTANCE == null) {
				INSTANCE = new EnchereManager();
			}
	    	
	    	return INSTANCE;
	    }
	    
	    public List<Enchere> getEnchereByIdArticle(int idArticle) throws BllException {
	        List<Enchere> encheres= null;
	        try {
	        	encheres = enchereDAO.selectAllByIdArticle(idArticle);
	        	if (encheres == null ) {
					throw new BllException(CodesResultatBLL.Select_OBJET_NOTFOUND);
				}
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getEnchereByIdArticle(int idArticle) : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.SELECT_OBJET);
	        }
	        return encheres;
	    }
	    
	    public Enchere getBestEnchereByIdArticle(int idArticle) throws BllException {
	        Enchere encheres = null;
	        try {
	        	encheres = enchereDAO.selectMustEnchereByIdArticle(idArticle);
	        	if (encheres == null ) {
					throw new BllException(CodesResultatBLL.Select_OBJET_NOTFOUND);
				}
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getBestEnchereByIdArticle(int idArticle) : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.SELECT_OBJET);
	        }
	        return encheres;
	    }
	    
	    public void create(Enchere enchere) throws BllException {

	    	this.checkEnchere(enchere);
	    	
	        try {
	        	enchereDAO.create(enchere);
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getBestEnchereByIdArticle(int idArticle) : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.INSERT_OBJET_NOTINSERT);
	        }
	    }
	    
	    public void update(Enchere enchere) throws BllException {
	    	this.checkEnchere(enchere);
	        try {
	        	enchereDAO.create(enchere);
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getBestEnchereByIdArticle(int idArticle) : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.UPDATE_OBJET_NOTUPDATE);
	        }
	    }
	    
	    private void checkEnchere(Enchere enchere) throws BllException {
	    	if (enchere == null || enchere.getArticle().getNoArticle() == 0  || enchere.getMontant_enchere() <= 0 || enchere.getUtilisateur().getNoUtilisateur() == 0) {
				throw new BllException(CodesResultatBLL.OBJET_NOTCONFORM);
			}
	    	Article article =  articleManager.getArticle(enchere.getArticle().getNoArticle());
	    	if (!article.getDateDebut().isAfter(LocalDate.now()) || !article.getDateFinEncheres().isBefore(LocalDate.now())) {
				throw new BllException(CodesResultatBLL.CHECK_INSERT_OK);
			}
	    }	    
}
