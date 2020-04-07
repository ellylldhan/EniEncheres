/**
 * 
 */
package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class EnchereDAOJdbcImpl implements EnchereDAO{

	private static String RQT_SelectByIdArticleMustEnchere = "SELECT * FROM Enchere where no_article = ?";
	private static String RQT_SelectAllByIdArticle = "SELECT * FROM Enchere where no_article = ? and montant_enchere = ( select TOP 1 Max(montant_enchere) from Enchere where no_article = ?)";
	private static String RQT_INSERT = "INSERT INTO Enchere VALUES(?,?,?,?)";



	/**
	 * {@inheritDoc}
	 * @throws DalException 
	 * @see fr.eni.encheres.dal.EnchereDAO#Create(fr.eni.encheres.bo.Enchere)
	 */
	@Override
	public void create(Enchere enchere) throws DalException {
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_INSERT);
			
			requete.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
			requete.setInt(2, enchere.getArticle().getNoArticle());
			requete.setTimestamp(3, enchere.getDate_enchere());
			requete.setInt(4, enchere.getMontant_enchere());
			
			int rs = requete.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new DalException(CodesResultatDAL.INSERT_OBJET_ECHEC); 
		}
		
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectByIdArticleMustEnchere(int)
	 */
	@Override
	public Enchere selectByIdArticleMustEnchere(int idArticle)  throws DalException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectAllByIdArticle(int)
	 */
	@Override
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws DalException {
		// TODO Auto-generated method stub
		return null;
	}

}
