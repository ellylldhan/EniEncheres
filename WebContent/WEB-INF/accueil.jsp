<%@ page import="java.util.List"%>
<%@ page import="fr.eni.encheres.messages.LecteurMessage"%>
<%@ page import="fr.eni.encheres.bo.Categorie"%>
<%@ page import="fr.eni.encheres.bo.Enchere"%>
<%@ page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">

	<jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>

	<h3 class="text-center">Liste des enchères</h3>

	<form method="post" action="/EniEncheres/eni/encheres/ServletAccueil">
		<label for="recherche-article">Filtres :</label> <input
			id="recherche-article" type="text" name="recherche"
			class="form-control" placeholder="Le nom de l'article contient...">
		<div class="form_buttons">
			<button type="submit" class="btn btn-primary">Rechercher</button>
		</div>
	</form>

	<div class="card-body">
		<c:if test="${ !empty listeCodesErreur }">
			<div class="alert alert-danger" role="alert">
				<strong>ERREUR !</strong>
				<ul>
					<c:forEach var="code" items="${ listeCodesErreur }">
						<li>${ LecteurMessage.getMessageErreur(code) }</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>



	<label for="categories">Catégories :</label>
	<select class="form-control custom-select" id="categories"
		name="categories">
		<option value="" selected>Toutes</option>
		<c:forEach var="cat" items="${ listeCategories }">
			<option value="${ cat.noCategorie }">${ cat.libelle }</option>
		</c:forEach>
	</select>

	<br>

	<div>
		<!-- if listeEncheres est vide alors on affiche 'ya rien' -->

		<c:if test="${empty listeEncheres}">
			<br>
			<p>La liste des enchères pour est vide.</p>
		</c:if>

		<c:forEach items="${listeEncheres}" var="enchere">
			<!-- Carte Enchere -->
			<div class="card card-cascade narrower" style="width: 50%;">

				<!-- Card content -->
				<div class="card-body card-body-cascade">

					<!-- Nom Article -->
					<p id="carte-article-nom">
						<a href="${pageContext.request.contextPath}/eni/encheres/encheres?idArticle=${enchere.article.noArticle}" class="nav-link">
						${enchere.article.nomArticle}
						</a>
					</p>

					<!-- Contenu -->
					<p id="carte-article-prix" class="card-text">
						Prix :
						<c:if test="${enchere.article.prixVente == 0}">
							
							<c:if test="${enchere.montant_enchere == 0 }">
								${enchere.article.prixInitial}
							</c:if>
							
							<c:if test="${ enchere.montant_enchere !=0 }">
								${enchere.montant_enchere}
							</c:if>
						</c:if>
						<c:if test="${ enchere.article.prixVente != 0 }">
							${enchere.article.prixVente}
						</c:if>
					<p id="carte-article-dateFin" class="card-text">
						Fin de l'enchère : ${enchere.article.dateFinEncheres}<br />
					<p id="carte-article-vendeur" class="card-text">Vendeur :
					<a href="${pageContext.request.contextPath}/eni/encheres/detailProfil?idUtilisateur=${enchere.article.utilisateur.noUtilisateur}" class="nav-link">
						${enchere.article.utilisateur.pseudo}
					</a>
						
				</div>

			</div>
			<!-- Card Encheres -->
		</c:forEach>


	</div>


</body>
</html>
