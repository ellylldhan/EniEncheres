package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;
import fr.eni.encheres.log.MonLogger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        BusinessException be = new BusinessException();
        Integer noUtilisateur = null;

        if (u != null) {
            String tel = u.getTelephone();
            try {
                this.checkPseudo(u.getPseudo(), be);
                this.checkTelephone(u.getTelephone(), be);
                utilisateurDAO.insert(u);
                noUtilisateur = u.getNoUtilisateur();
            } catch (BusinessException | NumberFormatException e) {
                logger.severe("Erreur lors de l'ajout d'un utilisateur : " + e.getMessage());
                throw e;
            }
        }
        return noUtilisateur;
    }

    private void checkTelephone(String t, BusinessException be) throws BusinessException {
        try {
            Integer.parseInt(t);
            if(t.toCharArray().length != 10){
                throw be;
            }
        } catch (NumberFormatException | BusinessException e) {
            e.printStackTrace();
            logger.severe("Erreur dans UtilisateurManager dans la methode checkTelephone : " + e.getMessage());
            be.ajouterErreur(CodesResultatBLL.ERREUR_FORMAT_TELEPHONE);
            throw be;
        }
    }

    public void updateUtilisateur(Utilisateur u) throws BusinessException {
        BusinessException be = new BusinessException();

        if (u != null)
            try {
                this.checkPseudo(u.getPseudo(), be);
                utilisateurDAO.update(u);
            } catch (BusinessException e) {
                logger.severe("Erreur lors de la mise à jour d'un utilisateur : " + e.getMessage());
                throw e;
            }
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

    private void checkPseudo(String p, BusinessException be) throws BusinessException {
        byte[] bytes = null;
        try {
            bytes = p.getBytes("US-ASCII");
            for (byte b : bytes) {
                if (b < 65 || (b >90 && b<97) || b > 122){
                    be.ajouterErreur(CodesResultatBLL.PSEUDO_HAS_FORBIDDEN_CHAR);
                    throw be;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.severe("Erreur dans UtilisateurManager dans la methode checkPseudo : " + e.getMessage());
        }
    }

    public String getHashFromPassword(String pw) throws BusinessException {
        byte[] salt = {94, 41, 31, -106, -60, 93, -19, -108, 82, 98, 61, -116, 97, -72, 40, 104};
        MessageDigest md = null;
        byte[] hashedPassword = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            hashedPassword = md.digest(pw.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Erreur lors de la tentative de hachage du mot de passe : " + e.getMessage());
            BusinessException be = new BusinessException();
            be.ajouterErreur(CodesResultatBLL.ERREUR_HACHAGE);
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
