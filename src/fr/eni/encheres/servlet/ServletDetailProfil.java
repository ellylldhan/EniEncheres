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
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

/**
 * Servlet implementation class ServletDetailProfil
 */
@WebServlet("/eni/encheres/detailProfil")
public class ServletDetailProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDetailProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		int idUtilisateur = -1;
		int paramIdUtilisateur = -1;
		Utilisateur utilisateur = null;
		boolean isMyAccount = false;
		
		//Vérification de l'existance d'une session, retourne true si oui, sinon null
		HttpSession session = request.getSession();
		
		//Si on a des erreurs, on les envoit à la jsp
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail_profil.jsp");
			rd.forward(request, response);
		} 
		else {
			UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
			
			String parametreIdUtilisateur = request.getParameter("idUtilisateur");
			
			if (parametreIdUtilisateur != null) {
				paramIdUtilisateur = Integer.parseInt(parametreIdUtilisateur);
				
				if (session.getAttribute("idUtilisateur") != null) {
					idUtilisateur = (int) session.getAttribute("idUtilisateur");
					
					if (paramIdUtilisateur == idUtilisateur) {
						isMyAccount = true;
					}
				}
				
				try {
					utilisateur = utilisateurManager.getUtilisateur(paramIdUtilisateur);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", listeCodesErreur);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail_profil.jsp");
					rd.forward(request, response);
				}
				
				request.setAttribute("utilisateur", utilisateur);
				request.setAttribute("isMyAccount", isMyAccount);
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail_profil.jsp");
				rd.forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + "/eni/encheres/accueil");
			}
			
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
