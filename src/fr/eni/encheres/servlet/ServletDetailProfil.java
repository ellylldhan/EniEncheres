package fr.eni.encheres.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BllException;

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
		Utilisateur utilisateur = null;
		boolean isMyAccount = false;
		
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
		
		String parametreIdUtilisateur = request.getParameter("idUtilisateur");
		int idUtilisateur = 0;
		
		if (parametreIdUtilisateur != null) {
			idUtilisateur = Integer.parseInt(parametreIdUtilisateur);
		}
		
		utilisateur = utilisateurManager.getUtilisateur(idUtilisateur);
		
		request.setAttribute("utilisateur", utilisateur);
		request.setAttribute("isMyAccount", isMyAccount);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detail_profil.jsp");
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
