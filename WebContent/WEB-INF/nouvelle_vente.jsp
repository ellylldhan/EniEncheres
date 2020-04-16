<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>
	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h3 class="font-weight-bold">Nouvelle vente</h3>
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
		    	        	
		        	<form action="${pageContext.request.contextPath}/eni/encheres/nouvelleVente" method="post">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="nom_article">Article : </label>
									<input class="form-control" type="text" id="nom_article" name="nom_article">
								</div>
								
								<div>
									<textarea class="form-control" id="description_article" name="description_article" placeholder="Description de l'article ..."></textarea>
								</div>
								<br>
								
								<div class="form-group">
									<label for="categorie_article">Catégorie : </label>
									<select class="form-control" id="categorie_article" name="categorie_article">
										<c:forEach var="c" items="${ listeCategories }">
											<option value="${ c.noCategorie }">${ c.libelle }</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="form-group">
									<label for="prix_initial_article">Mise à prix : </label>
									<input class="form-control" id="prix_initial_article" name="prix_initial_article" type="number" value="0">
								</div>
								
								<div class="form-group">
									<label for="date_debut_enchere_article">Date de début : </label>
									<input class="form-control" id="date_debut_enchere_article" name="date_debut_enchere_article" type="date">
								</div>
								
								<div class="form-group">
									<label for="date_fin_enchere_article">Date de fin : </label>
									<input class="form-control" id="date_fin_enchere_article" name="date_fin_enchere_article" type="date">
								</div>
							</div>
							<div class="col-md-6">
								<div class="card" style="margin-top: 25px;">
									<div class="card-header">
										<h5>Retrait</h5>
									</div>
							    	<div class="card-body">	
										<div class="form-group">
											<label for="rue_retrait">Rue : </label>
											<input class="form-control" type="text" id="rue_retrait" name="rue_retrait" value="${ utilisateur.rue }">
										</div>
										<div class="form-group">
											<label for="code_postal_retrait">Code postal : </label>
											<input class="form-control" type="text" id="code_postal_retrait" name="code_postal_retrait" value="${ utilisateur.codePostal }">
										</div>
										<div class="form-group">
											<label for="ville_retrait">Ville : </label>
											<input class="form-control" type="text" id="ville_retrait" name="ville_retrait" value="${ utilisateur.ville }">
										</div>
							      	</div>
								</div>
							</div>
						</div>
						
						<div class="form_buttons">
 							<button type="submit" class="btn btn-outline-success">Enregistrer</button>
							<button type="button" class="btn btn-outline-danger">Annuler</button>
						</div>
					</form>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>