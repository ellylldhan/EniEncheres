package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatDAL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	private static Logger logger ;
    private static StackTraceElement stack;
    private static String nomMethodeCourante;
    private static String nomClasseCourante;
    
    private static final String RQT_SELECT_BY_ID = "SELECT * from UTILISATEURS WHERE no_utilisateur = ?";
    private static final String RQT_SELECT_BY_ARG = "SELECT * from UTILISATEURS WHERE pseudo = ? OR email = ?";
    private static final String RQT_SELECT_ALL = "SELECT * from UTILISATEURS";
    private static final String RQT_INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?)\n";
    private static final String RQT_UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, credit = ?, administrateur = ? WHERE no_utilisateur = ?";
    private static final String RQT_UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
    private static final String RQT_DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";

    
    public UtilisateurDAOJdbcImpl() {
		logger = MonLogger.getLogger(getClass().getName());

        stack = new Throwable().getStackTrace()[0];
        nomClasseCourante = stack.getClassName();
        nomMethodeCourante = stack.getMethodName();
	}
    
    @Override
    public Utilisateur selectByArg(String a) throws BusinessException {
        Utilisateur u = null;

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_SELECT_BY_ARG);
            pStmt.setString(1, a);
            pStmt.setString(2, a);
            ResultSet rs = pStmt.executeQuery();

            if(rs.next()){
                u = itemBuilder(rs);
            }

        } catch (SQLException e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }
        return u;
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#selectById(int)
     */

    @Override
    public Utilisateur selectById(int id) throws BusinessException {

        Utilisateur u = null;

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_SELECT_BY_ID);
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();

            if(rs.next()){
                u = itemBuilder(rs);
            }

        } catch (SQLException e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return u;
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#selectAll()
     */
    @Override
    public List<Utilisateur> selectAll() throws BusinessException {

        List<Utilisateur> utilisateurs = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_SELECT_ALL);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()){
                utilisateurs.add(itemBuilder(rs));
            }
        } catch (SQLException e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_OBJET_ECHEC);
			throw businessException;
        }

        return utilisateurs;
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#insert(fr.eni.encheres.bo.Categorie)
     */
    @Override
    public void insert(Utilisateur u) throws BusinessException {
        int nbLignes = 0;

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pStmt = preparedStatementBuilder(u, con, false);

            nbLignes = pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();

            if(rs.next()){
                u.setNoUtilisateur(rs.getInt(1));
            }
        } catch (SQLException e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
        }
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#update(fr.eni.encheres.bo.Categorie)
     */
    @Override
    public void update(Utilisateur u) throws BusinessException {

        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = preparedStatementBuilder(u, con, true);
            pStmt.executeUpdate();
        } catch (SQLException e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
        }
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#delete(int)
     */
    @Override
    public void delete(int id) throws BusinessException {

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_DELETE);
            pStmt.setInt(1, id);
            pStmt.executeUpdate();
        } catch (SQLException e) {
        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
        }
    }

    /**
     * Méthode en charge de créer une instance d'objet Utilisateur en fonction des enregistrements récupérés en base de données
     * @param rs
     * @return Utilisateur
     * @throws SQLException
     */
    private Utilisateur itemBuilder(ResultSet rs) throws SQLException {
        return new Utilisateur(
        		rs.getInt("no_utilisateur"),
                rs.getString("pseudo"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("rue"),
                rs.getString("code_postal"),
                rs.getString("ville"),
                rs.getString("mot_de_passe"),
                rs.getInt("credit"),
                rs.getBoolean("administrateur")
        );
    }

    /**
     * Méthode en charge de configurer le PreparedStatement dans le cas d'un INSERT ou d'un UPDATE
     * @param u
     * @param con
     * @param isAlreadyInDb : true = utilisateur existant en BDD, false = utilisateur n'éxistant pas en BDD
     * @return
     * @throws SQLException
     */
    private PreparedStatement preparedStatementBuilder(Utilisateur u, Connection con, boolean isAlreadyInDb) throws SQLException {

        PreparedStatement pStmt = null;

        if(isAlreadyInDb)
            pStmt = con.prepareStatement(RQT_UPDATE);
        else
            pStmt = con.prepareStatement(RQT_INSERT);

        pStmt.setString(1, u.getPseudo());
        pStmt.setString(2, u.getNom());
        pStmt.setString(3, u.getPrenom());
        pStmt.setString(4, u.getEmail());

        if(u.getTelephone() != null)
            pStmt.setString(5, u.getTelephone());
        else
            pStmt.setNull(5, Types.VARCHAR);

        pStmt.setString(6, u.getRue());
        pStmt.setString(7, u.getCodePostal());
        pStmt.setString(8, u.getVille());
        pStmt.setString(9, u.getMotDePasse());
        pStmt.setInt(10, u.getCredit());
        pStmt.setBoolean(11, u.isAdministrateur());

        // Where no_utilisateur = u.getNoUtilisateur()
        if(isAlreadyInDb)
            pStmt.setInt(12, u.getNoUtilisateur());

        return pStmt;
    }

	/**
	 * {@inheritDoc}
	 * @see fr.eni.encheres.dal.UtilisateurDAO#updateCredit(fr.eni.encheres.bo.Utilisateur)
	 */
	@Override
	public void updateCredit(Utilisateur u) throws BusinessException {
		  try (Connection connection = ConnectionProvider.getConnection()) {
	            PreparedStatement requete = connection.prepareStatement(RQT_UPDATE_CREDIT);

	            requete.setInt(1, u.getCredit());
	            requete.setInt(2, u.getNoUtilisateur());

	            requete.executeUpdate();

	        } catch (Exception e) {
	        	logger.log(Level.SEVERE, "Erreur dans {0} / {1} : {2}", new Object[]{nomClasseCourante, nomMethodeCourante, e.getMessage()});
				BusinessException businessException = new BusinessException();
				businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
				throw businessException;
	        }
	}
}
