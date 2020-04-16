package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.log.MonLogger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

public class UtilisateurManager {

    private static Logger logger = MonLogger.getLogger("UtilisateurManager");
    private static UtilisateurManager INSTANCE;

    private UtilisateurDAO utilisateurDAO = DAOFactory.getUtilisateurDAO();

    public UtilisateurManager() {
    }

    public static UtilisateurManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UtilisateurManager();
        return INSTANCE;
    }

    public List<Utilisateur> getUtilisateurs() throws BusinessException {
        List<Utilisateur> utilisateurs = null;
        utilisateurs = utilisateurDAO.selectAll();
        return utilisateurs;
    }

    public int addUtilisateur(Utilisateur u) throws BusinessException {
        if (u != null && u instanceof Utilisateur) {
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

    public void updateUtilisateur(Utilisateur u) throws BusinessException {
        if (u != null && u instanceof Utilisateur)
            utilisateurDAO.update(u);
    }

    public void removeUtilisateur(int id) throws BusinessException {
        if (id != 0)
            utilisateurDAO.delete(id);
    }

    public Utilisateur getUtilisateur(int id) throws BusinessException {
        Utilisateur u = null;
        if (id != 0)
            u = utilisateurDAO.selectById(id);

        return u;
    }

    public boolean isCorrectPassword(String pw, String motDePasse) throws BusinessException {
        return pw.equals(motDePasse);
    }

    public Utilisateur getUtilisateurByArg(String login) throws BusinessException {
        Utilisateur u = null;
        try {
            u = utilisateurDAO.selectByArg(login);
        } catch (BusinessException e) {
            logger.severe("Erreur dans UtitilisateurManager lors de la récupération de l'utilisateur [" + login + "] : " + e.getMessage());
        }
        return u;
    }

    public String getHashFromPassword(String pw) throws BusinessException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = null;
        byte[] hashedPassword = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(pw.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Erreur lors de la tentative de hachage du mot de passe : " + e.getMessage());
        }
        return bytesToHex(hashedPassword);
    }

    private String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
