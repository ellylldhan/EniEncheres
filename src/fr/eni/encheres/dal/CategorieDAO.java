/**
 * 
 */
package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public interface CategorieDAO {
	/**
	 * Méthode en charge de récupérer une instance d'objet Categorie en fonction de son numéro
	 * @param noCategorie
	 * @return
	 * @throws DalException
	 */
	public Categorie selectById(int noCategorie) throws DalException;
	
	/**
	 * Méthode en charge de récupérer tous les enregistrements de la table CATEGORIES
	 * @return
	 * @throws DalException
	 */
	public List<Categorie> selectAll() throws DalException;
	
	/**
	 * Méthode en charge d'insérer un enregistrement dans la table CATEGORIES
	 * @param categorie
	 * @throws DalException
	 */
	public void insert(Categorie categorie) throws DalException;
	
	/**
	 * Méthode en charge de modifier un enregistrement de CATEGORIES
	 * @param categorie
	 * @throws DalException
	 */
	public void update(Categorie categorie) throws DalException;
	
	/**
	 * Méthode en charge de supprimer un enregistrement de la table CATEGORIES
	 * @param noCategorie
	 * @throws DalException
	 */
	public void delete(int noCategorie) throws DalException;

}
