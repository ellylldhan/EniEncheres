<%@ page import="java.util.List"%>
<%@ page import="fr.eni.encheres.bo.Categorie"%>
<%@ page import="fr.eni.encheres.bo.Enchere"%>
<%@ page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">

	<div class="topnav">
		<nav class="navbar navbar-expand-sm navbar-light bg-light mb-3">
		<c:choose>
		<c:when test="${empty sessionScope.utilisateur.pseudo}">
		<!-- si pas de pseudo alors affiche liens vers inscription/connection -->
		              <a class="nav-link" href="${pageContext.request.contextPath}/ServletInscription">S'inscrire</a>
		              <a class="nav-link" href="${pageContext.request.contextPath}/ServletConnexion">Se Connecter</a>
		</c:when>
		<c:otherwise>
		<!-- sinon affiche pseudo avec lien vers profil utilisateur -->
			<a class="nav-link" href="${pageContext.request.contextPath}/ServletDetailProfil">${sessionScope.utilisateur.pseudo}</a>
		</c:otherwise>
		</c:choose>
		</nav>
	</div>


	<h3 class="text-center">Liste des enchères</h3>

	<form method="post" action="#recherche">
		<label for="recherche-article">Filtres :</label> <input
			id="recherche-article" type="text" name="recherche"
			placeholder="Le nom de l'article contient..."> <input
			id="btn-recherche" type="submit" value="Rechercher" />
	</form>

	<label for="categories">Catégories :</label>
	<select class="form-control custom-select" id="categories"
		name="categories">
		<option value="" selected>Toutes</option>
		<c:forEach var="c" items="${ listeCategories }">
			<option value="${ c.noCategorie }">${ c.libelle }</option>
		</c:forEach>
	</select>

	<br>

	<div>
		<!-- if listeEncheres est vide alors on affiche 'ya rien' -->

		<c:if test="${empty listeEncheres }">
			<br>
			<h3>La liste des enchères pour est vide.</h3>
		</c:if>

		<c:forEach items="${listeEncheres}" var="enchere">
			<!-- Carte Enchere -->
			<div class="card card-cascade narrower" style="width: 50%;">

				<!-- Card image -->
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/images/no_image.png"
						class="img-thumbnail">
				</div>

				<!-- Card content -->
				<div class="card-body card-body-cascade">

					<!-- Nom Article -->
					<p id="carte-article-nom">${enchere.getArticle.getNomArticle}</p>

					<!-- Contenu -->
					<p id="carte-article-prix" class="card-text">Prix :
						${enchere.getBestEnchereByIdArticle(enchere.getArticle.getNoArticle)}</p>
					<p id="carte-article-dateFin" class="card-text">Fin de
						l'enchère : ${enchere.getArticle.dateFinEncheres}</p>
					<br />
					<p id="carte-article-vendeur" class="card-text">Vendeur :
						${enchere.getUtilisateur.getPseudo}</p>

				</div>

			</div>
			<!-- Card Encheres -->
		</c:forEach>
	</div>


</body>
</html>