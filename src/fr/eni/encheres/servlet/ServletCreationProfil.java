package fr.eni.encheres.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.ls.LSInput;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class CreationProfil
 */
@WebServlet("/eni/encheres/creationProfil")
public class ServletCreationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreationProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = null;
		//TODO: A modifier avec le token de connexion
		boolean isConnected = true;
		
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
		
		//Simulation récupération de l'utilisateur connecté
		utilisateur = utilisateurManager.getUtilisateur(4);
		
		request.setAttribute("utilisateur", utilisateur);
		request.setAttribute("isConnected", isConnected);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_profil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//TODO: A modifier avec le token de connexion
		boolean isConnected = true;
		
		//Lecture des paramètres
		String pseudo = null;
		String prenom = null;
		String nom = null;
		String email = null;
		String telephone = null;
		String rue = null;
		String codePostal = null;
		String ville = null;
		String motDePasse = null;
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		pseudo = lireParametrePseudoUtilisateur(request, listeCodesErreur);
		prenom = lireParametrePrenomUtilisateur(request, listeCodesErreur);
		nom = lireParametreNomUtilisateur(request, listeCodesErreur);
		email = lireParametreEmailUtilisateur(request, listeCodesErreur);
		telephone = lireParametreTelephoneUtilisateur(request, listeCodesErreur);
		rue = lireParametreRueUtilisateur(request, listeCodesErreur);
		codePostal = lireParametreCodePostalUtilisateur(request, listeCodesErreur);
		ville = lireParametreVilleUtilisateur(request, listeCodesErreur);
		motDePasse = lireParametreMdpUtilisateur(request, listeCodesErreur);
		
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
		} else {
			UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
			if (isConnected) {
				//TODO: A modifier lorsque les contexts de session seront implémentés
				Utilisateur utilisateurToUpdate = utilisateurManager.getUtilisateur(4);
				utilisateurToUpdate.setPseudo(pseudo);
				utilisateurToUpdate.setPrenom(prenom);
				utilisateurToUpdate.setNom(nom);
				utilisateurToUpdate.setEmail(email);
				utilisateurToUpdate.setTelephone(telephone);
				utilisateurToUpdate.setRue(rue);
				utilisateurToUpdate.setCodePostal(codePostal);
				utilisateurToUpdate.setVille(ville);
				utilisateurToUpdate.setMotDePasse(motDePasse);
				utilisateurManager.updateUtilisateur(utilisateurToUpdate);
			} else {
				int credit = 100;
				boolean administrateur = false;
				Utilisateur utilisateurToAdd = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
				utilisateurManager.addUtilisateur(utilisateurToAdd);
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/enchere.jsp");
			rd.forward(request, response);
			
		}
		
	}
	
	/**
	 * Méthode en charge de vérifier si le pseudo utilisateur est bien renseigné et unique
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametrePseudoUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String pseudo;
		pseudo = request.getParameter("pseudo_utilisateur");
		
		if (pseudo == null || pseudo.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.PSEUDO_UTILISATEUR_OBLIGATOIRE);
		} else {
			UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
			//TODO: Modifier pour le sélectionner via le pseudo et non l'id
			Utilisateur utilisateur = utilisateurManager.getUtilisateur(4);
			if (utilisateur != null) {
				listeCodesErreur.add(CodesResultatServlets.PSEUDO_UTILISATEUR_UNIQUE);
			}
		}
		
		return pseudo;
	}
	
	/**
	 * Méthode en charge de vérifier si le prénom de l'utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametrePrenomUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String prenom;
		prenom = request.getParameter("prenom_utilisateur");
		
		if (prenom == null || prenom.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.PRENOM_UTILISATEUR_OBLIGATOIRE);
		}
		
		return prenom;
	}
	
	/**
	 * Méthode en charge de vérifier si le nom de l'utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreNomUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String nom;
		nom = request.getParameter("nom_utilisateur");
		
		if (nom == null || nom.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.NOM_UTILISATEUR_OBLIGATOIRE);
		}
		
		return nom;
	}
	
	/**
	 * Méthode en charge de vérifier si l'email de l'utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreEmailUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String email;
		email = request.getParameter("email_utilisateur");
		
		if (email == null || email.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.EMAIL_UTILISATEUR_OBLIGATOIRE);
		}
		
		return email;
	}
	
	/**
	 * Méthode en charge de vérifier si le numéro de téléphone est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreTelephoneUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String telephone;
		telephone = request.getParameter("telephone_utilisateur");
		
		if (telephone == null || telephone.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.TELEPHONE_UTILISATEUR_OBLIGATOIRE);
		}
		
		return telephone;
	}
	
	/**
	 * Méthode en charge de vérifier si la rue est renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreRueUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String rue;
		rue = request.getParameter("rue_utilisateur");
		
		if (rue == null || rue.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.RUE_UTILISATEUR_OBLIGATOIRE);
		}
		
		return rue;
	}
	
	/**
	 * Méthode en charge de vérifier si le code postal est renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreCodePostalUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String codePostal;
		codePostal = request.getParameter("code_postal_utilisateur");
		
		if (codePostal == null || codePostal.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.CODE_POSTAL_UTILISATEUR_OBLIGATOIRE);
		}
		
		return codePostal;
	}
	
	/**
	 * Méthode en charge de vérifier si la ville est bien renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreVilleUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String ville;
		ville = request.getParameter("ville_utilisateur");
		
		if (ville == null || ville.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.VILLE_UTILISATEUR_OBLIGATOIRE);
		}
		
		return ville;
	}
	
	/**
	 * Méthode en charge de vérifier si le mot de passe est bien renseigné et s'il est bien confirmé
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 */
	private String lireParametreMdpUtilisateur(HttpServletRequest request, List<Integer> listeCodesErreur) {
		String mdp;
		String confirmation;
		
		mdp = request.getParameter("mdp_utilisateur");
		confirmation = request.getParameter("confirmation_mdp_utilisateur");
		
		if (mdp == null || mdp.trim().equals("") || confirmation == null || confirmation.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.MDP_UTILISATEUR_OBLIGATOIRE);
		} else {
			if (!mdp.equals(confirmation)) {
				listeCodesErreur.add(CodesResultatServlets.MDP_UTILISATEUR_CONFIRMER);
			}
		}
		
		return mdp;
	}

}
