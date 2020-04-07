/**
 * 
 */
package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class CategorieDAO {
	
	private static String RQT_SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = ?";
	private static String RQT_SELECT_ALL = "SELECT * FROM CATEGORIES";
	private static String RQT_INSERT = "INSERT INTO CATEGORIES VALUES(?)";
	private static String RQT_UPDATE = "UPDATE CATEGORIES SET libelle = ? WHERE no_categorie = ?";
	private static String RQT_DELETE = "DELETE CATEGORIES WHERE no_categorie = ?";
	
	/**
	 * Méthode en charge de récupérer une instance d'objet Categorie en fonction de son numéro
	 * @param noCategorie
	 * @return categorie : Instance d'objet Categorie
	 * @throws DalException
	 */
	public Categorie selectById(int noCategorie) throws DalException {

        Categorie categorie = null;

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SELECT_BY_ID);
            requete.setInt(1, noCategorie);
            ResultSet rs = requete.executeQuery();

            if (rs.next()) {
                categorie = itemBuilder(rs);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return categorie;

    }
	
	/**
	 * Méthode en charge de récupérer tous les enregistrements de la table CATEGORIES
	 * @return categories : Liste d'instances d'objet Categorie
	 * @throws DalException
	 */
	public List<Categorie> selectAll() throws DalException {

        List<Categorie> categories = new ArrayList<Categorie>();

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_SELECT_ALL);

            ResultSet rs = requete.executeQuery();

            while (rs.next()) {
                categories.add(itemBuilder(rs));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return categories;

    }
	
	/**
	 * Méthode en charge d'insérer un enregistrement dans la table CATEGORIES
	 * @param categorie
	 * @throws DalException
	 */
	public void insert(Categorie categorie) throws DalException {

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
            //TODO: handle exception
        }

    }
	
	/**
	 * Méthode en charge de modifier un enregistrement de CATEGORIES
	 * @param categorie
	 * @throws DalException
	 */
	public void update(Categorie categorie) throws DalException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_UPDATE);
            requete.setString(1, categorie.getLibelle());

            requete.setInt(2, categorie.getNoCategorie());

            requete.executeUpdate();

        } catch (Exception e) {
            //TODO: handle exception
        }

    }
	
	/**
	 * Méthode en charge de supprimer un enregistrement de la table CATEGORIES
	 * @param noCategorie
	 * @throws DalException
	 */
	public void delete(int noCategorie) throws DalException {

        try (Connection connection = ConnectionProvider.getConnection()) {
            PreparedStatement requete = connection.prepareStatement(RQT_DELETE);
            requete.setInt(1, noCategorie);
            requete.executeUpdate();
        } catch (Exception e) {
            //TODO: handle exception
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
