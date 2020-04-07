/**
 * 
 */
package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.jdbc.ArticleDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.CategorieDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.RetraitDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public abstract class DAOFactory {

	public static EnchereDAO getEnchereDAO()
	{
		return new EnchereDAOJdbcImpl();
	}
	
	public static RetraitDAO getRetraitDAO()
	{
		return new RetraitDAOJdbcImpl();
	}
	
	public static CategorieDAO getCategorieDAO()
	{
		return new CategorieDAOJdbcImpl();
	}
	public static UtilisateurDAO getUtilisateurDAO()
	{
		return new UtilisateurDAOJdbcImpl();
	}
	public static DAO<Article> getArticleDAO()
	{
		return new ArticleDAOJdbcImpl();
	}
}
