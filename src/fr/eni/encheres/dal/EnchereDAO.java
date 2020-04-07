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

	public void create(Enchere enchere)  throws DalException ;
	public Enchere selectByIdArticleMustEnchere(int idArticle)  throws DalException ;
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws DalException ;

	
}
