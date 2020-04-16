package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet({ "/eni/encheres/ServletAccueil", "/accueil", "/home", "/eni/encheres/accueil", "/eni/encheres/home" })
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String recherche = null;
	public String categorie = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAccueil() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Integer> listeCodesErreur = new ArrayList<>();

		try {
			// Instanciations managers
			EnchereManager enchereManager = EnchereManager.getInstance();
			CategorieManager categorieManager = CategorieManager.getInstance();

			// Lister les enchere actives selon categorie (le triage des cat. est fait dans
			// le manager)
			List<Enchere> listeEncheresActives = enchereManager.getEncheresActives(categorie);

			// Recherche d'une String dans le nom des articles des enchères actives
			if (recherche != null) {
				List<Enchere> resultatRecherche = new ArrayList<>();

				for (Enchere enchere : listeEncheresActives) {
					String nomArticle = enchere.getArticle().getNomArticle().toLowerCase();
					if (nomArticle.contains(recherche.toLowerCase())) {
						resultatRecherche.add(enchere);
					}
				}

				// Si recherche infructueuse, ajoute code erreur correspondant
				if (resultatRecherche.size() == 0) {
					listeCodesErreur.add(CodesResultatServlets.ENCHERE_NOT_FOUND);
				}

				// Quelque soit le resultat, on le place dans listeEncheresActives (contient
				// null ou une liste d'enchères)
				listeEncheresActives = resultatRecherche;
			}

			request.setAttribute("listeEncheres", listeEncheresActives);

			// Lister les categories pour le dropdown menu
			List<Categorie> listeCategories = categorieManager.getCategories();
			request.setAttribute("listeCategories", listeCategories);

		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		recherche = request.getParameter("recherche");
		categorie = request.getParameter("categorie");

		doGet(request, response);
	}

}