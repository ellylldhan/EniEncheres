package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class UtilisateurManager {

    private static Logger LOGGER = MonLogger.getLogger("UtilisateurManager");
    private static UtilisateurManager INSTANCE;

    private UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

    public UtilisateurManager() { }

    public static UtilisateurManager getInstance() {
        if(INSTANCE == null)
            INSTANCE = new UtilisateurManager();
        return INSTANCE;
    }

    public List<Utilisateur> getUtilisateurs() throws BusinessException{
        List<Utilisateur> utilisateurs = null;
        utilisateurs = utilisateurDAO.selectAll();
        return utilisateurs;
    }

    public int addUtilisateur(Utilisateur u) throws BusinessException {
        if(u != null && u instanceof Utilisateur){
            String tel = u.getTelephone();
            try {
                Integer.parseInt(tel);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
            utilisateurDAO.insert(u);
        return u.getNoUtilisateur();
    }

    public void updateUtilisateur(Utilisateur u)throws BusinessException {
        if(u != null && u instanceof Utilisateur)
            utilisateurDAO.update(u);
    }

    public void removeUtilisateur(int id)throws BusinessException {
        if(id != 0)
            utilisateurDAO.delete(id);
    }

    public Utilisateur getUtilisateur(int id) throws BusinessException {
        Utilisateur u = null;
        if(id != 0)
            u = utilisateurDAO.selectById(id);
       
        return u;
    }

    public boolean isCorrectPassword(String pw, String motDePasse) throws BusinessException {
        if(pw.equals(motDePasse))
            return true;
        else {
        	return false;
        }
    }

    public Utilisateur getUtilisateurByArg(String login) throws BusinessException{
        Utilisateur u = null;
        try {
            u= utilisateurDAO.selectByArg(login);
        } catch (BusinessException e) {
            LOGGER.severe("Erreur dans UtitilisateurManager lors de la récupération de l'utilisateur [" + login + "] : " + e.getMessage());
        }
        return u;
    }
}
