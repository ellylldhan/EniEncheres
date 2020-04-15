<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>
	<div class="card card_style">
		<div class="card-header">
			<h3 class="my_title">Mon profil</h3>
		</div>
	
		<div class="card-body">
			<c:if test="${ !empty listeCodesErreur }">
		    	<div class="alert alert-danger" role="alert">
		   			<strong>ERREUR !</strong>
		 			<ul>
						<c:forEach var="code" items="${ listeCodesErreur }">
							<li>
	    						${ LecteurMessage.getMessageErreur(code) }
	    					</li>
	    				</c:forEach>
		    		</ul>
		   		</div>
		   	</c:if>
		
			<form action="${pageContext.request.contextPath}/eni/encheres/creationProfil" method="post">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="pseudo_utilisateur">Pseudo</label>
							<input class="form-control" type="text" id="pseudo_utilisateur" name="pseudo_utilisateur" <c:if test="${ isConnected }">value="${ utilisateur.pseudo }"</c:if>>
						</div>
						
						<div class="form-group">
							<label for="prenom_utilisateur">Prénom</label>
							<input class="form-control" type="text" id="prenom_utilisateur" name="prenom_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.prenom }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="telephone_utilisateur">Téléphone</label>
							<input class="form-control" type="text" id="telephone_utilisateur" name="telephone_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.telephone }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="code_postal_utilisateur">Code postal</label>
							<input class="form-control" type="text" id="code_postal_utilisateur" name="code_postal_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.codePostal }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="mdp_utilisateur">Mot de passe</label>
							<input class="form-control" type="password" id="mdp_utilisateur" name="mdp_utilisateur">
						</div>
					</div>
					
					<div class="col-md-6">
						<div class="form-group">
							<label for="nom_utilisateur">Nom</label>
							<input class="form-control" type="text" id="nom_utilisateur" name="nom_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.nom }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="email_utilisateur">Email</label>
							<input class="form-control" type="text" id="email_utilisateur" name="email_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.email }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="rue_utilisateur">Rue</label>
							<input class="form-control" type="text" id="rue_utilisateur" name="rue_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.rue }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="ville_utilisateur">Ville</label>
							<input class="form-control" type="text" id="ville_utilisateur" name="ville_utilisateur" value="<c:if test="${ isConnected }">${ utilisateur.ville }</c:if>">
						</div>
						
						<div class="form-group">
							<label for="confirmation_mdp_utilisateur">Confirmation du mot de passe</label>
							<input class="form-control" type="password" id="confirmation_mdp_utilisateur" name="confirmation_mdp_utilisateur">
						</div>
					</div>
				</div>
				<div class="form_buttons">
					<c:choose>
						<c:when test="${ isConnected }">
							<button type="submit" class="btn btn-outline-success">Enregistrer</button>
							<a type="button" class="btn btn-outline-danger" href="${pageContext.request.contextPath}/eni/encheres/supprimerCompte?idUtilisateur=${ utilisateur.noUtilisateur }">Supprimer mon compte</a>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-outline-success">Créer</button>
							<a href="${pageContext.request.contextPath}/eni/encheres/ServletAccueil" type="button" class="btn btn-outline-danger">Annuler</a>
						</c:otherwise>
					</c:choose>
				</div>
			</form>
		</div>
	</div>
</body>
</html>