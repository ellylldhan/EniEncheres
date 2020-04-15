/**
 * 
 */
package fr.eni.encheres.dal;



import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.exception.BusinessException;

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
	 * @throws BusinessException
	 */
	public void create(Enchere enchere)  throws BusinessException ;
	
	/**
	 * Méthode en charge de récupérer l'instance d'une enchers qui a le montant le plus élevépour l'article donné en paramètre
	 * @param idArticle
	 * @return
	 * @throws BusinessException
	 */
	public Enchere selectMustEnchereByIdArticle(int idArticle)  throws BusinessException ;
	
	/**
	 * Méthode en charge de récupérer toute les instances d'objet d'enchère lié à un article
	 * @param idArticle
	 * @return
	 * @throws BusinessException
	 */
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws BusinessException ;
	
	
	/**
	 * Méthode en charge de récupérer l'instance d'objet d'enchère lié à l'article et l'utilisateur
	 * @param idArticle,idUtilisateur
	 * @return
	 * @throws BusinessException
	 */
	public Enchere selectById(int idArticle,int idUtilisateur)  throws BusinessException ;
	
	/**
	 * Méthode en charge de mettre à jour une enchère 
	 * @param enchere
	 * @return
	 * @throws BusinessException
	 */
	public void update(Enchere enchere) throws BusinessException;

	/**
	 * Retourne la liste de toutes les enchères en cours de validité.
	 * @return Liste enchères non retirées dont la date de fin n'est pas dépassée.
	 * @throws BusinessException
	 */
	public List<Enchere> selectAllEncheresValides() throws BusinessException;
	
	

}
