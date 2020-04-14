package fr.eni.encheres.servlet;

import java.io.IOException;
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
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet({ "/eni/encheres/ServletAccueil", "/accueil", "/home", "/eni/encheres/accueil", "/eni/encheres/home" })
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		try {
			EnchereManager enchereManager = EnchereManager.getInstance();
			CategorieManager categorieManager = CategorieManager.getInstance();

			// Choper les encheres
			List<Enchere> encheres = enchereManager.getEncheresActives();
			
			// Choper les categories
			List<Categorie> categories = categorieManager.getCategories();

			request.setAttribute("listeEncheres", encheres);
			request.setAttribute("listeCategories", categories);

		} catch (BllException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
		rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}