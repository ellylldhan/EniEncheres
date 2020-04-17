package fr.eni.encheres.servlet;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.log.MonLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/eni/encheres/GestionConnexionUtilisateur")
public class ServletGestionConnexionUtilisateur extends HttpServlet {

    private static final String LOGIN_VIEW = "/WEB-INF/login.jsp";
    private static Logger LOGGER = MonLogger.getLogger("ServletGestionConnexionUtilisateur");
    private static UtilisateurManager um = UtilisateurManager.getInstance();
    RequestDispatcher rd = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        if (request.getSession().getAttribute("idUtilisateur") == null) {
            rd = request.getRequestDispatcher(LOGIN_VIEW);
            rd.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/accueil");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        List<Integer> listeCodesErreur = new ArrayList<>();

        try {

            // Il n'existe pas de session
            if (request.getSession().getAttribute("idUtilisateur") == null) {
                Utilisateur u = null;
                String login = request.getParameter("identifiant");
                String pw = um.getHashFromPassword(request.getParameter("motDePasse"));
                HttpSession session = null;
                boolean isCorrectPw = false;

                if (login.isEmpty() && pw.isEmpty()) {
                    listeCodesErreur.add(CodesResultatBLL.WRONG_USER_INPUTS);
                } else {

                    u = um.getUtilisateurByArg(login);

                    if (u != null) {
                        if (um.isCorrectPassword(pw, u.getMotDePasse())) {
                            session = request.getSession();
                            session.setAttribute("idUtilisateur", u.getNoUtilisateur());
                            session.setMaxInactiveInterval(5 * 60);
                        } else {
                            listeCodesErreur.add(CodesResultatBLL.WRONG_USER_INPUTS);
                        }
                    } else {
                        listeCodesErreur.add(CodesResultatBLL.WRONG_USER_INPUTS);
                    }
                }
                if (listeCodesErreur.size() > 0) {
                    request.setAttribute("listeCodesErreur", listeCodesErreur);
                    doGet(request, response);
                }
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            LOGGER.severe("Erreur dans ServletGestionConnexionUtilisateur lors de la tentative de connexion : " + e.getMessage());
            doGet(request, response);
        }
    }

}
