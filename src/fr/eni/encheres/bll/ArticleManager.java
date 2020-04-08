package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BllException;
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

	private static Logger LOGGER = MonLogger.getLogger("ArticleManager");

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
	public List<Article> getArticles() {
		List<Article> articles = null;
		try {
			articles = articleDAO.selectAll();
		} catch (DalException e) {
			LOGGER.severe("Erreur dans ArticleManager getArticles() : " + e.getMessage());
			e.printStackTrace();
		}
		return articles;
	}

	/**
	 * Méthode en charge d'ajouter un article.
	 * 
	 * @param article Article à ajouter
	 * @return L'id de l'article ajouté
	 * @throws BllException
	 */
	public int addArticle(Article article) throws BllException {

		try {
			articleDAO.insert(article);
		} catch (DalException e) {
			LOGGER.severe("Erreur dans ArticleManager addArticle(Article article) : " + e.getMessage());
		}
		return article.getNoArticle();

	}

	/**
	 * Méthode en charge de mettre à jour un article.
	 * 
	 * @param article Article à mettre à jour
	 * @throws BllException
	 */
	public void updateArticle(Article article) throws BllException {

		try {
			articleDAO.update(article);
		} catch (DalException e) {
			LOGGER.severe("Erreur dans ArticleManager updateArticle(Article article) : " + e.getMessage());
		}
	}

	/**
	 * Méthode en charge de supprimer un article selon son id.
	 * 
	 * @param article Article à supprimer
	 * @throws BllException
	 */
	public void removeArticle(int noArticle) throws BllException {

		try {
			articleDAO.delete(noArticle);
		} catch (DalException e) {
			LOGGER.severe("Erreur dans ArticleManager removeArticle(int noCategorie) : " + e.getMessage());
		}

	}

	/**
	 * Méthode en charge de retourner un article selon son id.
	 * 
	 * @param noArticle L'identifiant de l'article
	 * @return L'article en question
	 * @throws BllException
	 */
	public Article getArticle(int noArticle) throws BllException {

		Article article = null;
		try {
			article = articleDAO.selectById(noArticle);
		} catch (DalException e) {
			LOGGER.severe("Erreur dans ArticleManager getArticle(int noArticle) : " + e.getMessage());
		}
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
	public List<Article> findArticle(String nom) throws BllException {

		List<Article> articles = new ArrayList<>();
		try {
			articles = articleDAO.findByName(nom);
		} catch (DalException e) {
			LOGGER.severe("Erreur dans ArticleManager getArticle(int noArticle) : " + e.getMessage());
		}
		return articles;
	}
}
