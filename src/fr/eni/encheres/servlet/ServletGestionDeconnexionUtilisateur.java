package fr.eni.encheres.servlet;

import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.log.MonLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/eni/encheres/DeconnexionUtilisateur")
public class ServletGestionDeconnexionUtilisateur extends HttpServlet {

    private static Logger LOGGER = MonLogger.getLogger("ServletGestionDeconnexionUtilisateur");
    RequestDispatcher rd = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getSession().getAttribute("idUtilisateur") != null){
                request.getSession().invalidate();
            } else {
                throw new BllException(CodesResultatBLL.USER_NOT_CONNECTED);
            }
        } catch (BllException e) {
            e.printStackTrace();
            LOGGER.severe("Erreur dans ServletGestionDeconnexionUtilisateur lors de la tentative de connexion : " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/accueil");
    }
}
