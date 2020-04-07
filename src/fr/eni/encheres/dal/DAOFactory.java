/**
 * 
 */
package fr.eni.encheres.dal;

import fr.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class DAOFactory {

	public static EnchereDAO getEnchereDAO()
	{
		return new EnchereDAOJdbcImpl();
	}
	
}
