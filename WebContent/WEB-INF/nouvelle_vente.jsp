<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
	<div class="row">
		<div class="col-md-4">
			<br>
			
			<img src="${pageContext.request.contextPath}/images/no_image.png" class="img-thumbnail">
			<br>
		</div>
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					<h3 class="font-weight-bold">Nouvelle vente</h3>
				</div>
		    	<div class="card-body">		        	
		        	<form action="${pageContext.request.contextPath}/eni/encheres/ServletNouvelleVente" method="post">
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
							<label for="photo_article">Photo de l'article : </label>
							<button class="form-control btn btn-outline-dark" type="button">Joindre</button>
						</div>
						
						<div class="form-group">
							<label for="prix_initial_article">Mise à prix : </label>
							<input class="form-control" id="prix_initial_article" name="prix_initial_article" type="number">
						</div>
						
						<div class="form-group">
							<label for="date_debut_enchere_article">Date de début : </label>
							<input class="form-control" id="date_debut_enchere_article" name="date_debut_enchere_article" type="date">
						</div>
						
						<div class="form-group">
							<label for="date_fin_enchere_article">Date de fin : </label>
							<input class="form-control" id="date_fin_enchere_article" name="date_fin_enchere_article" type="date">
						</div>
						
						<div class="card">
							<div class="card-header">
								<h5>Retrait</h5>
							</div>
					    	<div class="card-body">	
								<div class="form-group">
									<label for="rue_retrait">Rue : </label>
									<input class="form-control" type="text" id="rue_retrait" name="rue_retrait">
								</div>
								<div class="form-group">
									<label for="code_postal_retrait">Code postal : </label>
									<input class="form-control" type="text" id="code_postal_retrait" name="code_postal_retrait">
								</div>
								<div class="form-group">
									<label for="ville_retrait">Ville : </label>
									<input class="form-control" type="text" id="ville_retrait" name="ville_retrait">
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