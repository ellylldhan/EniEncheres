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
	
	public List<Article> findByName(String nom) throws BusinessException; 
	
	/**
	 * Retourne une liste d'articles dont l'enchère est EN COURS.
	 * @param 
	 * @return Une liste d'articles
	 * @throws BusinessException
	 */
	public List<Article> getEnCours() throws BusinessException;
	
	/**
	 * Retourne une liste d'articles dont l'enchère est OUVERTE.
	 * @param 
	 * @return Une liste d'articles
	 * @throws BusinessException
	 */
	public List<Article> getOuverte() throws BusinessException;
	
	/**
	 * Retourne une liste d'articles dont l'enchère est TERMINEE.
	 * @param 
	 * @return Une liste d'articles
	 * @throws BusinessException
	 */
	public List<Article> getTerminee() throws BusinessException;

}
