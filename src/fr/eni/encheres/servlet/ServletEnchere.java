package fr.eni.encheres.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebEndpoint;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
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
		List<Integer> listeCodesErreur = new ArrayList();

		int idArticle = this.checkIdArticle(request, listeCodesErreur);


		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
		else {
			try {
				ArticleManager articleManager = ArticleManager.getInstance();
				EnchereManager enchereManager = EnchereManager.getInstance();
				RetraitManager retraitManager = RetraitManager.getInstance();



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
					Utilisateur utilisateur = null;
					if (request.getSession().getAttribute("idUtilisateur") != null) {
						int idUtilisateur = (int) request.getSession().getAttribute("idUtilisateur");
						utilisateur = UtilisateurManager.getInstance().getUtilisateur(idUtilisateur);
					}

					if (utilisateur.getNoUtilisateur() == enchere.getUtilisateur().getNoUtilisateur() && utilisateur != null) {
						win = true;
					}
				}
				request.setAttribute("fini", fini);
				request.setAttribute("win", win);


			}
			catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/enchere.jsp");
				rd.forward(request, response);
			}

			if(article == null){
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/enchere.jsp");
				rd.forward(request, response);

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager enchereManager = EnchereManager.getInstance();
		ArticleManager articleManager = ArticleManager.getInstance();

		List<Integer> listeCodesErreur = new ArrayList<>();
		int idArticle = this.checkIdArticle(request, listeCodesErreur);
		int proposition = this.checkPropostion(request, listeCodesErreur);
		Article article = null;
		if (request.getSession().getAttribute("idUtilisateur") == null) {
			listeCodesErreur.add(CodesResultatBLL.USER_NOT_CONNECTED);
		}

		if (listeCodesErreur.size() > 0) {
			if (idArticle != 0) {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				doGet(request, response);
			}else {
				request.setAttribute("listeCodesErreur", listeCodesErreur);
				response.sendRedirect(request.getContextPath() + "/accueil");
			}

		}else {
			try {
				int idUtilisateur = (int) request.getSession().getAttribute("idUtilisateur");
				Utilisateur utilisateur =  UtilisateurManager.getInstance().getUtilisateur(idUtilisateur);

				article = articleManager.getArticle(idArticle);
				Enchere enchere = new Enchere(utilisateur, article, proposition);
				enchereManager.update(enchere);
				doGet(request, response);
			}catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				doGet(request, response);
			}
		}

	}


	/**
	 * Méthode en charge de vérifier si l'id est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private int checkIdArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String parameteIdArticle= request.getParameter("idArticle");
		//TODO
		parameteIdArticle = "1";
		//
		if (parameteIdArticle == null || parameteIdArticle.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.IDARTICLE_NOT_FOUND);
		} else {
			return Integer.parseInt(parameteIdArticle);
		}
		return 0;
	}

	/**
	 * Méthode en charge de vérifier si l'id est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private int checkPropostion(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String parameterPropostion = request.getParameter("proposition");


		if (parameterPropostion == null || parameterPropostion.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.Proposition_NOT_FOUND);
		} else {
			return Integer.parseInt(parameterPropostion);
		}
		return 0;
	}

}
