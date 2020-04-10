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

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * 
 * @author thomas
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class EnchereDAOJdbcImpl implements EnchereDAO {

	private static final String RQT_SELECT_ALL_BY_ARTICLE_ID = "SELECT  [no_utilisateur], [no_article], [date_enchere], [montant_enchere] FROM Encheres where no_article = ?";
	private static final String RQT_SELECT_MAX_ENCHERE_BY_ARTICLE_ID = "SELECT  [no_utilisateur], [no_article], [date_enchere], [montant_enchere] FROM Encheres where no_article = ? and montant_enchere = ( select TOP 1 Max(montant_enchere) from Encheres where no_article = ?)";
	private static final String RQT_INSERT = "INSERT INTO Encheres VALUES(?,?,?,?)";
	private static final String RQT_UPDATE = "UPDATE [dbo].[ENCHERES] SET [date_enchere] = ? ,[montant_enchere] = ? WHERE no_utilisateur = ? AND no_article = ?  ";
	private static final String RQT_SELECT_ALL_ENCHERES_ACTIVES = "select a.no_article from ARTICLES_VENDUS a "
			+ "join encheres e on e.no_article = a.no_article "
			+ "where a.date_fin_encheres < GETDATE() "
			+ "and a.no_article not in (select no_article from RETRAITS)";
	
	private static Logger LOGGER = MonLogger.getLogger("EnchereDAOJdbcImpl");

	private static UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();
	private static ArticleDAO articleDAO = DAOFactory.getArticleDAO();

	/**
	 * {@inheritDoc}
	 * 
	 * @throws DalException
	 * @see fr.eni.encheres.dal.EnchereDAO#Create(fr.eni.encheres.bo.Enchere)
	 */
	@Override
	public void create(Enchere enchere) throws DalException {
		DAOFactory.getUtilisateurDAO();
		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_INSERT);

			requete.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
			requete.setInt(2, enchere.getArticle().getNoArticle());
			requete.setTimestamp(3, enchere.getDate_enchere());
			requete.setInt(4, enchere.getMontant_enchere());

			int rs = requete.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			LOGGER.severe("Erreur dans Enchere create(Enchere enchere) : " + e.getMessage());
			throw new DalException(CodesResultatDAL.INSERT_OBJET_ECHEC);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectByIdArticleMustEnchere(int)
	 */
	@Override
	public Enchere selectMaxEnchereByIdArticle(int idArticle) throws DalException {
		Enchere enchere = null;

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_MAX_ENCHERE_BY_ARTICLE_ID);
			requete.setInt(1, idArticle);
			requete.setInt(2, idArticle);
			ResultSet rs = requete.executeQuery();

			if (rs.next()) {
				enchere = itemBuilder(rs);
			}
		} catch (Exception e) {
			LOGGER.severe("Erreur dans Enchere selectByIdArticleMustEnchere(int idArticle) : " + e.getMessage());
			throw new DalException(CodesResultatDAL.SELECT_OBJET_ECHEC);
		}

		return enchere;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.EnchereDAO#SelectAllByIdArticle(int)
	 */
	@Override
	public List<Enchere> selectAllByIdArticle(int idArticle) throws DalException {
		List<Enchere> encheres = new ArrayList<Enchere>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL_BY_ARTICLE_ID);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				encheres.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			LOGGER.severe("Erreur dans Enchere selectAllByIdArticle(int idArticle) : " + e.getMessage());
			throw new DalException(CodesResultatDAL.SELECT_OBJET_ECHEC);
		}

		return encheres;
	}

	public List<Enchere> selectAllEncheresCourantes() throws DalException {
		List<Enchere> encheres = new ArrayList<Enchere>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL_BY_ARTICLE_ID);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				encheres.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			LOGGER.severe("Erreur dans Enchere selectAllByIdArticle(int idArticle) : " + e.getMessage());
			throw new DalException(CodesResultatDAL.SELECT_OBJET_ECHEC);
		}

		return encheres;
	}

	public void update(Enchere enchere) throws DalException {

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_UPDATE);
			requete.setTimestamp(1, enchere.getDate_enchere());
			requete.setInt(2, enchere.getMontant_enchere());
			requete.setInt(3, enchere.getUtilisateur().getNoUtilisateur());
			requete.setInt(4, enchere.getArticle().getNoArticle());

			requete.executeUpdate();

		} catch (Exception e) {
			LOGGER.severe("Erreur dans Enchere update(Enchere enchere) : " + e.getMessage());
			throw new DalException(CodesResultatDAL.UPDATE_OBJET_ECHEC);
		}

	}

	/**
	 * Retourne TRUE si un article est en vente et n'est pas retiré (= enchère en
	 * cours)
	 * 
	 * @param enchere Enchere
	 * @return boolean
	 * @throws DalException
	 * @throws BllException
	 */
	private boolean isCourante(Enchere enchere) throws DalException, BllException {
		boolean isVendu = articleDAO.isVendu(enchere.getArticle());
		boolean isRetire = articleDAO.isRetire(enchere.getArticle());
		return (!isVendu && !isRetire);
	}

	/**
	 * Méthode en charge de créer une instance d'objet Enchere en fonction des
	 * enregistrements récupérés en base de données
	 * 
	 * @param rs
	 * @return categorie : Instance d'objet Categorie
	 * @throws SQLException
	 * @throws DalException
	 */
	private Enchere itemBuilder(ResultSet rs) throws SQLException, DalException {

		Enchere enchere = new Enchere(utilisateurDAO.selectById(rs.getInt("no_utilisateur")),
				articleDAO.selectById(rs.getInt("no_article")), rs.getTimestamp("date_enchere"),
				rs.getInt("montant_enchere"));

		return enchere;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.EnchereDAO#isActive(fr.eni.encheres.bo.Enchere)
	 */
	@Override
	public boolean isActive(Enchere enchere) throws DalException {
		//
		return false;
	}

}
