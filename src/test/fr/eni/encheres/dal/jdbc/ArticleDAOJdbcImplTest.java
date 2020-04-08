/**
 * 
 */
package test.fr.eni.encheres.dal.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.exception.DalException;

/**
 * Classe en charge des tests unitaires de l'implémentation JDBC de ArticleDAO
 * 
 * @author Reno
 * @version EniEncheres - v1.0
 * @date 8 Apr 2020
 */
public class ArticleDAOJdbcImplTest {

	public static void main(String[] args) {
		// On instancie le DAO dont on a besoin pour tester nos méthodes
		ArticleDAO articleDAO = DAOFactory.getArticleDAO();

		try {
			Categorie catInfo = new Categorie("Informatique");
			Categorie catMeuble = new Categorie("Ameublement");
			Categorie catVet = new Categorie("Vêtement");
			Categorie catSport = new Categorie("Sport&Loisirs");

			Utilisateur user1 = new Utilisateur("pasAdmin1", "Toto", "McTata", "toto@email.com", "0299001235",
					"rue des fraises", "35000", "Rennes", "mdp123", 500, false);
			Utilisateur user2 = new Utilisateur("Admin1", "Titi", "UnNom", "titi@email.com", "0299000000",
					"place machin", "75000", "Paris", "mdp456", 10, true);

			Article art1 = new Article("Nom article 1", "Description art 1", LocalDate.of(2020, 2, 15),
					LocalDate.of(2020, 3, 15), 150, 300, user1, catInfo);
			Article art2 = new Article("Nom article 2", "Description art 2", LocalDate.of(2020, 3, 8),
					LocalDate.of(2020, 3, 9), 500, 1500, user2, catSport);

			articleDAO.insert(art1);
			articleDAO.insert(art2);
			System.out.println(art1.toString());
		} catch (DalException e) {
			e.printStackTrace();
		}
	}
}
