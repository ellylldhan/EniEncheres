<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>


<body class="container">
${request.setAttribute("Article", Article) }
	<div class="row">
		<div class="col-md-4">
			<img src="${pageContext.request.contextPath}/images/no_image.png" class="img-thumbnail">
			<br>
		</div>
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
				<c:if test="${!fini}">
					<h3 class="font-weight-bold">Nouvelle Enchère</h3>
				</c:if>
				<c:if test="${fini && win}"><h3 class="font-weight-bold">Vous avez remporté la vente</h3></c:if>
				<c:if test="${fini && !win}"><h3 class="font-weight-bold">${Enchere.utilisateur.pseudo} à remporté la vente</h3></c:if>
				</div>
		    	<div class="card-body">		        	

		        	<h3>Détail de l'enchère</h3>
		        	<p>${ Article.nomArticle }</p>
		        	<div>
		        		<label>Description : </label>
		        		<p>${ Article.description }</p>
		        	</div>
		        	<c:if test="${!fini}">
			        	<div>
			        		<label>Catégorie : </label>
			        		<p>${ Article.categorie.libelle }</p>
			        	</div>
		        	</c:if>
		        	<div>
		        		<label>Meilleur Offre: </label>
		        		<p>${Enchere.montant_enchere} 
		        			<c:if test="${!win}"> 
		        				par ${Enchere.utilisateur.pseudo}
		        			</c:if>
		        		
		        		</p>
		        	</div>
					<div>
		        		<label>Mise à prix : </label>
		        		<p>${Article.prixInitial}</p>
		        	</div>
		        	<c:if test="${!win}">					
			        	<div>
			        		<label>Fin de l'enchère : </label>
			        		<p>${Article.dateFinEncheres}</p>
			        	</div>			        	
		        	</c:if>
		        	<div>
		        		<label>Retrait : </label>
		        		<p>${Retrait.rue} ${Retrait.codePostal} ${Retrait.ville}</p>
		        	</div>		        			        	
		        	<div>
		        		<label>Vendeur : </label>
		        		<p>${Article.utilisateur.pseudo} </p>
		        	</div>
		        	<c:if test="${win}">
			        	<div>
			        		<label>tel : </label>
			        		<p>${Article.utilisateur.telephone} </p>
			        	</div>
		        	</c:if>
		        	<c:if test="${!fini}">
			        	<form action="${pageContext.request.contextPath}/eni/encheres/encheres" method="post">
							<input type="hidden" name="IdArticle" value="${IdArticle}" />
			        		<div class="form-group">
								<label for="prix_initial_article">Ma proposition: </label>
								<input class="form-control" id="proposition" name="proposition" type="number" value="${Enchere.montant_enchere}">
							</div>
							<div class="form_buttons">
	 							<button type="submit" class="btn btn-outline-success">Envoyer</button>
							</div>
			        	</form>
		        	</c:if>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>