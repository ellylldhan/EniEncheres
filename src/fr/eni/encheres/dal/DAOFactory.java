/**
 * 
 */
package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.CategorieDAOJdbImpl;
import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;
import fr.eni.encheres.dal.jdbc.RetraitDAOJdbcImpl;

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
		return new CategorieDAOJdbImpl();
	}
	public static UtilisateurDAO getUtilisateurDAO()
	{
		return null;
	}
	public static ArticleDAO getArticleDAO()
	{
		return null;
	}
}
