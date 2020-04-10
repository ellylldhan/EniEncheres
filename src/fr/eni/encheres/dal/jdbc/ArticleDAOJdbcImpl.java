/**
 * 
 */
package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.DateFormatter;

import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

/**
 * Classe en charge de
 * 
 * @author Reno
 * @version EniEncheres - v1.0
 * @date 7 Apr 2020
 */
public class ArticleDAOJdbcImpl implements ArticleDAO {

//	private static Logger LOGGER = MonLogger.getLogger("ArticleDAOJdbcImpl");
	private static Logger logger;
	private static StackTraceElement stack;
	private static String nomMethodeCourante;
	private static String nomClasseCourante;

	private static String RQT_SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = ?";
	private static String RQT_SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
	private static String RQT_INSERT = "INSERT INTO ARTICLES_VENDUS "
			+ "(no_article, nom_article, description, date_debut_encheres, date_fin_encheres,"
			+ " prix_initial, no_utilisateur, no_categorie) VALUES(?,?,?,?,?,?,?,?)";
	private static String RQT_UPDATE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
	private static String RQT_DELETE = "DELETE ARTICLES_VENDUS WHERE no_article = ?";
	private static String RQT_FIND = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ?";

	/**
	 * Constructeur
	 */
	public ArticleDAOJdbcImpl() {
		logger = MonLogger.getLogger(getClass().getName());

		stack = new Throwable().getStackTrace()[0];
		nomClasseCourante = stack.getClassName();
		nomMethodeCourante = stack.getMethodName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#selectById(int)
	 */
	@Override
	public Article selectById(int id) throws DalException {
		Article item = null;

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_BY_ID);

			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();

			if (rs.next()) {
				item = itemBuilder(rs);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
		}
		return item;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#selectAll()
	 */
	@Override
	public List<Article> selectAll() throws DalException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#findByName(java.lang.String)
	 */
	@Override
	public List<Article> findByName(String nom) throws DalException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_FIND);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#insert(fr.eni.encheres.bo.Article)
	 */
	@Override
	public void insert(Article article) throws DalException {
		// TODO: nbLignesModifiees : supprimer, ou ajouter return ?
		int nbLignesModifiees = 0;

		try (Connection conn = ConnectionProvider.getConnection()) {
			PreparedStatement requete = conn.prepareStatement(RQT_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

			int index = 1;
			requete.setString(index++, article.getNomArticle());
			requete.setString(index++, article.getDescription());
			requete.setDate(index++, Date.valueOf(article.getDateDebut()));
			requete.setDate(index++, Date.valueOf(article.getDateFinEncheres()));
			requete.setInt(index++, article.getPrixInitial());
			requete.setInt(index++, article.getPrixVente());
			requete.setInt(index++, article.getUtilisateur().getNoUtilisateur());
			requete.setInt(index++, article.getCategorie().getNoCategorie());

			nbLignesModifiees = requete.executeUpdate();
			ResultSet rs = requete.getGeneratedKeys();

			if (rs.next()) {
				article.setNoArticle(rs.getInt(1));
			}

		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, ex.getMessage() });
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#update(fr.eni.encheres.bo.Article)
	 */
	@Override
	public void update(Article article) throws DalException {
		try (Connection conn = ConnectionProvider.getConnection()) {
			PreparedStatement requete = conn.prepareStatement(RQT_UPDATE_PRIX_VENTE);

			requete.setInt(1, article.getPrixVente());
			requete.setInt(2, article.getNoArticle());

			requete.executeUpdate();

		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, ex.getMessage() });
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#delete(int)
	 */
	@Override
	public void delete(int id) throws DalException {
		try (Connection conn = ConnectionProvider.getConnection()) {
			PreparedStatement requete = conn.prepareStatement(RQT_DELETE);

			requete.setInt(1, id);

			requete.executeUpdate();

		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, ex.getMessage() });
		}

	}

	/**
	 * Méthode en charge de construire un objet Article avec tous ses attributs.
	 * 
	 * @param rs ResultSet
	 * @return Article
	 * @throws SQLException
	 * @throws DalException
	 */
	private Article itemBuilder(ResultSet rs) throws SQLException, DalException {

		Utilisateur utilisateur = DAOFactory.getUtilisateurDAO().selectById(rs.getInt("no_utilisateur"));
		Categorie categorie = DAOFactory.getCategorieDAO().selectById(rs.getInt("no_categorie"));

		return new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),
				rs.getDate("date_debut_encheres").toLocalDate(), rs.getDate("date_fin_encheres").toLocalDate(),
				rs.getInt("prix_initial"), rs.getInt("prix_vente"), utilisateur, categorie);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#isRetire(fr.eni.encheres.bo.Article)
	 */
	@Override
	public boolean isRetire(Article article) throws DalException, BllException {
		RetraitManager retraitManager = RetraitManager.getInstance();
		return (retraitManager.getRetrait(article.getNoArticle()) != null) ? true : false;
	}
		

	/**
	 * {@inheritDoc}
	 * 
	 * @throws BllException
	 * @see fr.eni.encheres.dal.ArticleDAO#isEncoreEnVente(fr.eni.encheres.bo.Article)
	 */
	@Override
	public boolean isDateFinDepassee(Article article) throws DalException, BllException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        DateFormatter dtf = 
        Date date1 = sdf.parse(article.getDateFinEncheres());
        Date date2 = sdf.parse("2010-01-31");

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    	Date now = dateFormat.format(new Date());
        
        System.out.println("date1 : " + sdf.format(date1));
        System.out.println("date2 : " + sdf.format(date2));
        
        if (date1.after(date2)) {
            System.out.println("Date1 is after Date2");
        }

        if (date1.before(date2)) {
            System.out.println("Date1 is before Date2");
        }

        if (date1.equals(date2)) {
            System.out.println("Date1 is equal Date2");
        }

    }
		
		article.getDateFinEncheres() < dtf.format(localDate);
		
		Enchere ench = enchereManager.getBestEnchereByIdArticle(article.getNoArticle());
		ench.getDate_enchere()		
	}

}
