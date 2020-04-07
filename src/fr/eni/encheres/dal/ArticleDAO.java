package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de la DAO d'Article
 * 
 * @author Reno
 * @version EniEncheres - v1.0
 * @date 7 Apr 2020
 */
public interface ArticleDAO {

	/**
	 * Retourne l'Article de la table ARTICLES_VENDUS correspondant à un numéro d'id.
	 * 
	 * @param noArticle
	 * @return
	 * @throws DalException
	 */
	public Article selectById(int noArticle) throws DalException;

	/**
	 * Retourne tous les Articles de la table ARTICLES_VENDUS.
	 * 
	 * @return
	 * @throws DalException
	 */
	public List<Article> selectAll() throws DalException;

	/**
	 * Insère un article dans la table ARTICLES_VENDUS.
	 * @param article
	 * @throws DalException
	 */
	public void insert(Article article) throws DalException;

	/**
	 * Met à jour le prix de vente d'un article dans la table ARTICLES_VENDUS.
	 * @param article
	 * @throws DalException
	 */
	public void update(Article article) throws DalException;

	/**
	 * Suppression d'un Article de la table ARTICLES_VENDUS.
	 * Méthode en charge de
	 * @param noArticle
	 * @throws DalException
	 */
	public void delete(int noArticle) throws DalException;

}
