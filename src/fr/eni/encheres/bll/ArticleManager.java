package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe qui contrôle les accès à la table ARTICLES de la base de données.
 * 
 * @author Reno
 * @version EniEncheres - v1.0
 * @date 7 Apr 2020
 */
public class ArticleManager {

	private static Logger logger = MonLogger.getLogger("ArticleManager");

	private static ArticleManager INSTANCE;

	private ArticleDAO articleDAO = DAOFactory.getArticleDAO();

	private ArticleManager() {

	}

	/**
	 * Méthode en charge de retourner une instance de la classe.
	 * 
	 * @return
	 * @throws DalException
	 * @throws BllException
	 */
	public static ArticleManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ArticleManager();
		}

		return INSTANCE;
	}

	/**
	 * Méthode en charge de retourner une liste de tous les articles.
	 * 
	 * @return La liste de tous les articles
	 */
	public List<Article> getArticles() throws BusinessException{
		List<Article> articles = null;

		articles = articleDAO.selectAll();

		return articles;
	}

	/**
	 * Méthode en charge d'ajouter un article.
	 * 
	 * @param article Article à ajouter
	 * @return L'id de l'article ajouté
	 * @throws BllException
	 */
	public int addArticle(Article article) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		try {
			this.checkArticle(article, businessException);
			articleDAO.insert(article);
			return article.getNoArticle();

		} catch (BusinessException e) {
			logger.log(Level.SEVERE, "Erreur dans ArticleManager / addArticle(Article article) : " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Méthode en charge de mettre à jour un article.
	 * 
	 * @param article Article à mettre à jour
	 * @throws BllException
	 */
	public void updateArticle(Article article) throws BusinessException {
		articleDAO.update(article);
	}

	/**
	 * Méthode en charge de supprimer un article selon son id.
	 * 
	 * @param article Article à supprimer
	 * @throws BllException
	 */
	public void removeArticle(int noArticle) throws BusinessException {
		articleDAO.delete(noArticle);
	}

	/**
	 * Méthode en charge de retourner un article selon son id.
	 * 
	 * @param noArticle L'identifiant de l'article
	 * @return L'article en question
	 * @throws BllException
	 */
	public Article getArticle(int noArticle) throws BusinessException {

		Article article = null;
		article = articleDAO.selectById(noArticle);

		return article;
	}

	/**
	 * Méthode en charge de chercher les noms d'article contenant une certaine chaîne de
	 * caractères.
	 * 
	 * @param nom Chaîne de caractères à chercher
	 * @return Une liste d'article resultant de la recherche.
	 * @throws BllException
	 */
	public List<Article> findArticle(String nom) throws BusinessException {

		List<Article> articles = new ArrayList<>();

			articles = articleDAO.findByName(nom);

		return articles;
	}
	
	/**
	 * Méthode en charge de vérifier les informations de l'article
	 * @param article
	 * @param businessException
	 * @throws BusinessException
	 */
	private void checkArticle(Article article, BusinessException businessException) throws BusinessException {

		LocalDate dateDebut = article.getDateDebut();
		LocalDate dateFin = article.getDateFinEncheres();

		//Si la date de début de l'enchère est postérieure à la date de fin
		if (dateDebut.isAfter(dateFin)) {
			businessException.ajouterErreur(CodesResultatBLL.DATE_FIN_ENCHERE_ANTERIEURE_DATE_DEBUT);
		} else if (dateFin.isBefore(LocalDate.now())) { //Si la date de fin de l'enchère est antérieure à la date du jour
			businessException.ajouterErreur(CodesResultatBLL.DATE_FIN_ENCHERE_ANTERIEURE_DATE_DU_JOUR);
		}
		
		if (businessException.hasErreurs()) {
			throw businessException;
		}

	}
}
