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

    public int addUtilisateur(Utilisateur u)throws BusinessException {

            utilisateurDAO.insert(u);

        return u.getNoUtilisateur();
    }

    public void updateUtilisateur(Utilisateur u)throws BusinessException {

            utilisateurDAO.update(u);

    }

    public void removeUtilisateur(int id)throws BusinessException {
    
            utilisateurDAO.delete(id);
      
    }

    public Utilisateur getUtilisateur(int id)throws BusinessException {
        Utilisateur u = null;
        
            u = utilisateurDAO.selectById(id);
       
        return u;
    }
    

}
