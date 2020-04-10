package fr.eni.encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.exception.BllException;

/**
 * Servlet implementation class ServletEnchere
 */
@WebServlet("/eni/encheres/ServletEnchere")
public class ServletEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEnchere() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Article article = null;
		try {
			ArticleManager articleManager = ArticleManager.getInstance();
			EnchereManager enchereManager = EnchereManager.getInstance();
			RetraitManager retraitManager = RetraitManager.getInstance();

			int idArticle = 1;//Integer.parseInt(request.getParameter("idArticle"));
			article = articleManager.getArticle(idArticle);
			
			request.setAttribute("Article", article );
			request.setAttribute("Retrait", retraitManager.getRetrait(idArticle));
			request.setAttribute("MeilleurPrix", enchereManager.getBestEnchereByIdArticle(idArticle).getMontant_enchere());
			
	
			

		} catch (BllException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (article != null) {				
				request.setAttribute("MeilleurPrix", article.getPrixInitial());
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/enchere.jsp");
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
