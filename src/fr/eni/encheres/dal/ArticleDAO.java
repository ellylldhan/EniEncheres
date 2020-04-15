package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.exception.BusinessException ;

/**
 * Classe en charge de la DAO d'Article
 * 
 * @author Reno
 * @version EniEncheres - v1.0
 * @date 7 Apr 2020
 */
public interface ArticleDAO extends DAO<Article> {

//	public Article selectById(int noArticle) throws BusinessException;
//	public List<Article> selectAll() throws BusinessException;
//	public void insert(Article article) throws DalException;
//	public void update(Article article) throws BusinessException;
//	public void delete(int noArticle) throws BusinessException;
	
	public List<Article> findByName(String nom) throws BusinessException; 

}
