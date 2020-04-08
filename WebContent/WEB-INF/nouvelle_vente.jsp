<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
	<div class="row row_top">
		<div class="col-md-4">
			<h3>ENI-Enchères</h3>
		</div>
		<div class="col-md-5">
			<h3 class="page_title">Nouvelle vente</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-5">
			<form action="" method="post">
				<div class="form-group">
					<label for="nom_article">Article : </label>
					<input class="form-control" type="text" id="nom_article" name="nom_article">
				</div>
				<br>
				
				<div>
					<textarea class="form-control" id="description_article" name="description_article" placeholder="Description de l'article ..."></textarea>
				</div>
				<br>
				
				<div class="form-group">
					<label for="categorie_article">Catégorie : </label>
					<select class="form-control" id="categorie_article" name="categorie_article">
						<c:forEach var="c" items="${ listeCategories }">
							<option>${ c.libelle }</option>
						</c:forEach>
					</select>
				</div>
				<br>
				
				<div class="form-group">
					<label for="photo_article">Photo de l'article : </label>
					<button class="form-control btn" type="button">UPLOADER</button>
				</div>
				<br>
				
				<div class="form-group">
					<label for="prix_initial_article">Mise à prix : </label>
					<input class="form-control" id="prix_initial_article" name="prix_initial_article" type="number">
				</div>
				<br>
				
				<div class="form-group">
					<label for="date_debut_enchere_article">Date de début : </label>
					<input class="form-control" id="date_debut_enchere_article" name="date_debut_enchere_article" type="date">
				</div>
				<br>
				
				<div class="form-group">
					<label for="date_fin_enchere_article">Date de fin : </label>
					<input class="form-control" id="date_fin_enchere_article" name="date_fin_enchere_article" type="date">
				</div>
			</form>
		</div>
	</div>
</body>
</html>