<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body>
	<div class="row">
		<div class="col-md-4">
			<h3>ENI-Enchères</h3>
		</div>
		<div class="col-md-4">
			<h3 class="font-weight-bold">Nouvelle vente</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form action="" method="post">
				<div class="form-group">
					<label for="nom_article">Article : </label>
					<input type="text" id="nom_article" name="nom_article">
				</div>
				<div class="form-group">
					<label for="description_article">Description : </label>
					<input type="text" id="description_article" name="description_article">
				</div>
				<div class="form-group">
					<label for="categorie_article">Catégorie : </label>
					<select id="categorie_article" name="categorie_article">
						<c:forEach var="c" items="${ listeCategories }">
							<option>${ c.libelle }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="photo_article">Photo de l'article : </label>
					<button>UPLOADER</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>