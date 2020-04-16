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

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class EnchereDAOJdbcImpl implements EnchereDAO{

	private static final String RQT_SelectAllByIdArticle = "SELECT  [no_utilisateur], [no_article], [date_enchere], [montant_enchere] FROM Encheres where no_article = ?";
	private static final String RQT_SelectByIdArticleMustEnchere = "SELECT  [no_utilisateur], [no_article], [date_enchere], [montant_enchere] FROM Encheres where no_article = ? and montant_enchere = ( select TOP 1 Max(montant_enchere) from Encheres where no_article = ?)   order by date_enchere asc";
	private static final String RQT_INSERT = "INSERT INTO Encheres VALUES(?,?,?,?)";
	private static final String RQT_UPDATE = "UPDATE [dbo].[ENCHERES] SET [date_enchere] = ? ,[montant_enchere] = ? WHERE no_utilisateur = ? AND no_article = ?  ";
	private static final String RQT_SelectById = "SELECT  [no_utilisateur], [no_article], [date_enchere], [montant_enchere] FROM Encheres WHERE no_utilisateur = ? AND no_article = ?";
	private static final String RQT_SELECT_ALL_ENCHERES_ACTIVES = "SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere " + 
			"FROM ENCHERES e JOIN ARTICLES_VENDUS a ON a.no_article = e.no_article " + 
			"WHERE a.date_fin_encheres >= getdate()";

	private static Logger logger ;
    private static StackTraceElement stack;
    private static String nomMethodeCourante;
    private static String nomClasseCourante;
	
	
	private static UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
	private static ArticleDAO articleDAO = DAOFactory.getArticleDAO();

	public EnchereDAOJdbcImpl() {
		logger = MonLogger.getLogger(getClass().getName());

        stack = new Throwable().getStackTrace()[0];
        nomClasseCourante = stack.getClassName();
        nomMethodeCourante = stack.getMethodName();
	}
	
	/**
	 * {@inheritDoc}
	 * @throws DalException 
	 * @see fr.eni.encheres.dal.EnchereDAO#Create(fr.eni.encheres.bo.Enchere)
	 */
	@Override
	public void create(Enchere enchere) throws BusinessException {
		DAOFactory.getUtilisateurDAO();
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_INSERT);
			
			requete.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
			requete.setInt(2, enchere.getArticle().getNoArticle());
			requete.setTimestamp(3, enchere.getDate_enchere());
			requete.setInt(4, enchere.getMontant_enchere());
			
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
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectByIdArticleMustEnchere(int)
	 */
	@Override
	public Enchere selectMustEnchereByIdArticle(int idArticle)  throws BusinessException {
		Enchere enchere= null;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SelectByIdArticleMustEnchere);
            requete.setInt(1, idArticle);
            requete.setInt(2, idArticle);
            ResultSet rs = requete.executeQuery();

            if (rs.next()) {
            	enchere = itemBuilder(rs);
            }
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return enchere;

	}
	
	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectAllByIdArticle(int)
	 */
	@Override
	public List<Enchere> selectAllByIdArticle(int idArticle)  throws BusinessException {
		List<Enchere> encheres = new ArrayList<Enchere>();

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SelectAllByIdArticle);

            ResultSet rs = requete.executeQuery();

            while (rs.next()) {
                encheres.add(itemBuilder(rs));
            }
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return encheres;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.EnchereDAO#update(fr.eni.encheres.bo.Enchere)
	 */
	public void update(Enchere enchere) throws BusinessException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_UPDATE);
            requete.setTimestamp(1, enchere.getDate_enchere());
            requete.setInt(2, enchere.getMontant_enchere());
            requete.setInt(3, enchere.getUtilisateur().getNoUtilisateur());
            requete.setInt(4, enchere.getArticle().getNoArticle());

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
	 * @see fr.eni.encheres.dal.EnchereDAO#selectById(int, int)
	 */
	@Override
	public Enchere selectById(int idArticle, int idUtilisateur) throws BusinessException {
		Enchere enchere= null;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SelectById);
            requete.setInt(1, idUtilisateur);
            requete.setInt(2, idArticle);
            ResultSet rs = requete.executeQuery();

            if (rs.next()) {
            	enchere = itemBuilder(rs);
            }
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return enchere;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectAllEncheresCourantes(int)
	 */
	public List<Enchere> selectAllEncheresValides() throws BusinessException {
		List<Enchere> encheres = new ArrayList<Enchere>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL_ENCHERES_ACTIVES);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				encheres.add(itemBuilder(rs));
			}
		} catch (SQLException ex) {
			// Exception provisoire pour mieux comprendre une erreur (@Reno)
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, ex.getMessage()});
			System.out.println(ex.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		} 

		return encheres;
	}
	
	/**
	 * Méthode en charge de créer une instance d'objet Enchere en fonction des enregistrements récupérés en base de données
	 * @param rs
	 * @return categorie : Instance d'objet Categorie
	 * @throws SQLException
	 * @throws DalException 
	 */
	private Enchere itemBuilder(ResultSet rs) throws SQLException,BusinessException {
		
		Enchere enchere = new Enchere(
				utilisateurDAO.selectById(rs.getInt("no_utilisateur")), 
				articleDAO.selectById(rs.getInt("no_article")),
				rs.getTimestamp("date_enchere"),
				rs.getInt("montant_enchere"));
		
		return enchere;
		
	}
}
