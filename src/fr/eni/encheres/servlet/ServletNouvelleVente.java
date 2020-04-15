package fr.eni.encheres.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Local;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/eni/encheres/nouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletNouvelleVente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategorieManager categorieManager = CategorieManager.getInstance();
			request.setAttribute("listeCategories", categorieManager.getCategories());
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelle_vente.jsp");
			rd.forward(request, response);
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//Lecture des paramètres
		String nomArticle = null;
		String description = null;
		int noCategorie;
		int prixInitial = 0;
		LocalDate dateDebutEncheres = null;
		LocalDate dateFinEncheres = null;
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		nomArticle = lireParametreNomArticle(request, listeCodesErreur);
		description = lireParametreDescriptionArticle(request, listeCodesErreur);
		noCategorie = lireParametreNoCategorie(request, listeCodesErreur);
		prixInitial = lireParametrePrixInitialArticle(request, listeCodesErreur);
		dateDebutEncheres = lireParametreDateDebutEncheresArticle(request, listeCodesErreur);
		dateFinEncheres = lireParametreDateFinEncheresArticle(request, listeCodesErreur);
		
		if(listeCodesErreur.size() > 0)
		{
			request.setAttribute("listeCodesErreur",listeCodesErreur);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelle_vente.jsp");
			rd.forward(request, response);
		}
		else
		{
			try {
				
			
				CategorieManager categorieManager = CategorieManager.getInstance();
				Categorie categorie = categorieManager.getCategorie(noCategorie);
				
				//TODO: A modifier lorsque les contexts de session seront implémentés
				UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
				Utilisateur utilisateur = utilisateurManager.getUtilisateur(1);
				
				Article articleToAdd = new Article(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, utilisateur, categorie);
				ArticleManager articleManager = ArticleManager.getInstance();
				int article = articleManager.addArticle(articleToAdd);
				
				//Si les champs du point de retrait sont renseignés, on créer le point de retrait lié à l'article
				boolean checkerRetrait = verifierChampsRetrait(request, listeCodesErreur);
				if (checkerRetrait = true) {
					String rueRetrait = request.getParameter("rue_retrait");
					String codePostalRetrait = request.getParameter("code_postal_retrait");
					String villeRetrait = request.getParameter("ville_retrait");
					
					RetraitManager retraitManager = RetraitManager.getInstance();
					Retrait retraitToAdd = new Retrait(articleToAdd, rueRetrait, codePostalRetrait, villeRetrait);
					
					int retrait = retraitManager.addRetrait(retraitToAdd);
					
					request.setAttribute("idArticle", article);
					response.sendRedirect(request.getContextPath() + "/eni/encheres/encheres");
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelle_vente.jsp");
				rd.forward(request, response);
			}
		}
		
	}
	
	/**
	 * Méthode en charge de vérifier si le nom de l'article est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreNomArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String nomArticle;
		nomArticle = request.getParameter("nom_article");
		
		if (nomArticle == null || nomArticle.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.NOM_ARTICLE_OBLIGATOIRE);
		}
		
		return nomArticle;
	}
	
	/**
	 * Méthode en charge de vérifier si la description de l'article est bien renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreDescriptionArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String descriptionArticle;
		descriptionArticle = request.getParameter("description");
		
		if (descriptionArticle == null || descriptionArticle.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.DESCRIPTION_ARTICLE_OBLIGATOIRE);
		}
		
		return descriptionArticle;
	}

	/**
	 * Méthode en charge de vérifier si le prix initial de vente est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private int lireParametrePrixInitialArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String prixInitial = request.getParameter("prix_initial_article");
		int parsedPrixInitial = -1;
		
		if (prixInitial == null || prixInitial.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.PRIX_INITIAL_ARTICLE_OBLIGATOIRE);
		} else {
			parsedPrixInitial = Integer.parseInt(prixInitial);
		}
		
		return parsedPrixInitial;
	}
	
	/**
	 * Méthode en charge de vérifier si la date de début des enchères est bien renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private LocalDate lireParametreDateDebutEncheresArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String dateEnchereDebutEncheresArticle = request.getParameter("date_debut_enchere_article");
		LocalDate localDateDebutEncheres = null;
		
		if (dateEnchereDebutEncheresArticle == null || dateEnchereDebutEncheresArticle.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.DATE_DEBUT_ENCHERES_ARTICLE_OBLIGATOIRE);
		} else {
			localDateDebutEncheres = LocalDate.parse(dateEnchereDebutEncheresArticle);
		}
		
		return localDateDebutEncheres;
	}
	
	/**
	 * Méthode en charge de vérifier si la date de fin des enchères est bien renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private LocalDate lireParametreDateFinEncheresArticle(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String dateEnchereFinEncheresArticle = request.getParameter("date_fin_enchere_article");
		LocalDate localDateFinEncheres = null;
		
		if (dateEnchereFinEncheresArticle == null || dateEnchereFinEncheresArticle.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.DATE_FIN_ENCHERES_ARTICLE_OBLIGATOIRE);
		} else {
			localDateFinEncheres = LocalDate.parse(dateEnchereFinEncheresArticle);
		}
		
		return localDateFinEncheres;
	}
	
	/**
	 * Méthode en charge de vérifier si le No de catégorie est bien renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private int  lireParametreNoCategorie(HttpServletRequest request, List<Integer> listeCodesErreur) {

		int noCategorie = 0;
		String noCategorieParam = request.getParameter("categorie_article");
				
		if (noCategorieParam == null || noCategorieParam.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.DATE_FIN_ENCHERES_ARTICLE_OBLIGATOIRE);
		} else {
			noCategorie = Integer.parseUnsignedInt(noCategorieParam);
		}
		
		return noCategorie;
	}
	
	
	/**
	 * Méthode en charge de vérifier si un point de retrait est renseigné
	 * @param request
	 * @return
	 */
	private boolean verifierChampsRetrait(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String rueRetrait = request.getParameter("rue_retrait");
		String codePostalRetrait = request.getParameter("code_postal_retrait");
		String villeRetrait = request.getParameter("ville_retrait");
		
		boolean checker = false;
		
		//Si un des champs du retrait est remplie et qu'au moins un autre est vide
		if ((rueRetrait != null && (codePostalRetrait == null || villeRetrait == null))
			|| (codePostalRetrait != null && (rueRetrait == null || villeRetrait == null))
			|| (villeRetrait != null && (rueRetrait == null || codePostalRetrait == null))) {
			listeCodesErreur.add(CodesResultatServlets.ERREURS_CHAMPS_RETRAIT);
		} else if (rueRetrait != null && codePostalRetrait != null && villeRetrait != null) {
			checker = true;
		}
		
		return checker;
	}

}
