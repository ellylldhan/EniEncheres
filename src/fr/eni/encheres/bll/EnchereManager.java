/**
 * 
 */
package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * 
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class EnchereManager {

	private static Logger logger;
	private static StackTraceElement stack;
	private static String nomMethodeCourante;
	private static String nomClasseCourante;

	private static EnchereManager INSTANCE;

	private EnchereDAO enchereDAO = DAOFactory.getEnchereDAO();
	private ArticleManager articleManager = ArticleManager.getInstance();
	private UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

	private EnchereManager() {
		logger = MonLogger.getLogger(getClass().getName());

		stack = new Throwable().getStackTrace()[0];
		nomClasseCourante = stack.getClassName();
		nomMethodeCourante = stack.getMethodName();
	}

	public static EnchereManager getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new EnchereManager();
		}

		return INSTANCE;
	}

	public List<Enchere> getEnchereByIdArticle(int idArticle) throws BusinessException {
		List<Enchere> encheres = null;
		try {
			encheres = enchereDAO.selectAllByIdArticle(idArticle);

		} catch (BusinessException e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			throw e;
		}
		return encheres;
	}

	public Enchere getBestEnchereByIdArticle(int idArticle) throws BusinessException {
		Enchere encheres = null;
		try {
			encheres = enchereDAO.selectMustEnchereByIdArticle(idArticle);

		} catch (BusinessException e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			throw e;
		}
		return encheres;
	}

	public void create(Enchere enchere) throws BusinessException {
		BusinessException businessException = new BusinessException();
		try {
			this.checkEnchere(enchere, businessException);
			if (enchereDAO.selectById(enchere.getArticle().getNoArticle(),
					enchere.getUtilisateur().getNoUtilisateur()) == null) {
				enchereDAO.create(enchere);
			} else {
				enchereDAO.update(enchere);
			}

		} catch (BusinessException e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			throw e;
		}
	}



	public void update(Enchere enchere) throws BusinessException {
		try {
			if (enchere.getUtilisateur().checkCredit(enchere.getMontant_enchere())) {
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatBLL.MISSING_CREDIT);
				throw businessException;
			}
			Enchere meilleurEnchere = enchereDAO.selectMustEnchereByIdArticle(enchere.getArticle().getNoArticle());

			enchere.getUtilisateur().setCredit(enchere.getUtilisateur().getCredit() - enchere.getMontant_enchere());

			this.create(enchere);
			utilisateurDAO.updateCredit(enchere.getUtilisateur());
			if(meilleurEnchere != null) {
				meilleurEnchere.getUtilisateur().setCredit(meilleurEnchere.getUtilisateur().getCredit() + meilleurEnchere.getMontant_enchere());
				utilisateurDAO.updateCredit(meilleurEnchere.getUtilisateur());
			}



		} catch (BusinessException e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			throw e;
		}

	}

	private void checkEnchere(Enchere enchere, BusinessException businessException) throws BusinessException {

		Enchere meilleurEnchere = enchereDAO.selectMustEnchereByIdArticle(enchere.getArticle().getNoArticle());


		if (meilleurEnchere == null) {
			meilleurEnchere = new Enchere();
			meilleurEnchere.setMontant_enchere(enchere.getArticle().getPrixInitial());
			if (enchere.getArticle().getUtilisateur().getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur()) {
				businessException.ajouterErreur(CodesResultatBLL.USER_LAST_ENCHERIR);
			}
		}
		else if (meilleurEnchere.getUtilisateur().getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur()) {
			businessException.ajouterErreur(CodesResultatBLL.USER_LAST_ENCHERIR);
		}


		if (enchere == null || enchere.getArticle().getNoArticle() == 0 || enchere.getMontant_enchere() <= meilleurEnchere.getMontant_enchere()
				|| enchere.getUtilisateur().getNoUtilisateur() == 0) {
			businessException.ajouterErreur(CodesResultatBLL.OBJET_NOTCONFORM);
		}


		if ( !(enchere.getArticle().getDateDebut().equals(LocalDate.now())) ||  !(enchere.getArticle().getDateFinEncheres().equals(LocalDate.now())) ||
				( enchere.getArticle().getDateDebut().isAfter(LocalDate.now()) || enchere.getArticle().getDateFinEncheres().isBefore(LocalDate.now()) ) ) 
		{

			businessException.ajouterErreur(CodesResultatBLL.DATE_EXPIRE);

		}
		if (businessException.hasErreurs()) {
			throw businessException;
		}

	}

}
