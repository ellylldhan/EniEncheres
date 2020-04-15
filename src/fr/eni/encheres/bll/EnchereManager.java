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
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;
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
	    private UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

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

	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getBestEnchereByIdArticle(int idArticle) : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.SELECT_OBJET);
	        }
	        return encheres;
	    }
	    
	    public void create(Enchere enchere) throws BllException {
	        try {
	        	this.checkEnchere(enchere);
	        	if (enchereDAO.selectById(enchere.getArticle().getNoArticle(), enchere.getUtilisateur().getNoUtilisateur()) == null) {
	        		enchereDAO.create(enchere);
				}
	        	else {
					enchereDAO.update(enchere);
				}
	        	
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getBestEnchereByIdArticle(int idArticle) : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.INSERT_OBJET_NOTINSERT);
	        }
	    }
	    	    
	    private void checkEnchere(Enchere enchere) throws BllException, DalException {
	    	
	    	Enchere meilleurEnchere = enchereDAO.selectMustEnchereByIdArticle(enchere.getArticle().getNoArticle());
	    	int meilleurOffre = 0;
	    	if (meilleurEnchere != null) {
				meilleurOffre = meilleurEnchere.getMontant_enchere();
		    	if (meilleurEnchere.getUtilisateur().getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur()) {
					throw new BllException(CodesResultatBLL.OBJET_NOTCONFORM);
				}
			}else {
				meilleurOffre = enchere.getArticle().getPrixInitial();
			}

	    		
	    	if (enchere == null || enchere.getArticle().getNoArticle() == 0 || enchere.getMontant_enchere() <= meilleurOffre || enchere.getUtilisateur().getNoUtilisateur() == 0) {
				throw new BllException(CodesResultatBLL.OBJET_NOTCONFORM);
			}

	    	if (!enchere.getArticle().getDateDebut().isBefore(LocalDate.now()) || !enchere.getArticle().getDateFinEncheres().isAfter(LocalDate.now())) {
				throw new BllException(CodesResultatBLL.CHECK_INSERT_NOT_OK);
			}

	    }

	    /**
	     * Retourne la liste des enchères actives (ni périmée, ni retirée)
	     * @return Liste d'Enchères
	     * @throws BllException
	     */
	    public List<Enchere> getEncheresActives() throws BllException {
	        List<Enchere> encheres= null;
	        try {
	        	encheres = enchereDAO.selectAllEncheresValides();
	        	if (encheres == null ) {
					throw new BllException(CodesResultatBLL.Select_OBJET_NOTFOUND);
				}
	        } catch (DalException e) {
	            LOGGER.severe("Erreur dans EnchereManager getEncheresActives() : " + e.getMessage());
	            throw new BllException(CodesResultatBLL.SELECT_OBJET);
	        }
	        return encheres;
	    }
	    
	    public void update (Enchere enchere) throws BllException {
	    	if (enchere.getUtilisateur().checkCredit(enchere.getMontant_enchere())) {
	    		throw new BllException(CodesResultatBLL.MISSING_CREDIT);
			}
	    	try {
				Enchere meilleurEnchere = enchereDAO.selectMustEnchereByIdArticle(enchere.getArticle().getNoArticle());
				
				meilleurEnchere.getUtilisateur().setCredit(meilleurEnchere.getUtilisateur().getCredit() + meilleurEnchere.getMontant_enchere());
				enchere.getUtilisateur().setCredit(enchere.getUtilisateur().getCredit() - enchere.getMontant_enchere());
				
				this.create(enchere);
				utilisateurDAO.updateCredit(meilleurEnchere.getUtilisateur());
				utilisateurDAO.updateCredit(enchere.getUtilisateur());
				
				
			} catch (DalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
  
}
