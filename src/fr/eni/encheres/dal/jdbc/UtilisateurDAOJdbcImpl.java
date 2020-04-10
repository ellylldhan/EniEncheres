package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

    private static Logger LOGGER = MonLogger.getLogger("UtilisateurDAOJdbcImpl");

    private static final String RQT_SELECT_BY_ID = "SELECT * from UTILISATEURS WHERE no_utilisateur = ?";
    private static final String RQT_SELECT_ALL = "SELECT * from UTILISATEURS";
    private static final String RQT_INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?)\n";
    private static final String RQT_UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, credit = ?, administrateur = ? WHERE no_utilisateur = ?";
    private static final String RQT_DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#selectById(int)
     */
    @Override
    public Utilisateur selectById(int id) throws DalException {

        Utilisateur u = null;

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_SELECT_BY_ID);
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();

            if(rs.next()){
                u = itemBuilder(rs);
            }

        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la récuperation de l'utilisateur dans la methode selectById : " + e.getMessage());
        }

        return u;
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#selectAll()
     */
    @Override
    public List<Utilisateur> selectAll() throws DalException {

        List<Utilisateur> utilisateurs = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_SELECT_ALL);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()){
                utilisateurs.add(itemBuilder(rs));
            }
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la récupération de la liste des utilisateurs dans la methode selectAll : " + e.getMessage());
        }

        return utilisateurs;
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#insert(fr.eni.encheres.bo.Categorie)
     */
    @Override
    public void insert(Utilisateur u) throws DalException {
        int nbLignes = 0;

        try(Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pStmt = preparedStatementBuilder(u, con, false);

            nbLignes = pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();

            if(rs.next()){
                u.setNoUtilisateur(rs.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de l'insertion de l'utilisateur [" + u.getNom() + " " + u.getPrenom() + "] : " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#update(fr.eni.encheres.bo.Categorie)
     */
    @Override
    public void update(Utilisateur u) throws DalException {

        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pStmt = preparedStatementBuilder(u, con, true);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la mise à jour de l'utilisateur + " + u.getNoUtilisateur() + "] : " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * @see fr.eni.encheres.dal.UtilisateurDAO#delete(int)
     */
    @Override
    public void delete(int id) throws DalException {

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pStmt = con.prepareStatement(RQT_DELETE);
            pStmt.setInt(1, id);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la suppression de l'utilisateur [" + id + "]");
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
}
