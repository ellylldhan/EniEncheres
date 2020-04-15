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

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.RetraitDAO;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class RetraitDAOJdbcImpl implements RetraitDAO {
	
	private static Logger logger ;
    private static StackTraceElement stack;
    private static String nomMethodeCourante;
    private static String nomClasseCourante;
	
	private ArticleDAO articleDAO = new ArticleDAOJdbcImpl();
	
	private static final String RQT_SELECT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article = ?";
	private static final String RQT_SELECT_ALL = "SELECT * FROM RETRAITS";
	private static final String RQT_INSERT = "INSERT INTO RETRAITS VALUES(?,?,?,?)";
	private static final String RQT_UPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
    private static final String RQT_DELETE = "DELETE RETRAITS WHERE no_article = ?";
    

	public RetraitDAOJdbcImpl() {
		logger = MonLogger.getLogger(getClass().getName());

        stack = new Throwable().getStackTrace()[0];
        nomClasseCourante = stack.getClassName();
        nomMethodeCourante = stack.getMethodName();
	}
    
    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.RetraitDAO#selectById(int)
     */
    @Override
    public Retrait selectById(int noArticle) throws BusinessException{

        Retrait retrait = null;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SELECT_BY_ID);
            requete.setInt(1, noArticle);
            ResultSet rs = requete.executeQuery();

            if (rs.next()) {
                retrait = itemBuilder(rs);
            }
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return retrait;

    }
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#selectAll()
	 */
    @Override
	public List<Retrait> selectAll() throws BusinessException {
		
		List<Retrait> retraits = new ArrayList<Retrait>();
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL);
			
			ResultSet rs = requete.executeQuery();
				
			while (rs.next()) {
				retraits.add(itemBuilder(rs));				
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		
		return retraits;
	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#insert(fr.eni.encheres.bo.Retrait)
	 */
    @Override
	public void insert(Retrait retrait) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_INSERT);
			
			requete.setInt(1, retrait.getArticle().getNoArticle());
			requete.setString(2, retrait.getRue());
			requete.setString(3, retrait.getCodePostal());
			requete.setString(4, retrait.getVille());
			
			int rs = requete.executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.RetraitDAO#update(fr.eni.encheres.bo.Retrait)
	 */
    @Override
	public void update(Retrait retrait) throws BusinessException {
		
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_UPDATE);
            requete.setString(1, retrait.getRue());
            requete.setString(2, retrait.getCodePostal());
            requete.setString(3, retrait.getVille());

            requete.setInt(4, retrait.getArticle().getNoArticle());

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
	 * @see fr.eni.encheres.dal.RetraitDAO#delete(int)
	 */
    @Override
	public void delete(int noArticle) throws BusinessException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_DELETE);
            requete.setInt(1, noArticle);
            requete.executeUpdate();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
        }

    }

	/**
	 * Méthode en charge de créer une instance d'objet Retrait en fonction des enregistrements récupérés en base de données
	 * @param rs
	 * @return retrait : Instance d'objet Retrait
	 * @throws SQLException
	 * @throws DalException 
	 */
	private Retrait itemBuilder(ResultSet rs) throws SQLException, BusinessException {
		Article article = articleDAO.selectById(rs.getInt("no_article"));
		Retrait retrait = new Retrait(article, rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
		
		return retrait;
		
	}

}