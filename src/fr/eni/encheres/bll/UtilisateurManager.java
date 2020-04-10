package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BllException;
import fr.eni.encheres.exception.DalException;
import fr.eni.encheres.log.MonLogger;

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

    public List<Utilisateur> getUtilisateurs() {
        List<Utilisateur> utilisateurs = null;
        try {
            utilisateurs = utilisateurDAO.selectAll();
        } catch (DalException e) {
            LOGGER.severe("Erreur dans UtilisateurManager lors de la récuperation de la liste des utilisateurs : " + e.getMessage());
        }
        return utilisateurs;
    }

    public int addUtilisateur(Utilisateur u) {
        try {
            utilisateurDAO.insert(u);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans UtilisateurManager lors de l'insertion d'un utilisateur [" + u.getNom() + " " + u.getPrenom() + "] : " + e.getMessage());
        }
        return u.getNoUtilisateur();
    }

    public void updateUtilisateur(Utilisateur u) {
        try {
            utilisateurDAO.update(u);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans UtilisateurManager lors de la mise à jour de l'utilisateur [" + u.getNoUtilisateur() + "] : " + e.getMessage());
        }
    }

    public void removeUtilisateur(int id) {
        try {
            utilisateurDAO.delete(id);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans UtilisateurManager lors de la suppersion de l'utilisateur [" + id + "] : " + e.getMessage());
        }
    }

    public Utilisateur getUtilisateur(int id) {
        Utilisateur u = null;
        try {
            u = utilisateurDAO.selectById(id);
        } catch (DalException e) {
            LOGGER.severe("Erreur dans UtilisateurManager lors de la récupération de l'utilisateur [" + id + "] : " + e.getMessage());
        }
        return u;
    }
}
