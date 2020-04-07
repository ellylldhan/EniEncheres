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
	 * @return
	 * @throws DalException
	 */
	public void create(Enchere enchere)  throws DalException ;
	
	/**
	 * Méthode en charge de récupérer l'instance d'une enchers qui a le montant le plus élevépour l'article donné en paramètre
	 * @param noArticle
	 * @return
	 * @throws DalException
	 */
	public Enchere selectByIdArticleMustEnchere(int idArticle)  throws DalException ;
	
	/**
	 * Méthode en charge de récupérer toute less instance d'objet d'enchère lié à un article
	 * @param noArticle
	 * @return
	 * @throws DalException
	 */
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws DalException ;

	
}
