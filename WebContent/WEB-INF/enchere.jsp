<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
	<div class="row">
		<div class="col-md-4">
			<img src="${pageContext.request.contextPath}/images/no_image.png" class="img-thumbnail">
			<br>
		</div>
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					<h3 class="font-weight-bold">Nouvelle vente</h3>
				</div>
		    	<div class="card-body">		        	

		        	<h3>Détail vente</h3>
		        	<p>${ Article.nomArticle }</p>
		        	<div>
		        		<label>Description : </label>
		        		<p>${ Article.description }</p>
		        	</div>
		        	<div>
		        		<label>Catégorie : </label>
		        		<p>${ Article.categorie.libelle }</p>
		        	</div>
		        	<div>
		        		<label>Meilleur Offre: </label>
		        		<p>${MeilleurPrix}</p>
		        	</div>
					<div>
		        		<label>Mise à prix : </label>
		        		<p>${Article.prixInitial}</p>
		        	</div>					
		        	<div>
		        		<label>Fin de l'enchère : </label>
		        		<p>${Article.dateFinEncheres}</p>
		        	</div>
		        	<div>
		        		<label>Retrait : </label>
		        		<p>${Retrait.rue} ${Retrait.codePostal} ${Retrait.ville}</p>
		        	</div>		        			        	
		        	<div>
		        		<label>Vendeur : </label>
		        		<p>${Article.utilisateur.pseudo} </p>
		        	</div>
		        	<form action="${pageContext.request.contextPath}/eni/encheres/ServletEnchere" method="post">
					
		        		<div class="form-group">
							<label for="prix_initial_article">Ma proposition: </label>
							<input class="form-control" id="proposition" name="proposition" type="number" value="${MeilleurPrix}">
						</div>
		        	</form>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>