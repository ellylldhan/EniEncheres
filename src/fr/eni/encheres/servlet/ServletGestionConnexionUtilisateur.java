package fr.eni.encheres.servlet;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.log.MonLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/eni/encheres/GestionConnexionUtilisateur")
public class ServletGestionConnexionUtilisateur extends javax.servlet.http.HttpServlet {

    private static Logger LOGGER = MonLogger.getLogger("ServletGestionConnexionUtilisateur");
    private static UtilisateurManager um = UtilisateurManager.getInstance();
    RequestDispatcher rd = null;

    protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        Utilisateur u = null;
        String login = request.getParameter("identifiant");
        String pw = request.getParameter("motDePasse");
        boolean isCorrectPw = false;

        if(login != null && !login.isEmpty() && !pw.isEmpty()){
            try {
                u = um.getUtilisateurByArg(login);

                if(u != null){
                    if(login.equals(u.getPseudo()) || login.equals(u.getEmail())){
                        isCorrectPw = um.isCorrectPassword(pw, u.getMotDePasse());

                        if(isCorrectPw){
                            HttpSession session = request.getSession();
                            session.setAttribute("id", u.getNoUtilisateur());
                        } else {

                            // TODO : Message erreur

                            doGet(request, response);
                        }
                    }
                }
            } catch (BllException e) {
                LOGGER.severe("Erreur dans ServletGestionConnexionUtilisateur lors de la tentative de connexion : " + e.getMessage());
                doGet(request, response);
            }
        }
        rd = request.getRequestDispatcher("/WEB-INF/acceuil.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
        rd.forward(request, response);
    }
}
