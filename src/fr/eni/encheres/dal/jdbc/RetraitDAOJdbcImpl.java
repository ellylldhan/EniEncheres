/**
 * 
 */
package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAO;
import fr.eni.encheres.dal.RetraitDAO;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class RetraitDAOJdbcImpl implements RetraitDAO {
	
	private static Logger LOGGER = MonLogger.getLogger("RetraitDAOJdbcImpl");
	
	private DAO<Article> articleDAO = new ArticleDAOJdbcImpl();
	
	private static final String RQT_SELECT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article = ?";
	private static final String RQT_SELECT_ALL = "SELECT * FROM RETRAITS";
	private static final String RQT_INSERT = "INSERT INTO RETRAITS VALUES(?,?,?,?)";
	private static final String RQT_UPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
    private static final String RQT_DELETE = "DELETE RETRAITS WHERE no_article = ?";
    
    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.RetraitDAO#selectById(int)
     */
    @Override
    public Retrait selectById(int noArticle) throws DalException{

        Retrait retrait = null;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SELECT_BY_ID);
            requete.setInt(1, noArticle);
            ResultSet rs = requete.executeQuery();

            if (rs.next()) {
                retrait = itemBuilder(rs);
            }
        } catch (Exception e) {
        	LOGGER.severe("Erreur dans Retrait selectById(int noArticle) : " + e.getMessage());
        }

        return retrait;

    }
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#selectAll()
	 */
    @Override
	public List<Retrait> selectAll() throws DalException {
		
		List<Retrait> retraits = new ArrayList<Retrait>();
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL);
			
			ResultSet rs = requete.executeQuery();
				
			while (rs.next()) {
				retraits.add(itemBuilder(rs));				
			}
		} catch (Exception e) {
        	LOGGER.severe("Erreur dans Retrait selectAll() : " + e.getMessage());
		}
		
		return retraits;
	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#insert(fr.eni.encheres.bo.Retrait)
	 */
    @Override
	public void insert(Retrait retrait) throws DalException {
		
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_INSERT);
			
			requete.setInt(1, retrait.getArticle().getNoArticle());
			requete.setString(2, retrait.getRue());
			requete.setString(3, retrait.getCodePostal());
			requete.setString(4, retrait.getVille());
			
			int rs = requete.executeUpdate();
		} catch (Exception e) {
        	LOGGER.severe("Erreur dans Retrait insert(Retrait retrait) : " + e.getMessage());
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#update(fr.eni.encheres.bo.Retrait)
	 */
    @Override
	public void update(Retrait retrait) throws DalException {
		
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_UPDATE);
            requete.setString(1, retrait.getRue());
            requete.setString(2, retrait.getCodePostal());
            requete.setString(3, retrait.getVille());

            requete.setInt(4, retrait.getArticle().getNoArticle());

            requete.executeUpdate();
		} catch (Exception e) {
        	LOGGER.severe("Erreur dans Retrait update(Retrait retrait) : " + e.getMessage());
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#delete(int)
	 */
    @Override
	public void delete(int noArticle) throws DalException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_DELETE);
            requete.setInt(1, noArticle);
            requete.executeUpdate();
        } catch (Exception e) {
        	LOGGER.severe("Erreur dans Retrait delete(int noArticle) : " + e.getMessage());
        }

    }

	/**
	 * Méthode en charge de créer une instance d'objet Retrait en fonction des enregistrements récupérés en base de données
	 * @param rs
	 * @return retrait : Instance d'objet Retrait
	 * @throws SQLException
	 * @throws DalException 
	 */
	private Retrait itemBuilder(ResultSet rs) throws SQLException, DalException {
		Article article = articleDAO.selectById(rs.getInt("no_article"));
		Retrait retrait = new Retrait(article, rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
		
		return retrait;
		
	}

}