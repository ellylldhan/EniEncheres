package fr.eni.encheres.servlet;

import java.io.IOException;
import java.time.LocalDate;

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
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BllException;
import sun.font.CreatedFontTracker;

/**
 * Servlet implementation class ServletEnchere
 */
@WebServlet("/eni/encheres/encheres")
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
			
			String parameteridArticle = request.getParameter("idArticle");
			
			parameteridArticle ="1";
			if (parameteridArticle != null) {
	
				int idArticle = Integer.parseInt(parameteridArticle);
				
				
				article = articleManager.getArticle(idArticle);
				request.setAttribute("IdArticle", idArticle);
				request.setAttribute("Article", article );
				request.setAttribute("Retrait", retraitManager.getRetrait(idArticle));
				
				Enchere enchere = enchereManager.getBestEnchereByIdArticle(idArticle);
				
				
				if (enchere == null) {
					enchere = new Enchere();
					enchere.setMontant_enchere(article.getPrixInitial());
					
				}
				request.setAttribute("Enchere", enchere);
				
				boolean fini = false;
				boolean win = false;
				if (article != null && article.getDateFinEncheres().isBefore(LocalDate.now())) {
					fini = true;
					Utilisateur utilisateur =  (Utilisateur)request.getSession().getAttribute("utilisateur");
					/**
					 * à enlever après que le contexte utilisateur utilisateur existe 
					 */
					////
					UtilisateurManager utilisateurmanager = UtilisateurManager.getInstance();
					utilisateur = utilisateurmanager.getUtilisateur(1);
					////
					if (utilisateur.getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur() ) {
						win = true;
					}
				}
				request.setAttribute("fini", fini);
				request.setAttribute("win", win);
				
			}
		} catch (BllException e) {
			// TODO Auto-generated catch block
		}
		
		if(article == null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/enchere.jsp");
			rd.forward(request, response);

		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager enchereManager = EnchereManager.getInstance();
		ArticleManager articleManager = ArticleManager.getInstance();
		
		String parameterProposition = request.getParameter("proposition");
		String parameteridArticle = request.getParameter("IdArticle");
		
		Article article = null;

		int proposition = 0;
		if (parameterProposition != null) {
			proposition = Integer.parseInt(parameterProposition);
		}
		
		int idArticle = 0;
		if (parameteridArticle != null) {
			idArticle = Integer.parseInt(parameteridArticle);
		}
		
		Utilisateur utilisateur =  (Utilisateur)request.getSession().getAttribute("utilisateur");
		
		/**
		 * à enlever après que le contexte utilisateur utilisateur existe 
		 */
		////
		UtilisateurManager utilisateurmanager = UtilisateurManager.getInstance();
		utilisateur = utilisateurmanager.getUtilisateur(1);
		////
		
		try {
			article = articleManager.getArticle(idArticle);
			enchereManager.create(new Enchere(utilisateur, article, proposition));
		} catch (BllException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
