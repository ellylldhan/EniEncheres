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
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import fr.eni.encheres.exception.CodesResultatBLL;

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
		List<Integer> listeCodesErreur = new ArrayList<>();
		
		int idUtilisateur = -1;
		Utilisateur utilisateur = null;
		boolean isConnected = false;
		
		//Vérification de l'existance d'une session, retourne true si oui, sinon null
		HttpSession session = request.getSession();
		
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_profil.jsp");
			rd.forward(request, response);
		} else {
			//Si on a une session
			if (session.getAttribute("idUtilisateur") != null) {
				isConnected = true;
				
				try {
					UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
					idUtilisateur = (int) session.getAttribute("idUtilisateur");
					
					utilisateur = utilisateurManager.getUtilisateur(idUtilisateur);
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("listeCodesErreur", listeCodesErreur);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_profil.jsp");
					rd.forward(request, response);
				}
				
				request.setAttribute("utilisateur", utilisateur);
			}
		
			request.setAttribute("isConnected", isConnected);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_profil.jsp");
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int idUtilisateur = -1;
		boolean isConnected = false;
		
		//Vérification de l'existance d'une session, retourne true si oui, sinon null
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			isConnected = true;
		}
		
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
		
		pseudo = lireParametrePseudoUtilisateur(request, response, listeCodesErreur);
		prenom = lireParametrePrenomUtilisateur(request, response, listeCodesErreur);
		nom = lireParametreNomUtilisateur(request, response, listeCodesErreur);
		email = lireParametreEmailUtilisateur(request, response, listeCodesErreur);
		telephone = lireParametreTelephoneUtilisateur(request, response, listeCodesErreur);
		rue = lireParametreRueUtilisateur(request, response, listeCodesErreur);
		codePostal = lireParametreCodePostalUtilisateur(request, response, listeCodesErreur);
		ville = lireParametreVilleUtilisateur(request, response, listeCodesErreur);
		motDePasse = lireParametreMdpUtilisateur(request, response, listeCodesErreur);
		
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur", listeCodesErreur);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creation_profil.jsp");
			rd.forward(request, response);
		} else {
			UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
			
			if (isConnected) {
				try {
					
					idUtilisateur = (int) session.getAttribute("idUtilisateur");
					
					Utilisateur utilisateurToUpdate;
					utilisateurToUpdate = utilisateurManager.getUtilisateur(idUtilisateur);
				
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
					
				} catch (BusinessException e1) {
					e1.printStackTrace();
					request.setAttribute("listeCodesErreur",e1.getListeCodesErreur());
					doGet(request, response);
				}
			} else {
				int credit = 100;
				boolean administrateur = false;
				Utilisateur utilisateurToAdd = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
				
				try {
					utilisateurManager.addUtilisateur(utilisateurToAdd);
				} catch (BusinessException e) {
					e.getMessage();
					request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
					doGet(request, response);
				}
			}
			
			response.sendRedirect(request.getContextPath() + "/eni/encheres/accueil");
			
		}
		
	}
	
	/**
	 * Méthode en charge de vérifier si le pseudo utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametrePseudoUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String pseudo;
		pseudo = request.getParameter("pseudo_utilisateur");
		
		if (pseudo == null || pseudo.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.PSEUDO_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return pseudo;
	}
	
	/**
	 * Méthode en charge de vérifier si le prénom de l'utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametrePrenomUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String prenom;
		prenom = request.getParameter("prenom_utilisateur");
		
		if (prenom == null || prenom.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.PRENOM_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return prenom;
	}
	
	/**
	 * Méthode en charge de vérifier si le nom de l'utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreNomUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String nom;
		nom = request.getParameter("nom_utilisateur");
		
		if (nom == null || nom.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.NOM_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return nom;
	}
	
	/**
	 * Méthode en charge de vérifier si l'email de l'utilisateur est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreEmailUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String email;
		email = request.getParameter("email_utilisateur");
		
		if (email == null || email.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.EMAIL_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return email;
	}
	
	/**
	 * Méthode en charge de vérifier si le numéro de téléphone est bien renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreTelephoneUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String telephone;
		telephone = request.getParameter("telephone_utilisateur");
		
		if (telephone == null || telephone.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.TELEPHONE_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
        try {
            Integer.parseInt(telephone);
        } catch (NumberFormatException e) {
    		listeCodesErreur.add(CodesResultatServlets.TEL_NON_VALIDE);

        }
		
		return telephone;
	}
	
	/**
	 * Méthode en charge de vérifier si la rue est renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreRueUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String rue;
		rue = request.getParameter("rue_utilisateur");
		
		if (rue == null || rue.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.RUE_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return rue;
	}
	
	/**
	 * Méthode en charge de vérifier si le code postal est renseigné
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreCodePostalUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String codePostal;
		codePostal = request.getParameter("code_postal_utilisateur");
		
		if (codePostal == null || codePostal.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.CODE_POSTAL_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return codePostal;
	}
	
	/**
	 * Méthode en charge de vérifier si la ville est bien renseignée
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreVilleUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String ville;
		ville = request.getParameter("ville_utilisateur");
		
		if (ville == null || ville.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.VILLE_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		}
		
		return ville;
	}
	
	/**
	 * Méthode en charge de vérifier si le mot de passe est bien renseigné et s'il est bien confirmé
	 * @param request
	 * @param listeCodesErreur
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private String lireParametreMdpUtilisateur(HttpServletRequest request, HttpServletResponse response, List<Integer> listeCodesErreur) throws ServletException, IOException {
		String mdp;
		String confirmation;
		
		mdp = request.getParameter("mdp_utilisateur");
		confirmation = request.getParameter("confirmation_mdp_utilisateur");
		
		if (mdp == null || mdp.trim().equals("") || confirmation == null || confirmation.trim().equals("")) {
			listeCodesErreur.add(CodesResultatServlets.MDP_UTILISATEUR_OBLIGATOIRE);
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			doGet(request, response);
		} else {
			if (!mdp.equals(confirmation)) {
				listeCodesErreur.add(CodesResultatServlets.MDP_UTILISATEUR_CONFIRMER);
				request.setAttribute("listeCodesErreur",listeCodesErreur);
				doGet(request, response);
			}
		}
		
		return mdp;
	}

}
