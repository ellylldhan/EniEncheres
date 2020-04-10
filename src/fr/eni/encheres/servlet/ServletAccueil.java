package fr.eni.encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.exception.BllException;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet({ "/eni/encheres/ServletAccueil", "/accueil", "/home", "/eni/encheres/accueil", "/eni/encheres/home" })
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CHEMIN_VERS_LA_JSP = "/WEB-INF/pageAccueilNonConnecte.jsp"; 
			
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueil() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			ArticleManager articleManager = ArticleManager.getInstance();
			EnchereManager enchereManager = EnchereManager.getInstance();
			RetraitManager retraitManager = RetraitManager.getInstance();
			
			// comparer les dates
			if (articleManager.get)
			
			request.setAttribute("Article", article );
			request.setAttribute("Retrait", retraitManager.getRetrait(idArticle));
			request.setAttribute("MeilleurPrix", enchereManager.getBestEnchereByIdArticle(idArticle).getMontant_enchere());
			
	
			

		} catch (BllException e) {

			
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(CHEMIN_VERS_LA_JSP);
		rd.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
