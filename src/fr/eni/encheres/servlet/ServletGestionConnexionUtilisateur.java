package fr.eni.encheres.servlet;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.log.MonLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/eni/encheres/GestionConnexionUtilisateur")
public class ServletGestionConnexionUtilisateur extends javax.servlet.http.HttpServlet {

    private static final String LOGIN_VIEW = "/WEB-INF/login.jsp";
    private static Logger LOGGER = MonLogger.getLogger("ServletGestionConnexionUtilisateur");
    private static UtilisateurManager um = UtilisateurManager.getInstance();
    RequestDispatcher rd = null;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        List<Integer> listCodesErreurs = new ArrayList<>();

        try {
            // Vérification de l'existance d'une session
            // return true si une session existe, sinon null
            // HttpSession session = request.getSession(false);

            // Il n'existe pas de session
            if(request.getSession().getAttribute("idUtilisateur") == null){
                Utilisateur u = null;
                String login = request.getParameter("identifiant");
                String pw = request.getParameter("motDePasse");
                boolean isCorrectPw = false;

                if(!login.isEmpty() && !pw.isEmpty()){

                    u = um.getUtilisateurByArg(login);

                    if(u != null){
                        isCorrectPw = um.isCorrectPassword(pw, u.getMotDePasse());

                        if(isCorrectPw){
                            request.getSession().setAttribute("idUtilisateur", u.getNoUtilisateur());
                        } else {
                            throw new BllException(CodesResultatBLL.WRONG_USER_INPUTS);
                        }
                    } else {
                        throw new BllException(CodesResultatBLL.USER_NOT_FOUND);
                    }
                } else {
                    throw new BllException(CodesResultatBLL.WRONG_USER_INPUTS);
                }
            }

        } catch (BusinessException e){
            e.printStackTrace();
            request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            LOGGER.severe("Erreur dans ServletGestionConnexionUtilisateur lors de la tentative de connexion : " + e.getMessage());
            doGet(request, response);
        } catch (BllException ex) {
            ex.printStackTrace();
            LOGGER.severe("Erreur dans ServletGestionConnexionUtilisateur lors de la tentative de connexion : " + ex.getMessage());
            doGet(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/accueil");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        rd = request.getRequestDispatcher(LOGIN_VIEW);
        rd.forward(request, response);
    }

    private void doDeconnection(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {
            if(request.getSession().getAttribute("idUtilisateur") != null){
                request.getSession().invalidate();
            } else {
                throw new BllException(CodesResultatBLL.USER_NOT_CONNECTED);
            }
        } catch (BllException e) {
            e.printStackTrace();
            doGet(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/detailProfil");
    }
}
