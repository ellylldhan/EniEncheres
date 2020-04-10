package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de la DAO d'Article
 * 
 * @author Reno
 * @version EniEncheres - v1.0
 * @date 7 Apr 2020
 */
public interface ArticleDAO extends DAO<Article> {

//	public Article selectById(int noArticle) throws DalException;
//	public List<Article> selectAll() throws DalException;
//	public void insert(Article article) throws DalException;
//	public void update(Article article) throws DalException;
//	public void delete(int noArticle) throws DalException;
	
	/**
	 * Retourne une liste d'articles dont le nom contient la String passée en argument.
	 * @param nom String recherchée dans le nom de l'article
	 * @return List<Article>
	 * @throws DalException
	 */
	public List<Article> findByName(String nom) throws DalException;


	


}
