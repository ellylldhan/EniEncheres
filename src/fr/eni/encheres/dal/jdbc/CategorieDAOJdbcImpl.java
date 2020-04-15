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
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class CategorieDAOJdbcImpl implements CategorieDAO {
	
	private static Logger logger ;
    private static StackTraceElement stack;
    private static String nomMethodeCourante;
    private static String nomClasseCourante;
    
	private static final String RQT_SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = ?";
	private static final String RQT_SELECT_ALL = "SELECT * FROM CATEGORIES";
	private static final String RQT_INSERT = "INSERT INTO CATEGORIES VALUES(?)";
	private static final String RQT_UPDATE = "UPDATE CATEGORIES SET libelle = ? WHERE no_categorie = ?";
	private static final String RQT_DELETE = "DELETE CATEGORIES WHERE no_categorie = ?";
	
	
	public CategorieDAOJdbcImpl() {
        logger = MonLogger.getLogger(getClass().getName());

        stack = new Throwable().getStackTrace()[0];
        nomClasseCourante = stack.getClassName();
        nomMethodeCourante = stack.getMethodName();
	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.CategorieDAO#selectById(int)
	 */
	@Override
	public Categorie selectById(int noCategorie) throws BusinessException {

        Categorie categorie = null;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SELECT_BY_ID);
            requete.setInt(1, noCategorie);
            ResultSet rs = requete.executeQuery();

            if (rs.next()) {
                categorie = itemBuilder(rs);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return categorie;

    }
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.CategorieDAO#selectAll()
	 */
	@Override
	public List<Categorie> selectAll() throws BusinessException {

        List<Categorie> categories = new ArrayList<Categorie>();

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL);

            ResultSet rs = requete.executeQuery();

            while (rs.next()) {
                categories.add(itemBuilder(rs));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return categories;

    }
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.CategorieDAO#insert(fr.eni.encheres.bo.Categorie)
	 */
	@Override
	public void insert(Categorie categorie) throws BusinessException {

        int nbLignes = 0;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            requete.setString(1, categorie.getLibelle());

            nbLignes = requete.executeUpdate();

            ResultSet rs = requete.getGeneratedKeys();

            if (rs.next()) {
                categorie.setNoCategorie(rs.getInt(1));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
        }

    }
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.CategorieDAO#update(fr.eni.encheres.bo.Categorie)
	 */
	@Override
	public void update(Categorie categorie) throws BusinessException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_UPDATE);
            requete.setString(1, categorie.getLibelle());

            requete.setInt(2, categorie.getNoCategorie());

            requete.executeUpdate();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
        }

    }
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.CategorieDAO#delete(int)
	 */
	@Override
	public void delete(int noCategorie) throws BusinessException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_DELETE);
            requete.setInt(1, noCategorie);
            requete.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

    }
	
	/**
	 * Méthode en charge de créer une instance d'objet Categorie en fonction des enregistrements récupérés en base de données
	 * @param rs
	 * @return categorie : Instance d'objet Categorie
	 * @throws SQLException
	 */
	private Categorie itemBuilder(ResultSet rs) throws SQLException {
		
		Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
		
		return categorie;
		
	}

}
