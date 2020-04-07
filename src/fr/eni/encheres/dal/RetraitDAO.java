/**
 * 
 */
package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public interface RetraitDAO {
	
	/**
	 * Méthode en charge de récupérer une instance d'objet Retrait en fonction de numéro article
	 * @param noArticle
	 * @return
	 * @throws DalException
	 */
	public Retrait selectById(int noArticle) throws DalException;
	
	/**
	 * Méthode en charge de récupérer tous les enregistrements de la table RETRAITS
	 * @return
	 * @throws DalException
	 */
	public List<Retrait> selectAll() throws DalException;
	
	/**
	 * Méthode en charge d'insérer un enregistrement dans la table RETRAITS
	 * @param retrait
	 * @throws DalException
	 */
	public void insert(Retrait retrait) throws DalException;
	
	/**
	 * Méthode en charge de modifier un enregistrement de RETRAITS
	 * @param retrait
	 * @throws DalException
	 */
	public void update(Retrait retrait) throws DalException;
	
	/**
	 * Méthode en charge de supprimer un enregistrement de la table RETRAITS
	 * @param noArticle
	 * @throws DalException
	 */
	public void delete(int noArticle) throws DalException;

}
