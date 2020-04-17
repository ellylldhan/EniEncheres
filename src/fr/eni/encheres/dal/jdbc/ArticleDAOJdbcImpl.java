/**
 * 
 */
package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatDAL;
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

	private static final String RQT_SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = ? AND no_utilisateur IS NOT NULL ";
	private static final String RQT_SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS where no_utilisateur IS NOT NULL ";
	private static final String RQT_INSERT = "INSERT INTO ARTICLES_VENDUS "
			+ "(nom_article, description, date_debut_encheres, date_fin_encheres,"
			+ " prix_vente, prix_initial, no_utilisateur, no_categorie) VALUES(?,?,?,?,?,?,?,?)";
	private static final String RQT_UPDATE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
	private static final String RQT_DELETE = "DELETE ARTICLES_VENDUS WHERE no_article = ?";
	private static final String RQT_FIND = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE ? AND no_utilisateur IS NOT NULL ";
	private static final String RQT_EN_COURS_BY_ID_ARTICLE = "SELECT U.no_utilisateur, "
			+ "A.no_article, A.nom_article, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres,A.description ,"
			+ "C.no_categorie " + "FROM ARTICLES_VENDUS as A "
			+ "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur "
			+ "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie "
			+ "Where (GETDATE() BETWEEN A.date_debut_encheres AND A.date_fin_encheres "
			+ "Or A.date_debut_encheres = GETDATE() "
			+ "Or date_fin_encheres = GETDATE()) AND A.no_utilisateur IS NOT NULL "
			+ "ORDER BY A.date_fin_encheres ASC";
	private static final String RQT_OUVERTE_BY_ID_ARTICLE = "SELECT U.no_utilisateur, "
			+ "A.no_article, A.nom_article, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres,A.description, "
			+ "C.no_categorie " + "FROM ARTICLES_VENDUS as A "
			+ "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur "
			+ "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie "
			+ "WHERE GETDATE() < A.date_debut_encheres AND A.no_utilisateur IS NOT NULL "
			+ "ORDER BY A.date_fin_encheres ASC";
	private static final String RQT_TERMINEE_BY_ID_ARTICLE = "SELECT U.no_utilisateur, "
			+ "A.no_article, A.nom_article, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres,A.description ,"
			+ "C.no_categorie " + "FROM ARTICLES_VENDUS as A "
			+ "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur "
			+ "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie "
			+ "WHERE GETDATE() > A.date_fin_encheres AND A.no_utilisateur IS NOT NULL "
			+ "ORDER BY A.date_fin_encheres ASC";

	
	private static final String RQT_EN_COURSVENDEUR_BY_ID_ARTICLE = "SELECT U.no_utilisateur, "
			+ "A.no_article, A.nom_article, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres,A.description ,"
			+ "C.no_categorie " + "FROM ARTICLES_VENDUS as A "
			+ "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur "
			+ "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie "
			+ "WHERE A.no_utilisateur = ? "
			+ "AND( GETDATE() BETWEEN A.date_debut_encheres AND A.date_fin_encheres "
			+ "Or A.date_debut_encheres = GETDATE() "
			+ "Or A.date_fin_encheres = GETDATE()) "
			+ "ORDER BY A.date_fin_encheres ASC";
	private static final String RQT_OUVERTEVENDEUR_BY_ID_ARTICLE = "SELECT U.no_utilisateur, "
			+ "A.no_article, A.nom_article, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres,A.description, "
			+ "C.no_categorie " + "FROM ARTICLES_VENDUS as A "
			+ "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur "
			+ "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie "
			+ "WHERE  A.no_utilisateur = ? "
			+ "AND GETDATE() < A.date_debut_encheres "
			+ "ORDER BY A.date_fin_encheres ASC";
	private static final String RQT_TERMINEEVENDEUR_BY_ID_ARTICLE = "SELECT U.no_utilisateur, "
			+ "A.no_article, A.nom_article, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres,A.description ,"
			+ "C.no_categorie " + "FROM ARTICLES_VENDUS as A "
			+ "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur "
			+ "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie "
			+ "WHERE  A.no_utilisateur = ? "
			+ "AND GETDATE() > A.date_fin_encheres "
			+ "ORDER BY A.date_fin_encheres ASC";
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
	public Article selectById(int id) throws BusinessException {
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}
		return item;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#selectAll()
	 */
	@Override
	public List<Article> selectAll() throws BusinessException {
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#findByName(java.lang.String)
	 */
	@Override
	public List<Article> findByName(String nom) throws BusinessException {
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#insert(fr.eni.encheres.bo.Article)
	 */
	@Override
	public void insert(Article article) throws BusinessException {
		// TODO: nbLignesModifiees : supprimer, ou ajouter return ?
		int nbLignesModifiees = 0;

		try (Connection conn = ConnectionProvider.getConnection()) {
			PreparedStatement requete = conn.prepareStatement(RQT_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

			requete.setString(1, article.getNomArticle());
			requete.setString(2, article.getDescription());
			requete.setDate(3, Date.valueOf(article.getDateDebut()));
			requete.setDate(4, Date.valueOf(article.getDateFinEncheres()));
			requete.setInt(5, article.getPrixInitial());
			requete.setInt(6, article.getPrixVente());
			requete.setInt(7, article.getUtilisateur().getNoUtilisateur());
			requete.setInt(8, article.getCategorie().getNoCategorie());

			nbLignesModifiees = requete.executeUpdate();
			ResultSet rs = requete.getGeneratedKeys();

			if (rs.next()) {
				article.setNoArticle(rs.getInt(1));
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#update(fr.eni.encheres.bo.Article)
	 */
	@Override
	public void update(Article article) throws BusinessException {
		try (Connection conn = ConnectionProvider.getConnection()) {
			PreparedStatement requete = conn.prepareStatement(RQT_UPDATE_PRIX_VENTE);

			requete.setInt(1, article.getPrixVente());
			requete.setInt(2, article.getNoArticle());

			requete.executeUpdate();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#delete(int)
	 */
	@Override
	public void delete(int id) throws BusinessException {
		try (Connection conn = ConnectionProvider.getConnection()) {
			PreparedStatement requete = conn.prepareStatement(RQT_DELETE);

			requete.setInt(1, id);

			requete.executeUpdate();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
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
	private Article itemBuilder(ResultSet rs) throws SQLException, BusinessException {

		Utilisateur utilisateur = DAOFactory.getUtilisateurDAO().selectById(rs.getInt("no_utilisateur"));
		Categorie categorie = DAOFactory.getCategorieDAO().selectById(rs.getInt("no_categorie"));

		return new Article(rs.getInt("no_article"), rs.getString("nom_article"), rs.getString("description"),
				rs.getDate("date_debut_encheres").toLocalDate(), rs.getDate("date_fin_encheres").toLocalDate(),
				rs.getInt("prix_initial"), rs.getInt("prix_vente"), utilisateur, categorie);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#getEnCours(int)
	 */
	@Override
	public List<Article> getEnCours() throws BusinessException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_EN_COURS_BY_ID_ARTICLE);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#getOuverte(int)
	 */
	@Override
	public List<Article> getOuverte() throws BusinessException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_OUVERTE_BY_ID_ARTICLE);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.eni.encheres.dal.ArticleDAO#getTerminee(int)
	 */
	@Override
	public List<Article> getTerminee() throws BusinessException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_TERMINEE_BY_ID_ARTICLE);

			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.ArticleDAO#getEnCoursVendeur(int)
	 */
	@Override
	public List<Article> getEnCoursVendeur(int id) throws BusinessException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_EN_COURSVENDEUR_BY_ID_ARTICLE);
			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.ArticleDAO#getOuverteVendeur(int)
	 */
	@Override
	public List<Article> getOuverteVendeur(int id) throws BusinessException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_OUVERTEVENDEUR_BY_ID_ARTICLE);
			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}

	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.ArticleDAO#getTermineeVendeur(int)
	 */
	@Override
	public List<Article> getTermineeVendeur(int id) throws BusinessException {
		List<Article> articles = new ArrayList<Article>();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement requete = connection.prepareStatement(RQT_TERMINEEVENDEUR_BY_ID_ARTICLE);
			requete.setInt(1, id);
			ResultSet rs = requete.executeQuery();

			while (rs.next()) {
				articles.add(itemBuilder(rs));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}",
					new Object[] { nomClasseCourante, nomMethodeCourante, e.getMessage() });
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
		}

		return articles;
	}
	

}
