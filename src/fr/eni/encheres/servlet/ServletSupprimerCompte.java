package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimerCompte
 */
@WebServlet("/eni/encheres/supprimerCompte")
public class ServletSupprimerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSupprimerCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur = new ArrayList<>();
		int idUtilisateur = -1;
		Utilisateur utilisateur = null;
				
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			
			response.sendRedirect(request.getContextPath() + "/eni/encheres/creationProfil");
		} else {
			if (request.getParameter("idUtilisateur") != null) {
				idUtilisateur = Integer.parseInt(request.getParameter("idUtilisateur"));
				
				try {
					UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
					utilisateur = utilisateurManager.getUtilisateur(idUtilisateur);
					
					if (utilisateur != null) {
						request.getSession().invalidate();
						utilisateurManager.removeUtilisateur(idUtilisateur);
					}
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", listeCodesErreur);
					response.sendRedirect(request.getContextPath() + "/eni/encheres/creationProfil");
				}
			}
			
			response.sendRedirect(request.getContextPath() + "/eni/encheres/accueil");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
