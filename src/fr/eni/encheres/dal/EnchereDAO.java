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
	 * @param enchere
	 * @return
	 * @throws DalException
	 */
	public void create(Enchere enchere)  throws DalException ;
	
	/**
	 * Méthode en charge de récupérer l'instance d'une enchers qui a le montant le plus élevépour l'article donné en paramètre
	 * @param idArticle
	 * @return
	 * @throws DalException
	 */
	public Enchere selectMustEnchereByIdArticle(int idArticle)  throws DalException ;
	
	/**
	 * Méthode en charge de récupérer toute les instances d'objet d'enchère lié à un article
	 * @param idArticle
	 * @return
	 * @throws DalException
	 */
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws DalException ;
	
	
	/**
	 * Méthode en charge de récupérer l'instance d'objet d'enchère lié à l'article et l'utilisateur
	 * @param idArticle,idUtilisateur
	 * @return
	 * @throws DalException
	 */
	public Enchere selectById(int idArticle,int idUtilisateur)  throws DalException ;
	
	/**
	 * Méthode en charge de mêttre à jour une enchère 
	 * @param enchere
	 * @return
	 * @throws DalException
	 */
	public void update(Enchere enchere) throws DalException;
}
