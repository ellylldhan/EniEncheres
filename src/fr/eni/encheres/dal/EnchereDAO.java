/**
 * 
 */
package fr.eni.encheres.dal;



import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public interface EnchereDAO {

	/**
	 * Méthode en charge d'insérer un enregistrement dans la table Encheres
	 * @param noArticle
	 * @throws DalException
	 */
	public void create(Enchere enchere)  throws DalException ;
	
	/**
	 * Méthode en charge de récupérer l'instance d'une enchère qui a le montant le plus élevé pour l'article donné en paramètre
	 * @param noArticle
	 * @return Enchere
	 * @throws DalException
	 */
	public Enchere selectMaxEnchereByIdArticle(int idArticle)  throws DalException ;
	
	/**
	 * Méthode en charge de récupérer toutes les instances d'objet d'enchère lié à un article
	 * @param noArticle
	 * @return Liste d'Enchère
	 * @throws DalException
	 */
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws DalException ;
	
	
	/**
	 * Retourne une liste de toutes les enchères en cours de validité.
	 * @return Liste enchères non retirées dont la date de fin n'est pas dépassée.
	 * @throws DalException
	 */
	public List<Enchere> selectAllEncheresValides() throws DalException;
	
}
