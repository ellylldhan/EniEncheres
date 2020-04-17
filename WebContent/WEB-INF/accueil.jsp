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
		<div class="form-group">
			<label for="recherche-article">Filtres :</label> <input
				id="recherche-article" type="text" name="recherche"
				class="form-control" placeholder="Le nom de l'article contient...">
		</div>

		<div class="form-group">
			<label for="categories">Catégories :</label> <select
				class="form-control custom-select" id="categories" name="categories">
				<option value="" selected>Toutes</option>
				<c:forEach var="cat" items="${ listeCategories }">
					<option value="${ cat.noCategorie }">${ cat.libelle }</option>
				</c:forEach>
			</select>
		</div>

		<c:if test="${ sessionScope.idUtilisateur != null }">
			<div class="form-row">
				<div class="form-group col-md-3" style="margin-left: 50px;">
					<div class="form-check">
						<input class="form-check-input" type="radio"
							id="typeEnchereAchatRadio" name="typeEnchereRadio"
							onchange="actualiserFormulaire(this)" checked /> <label
							class="form-check-label" for="typeEnchereAchatRadio">Achat</label>
					</div>

					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="typeEnchereAchatCheckboxOuverte"
							name="typeEnchereAchatCheckbox" value="getOuverte" /> <label
							class="form-check-label" for="typeEnchereAchatCheckboxOuverte">Enchère
							Ouverte</label>
					</div>

					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="typeEnchereAchatCheckboxEnCours"
							name="typeEnchereAchatCheckbox" value="getEnCours" /> <label
							class="form-check-label" for="typeEnchereAchatCheckboxEnCours">Enchère
							en cours</label>
					</div>

					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="typeEnchereAchatCheckboxTerminee"
							name="typeEnchereAchatCheckbox" value="getTerminee" /> <label
							class="form-check-label" for="typeEnchereAchatCheckboxTerminee">Enchère
							Terminée</label>
					</div>
				</div>

				<div class="form-group col-md-3">
					<div class="form-check">
						<input class="form-check-input" type="radio"
							id="typeEnchereVenteRadio" name="typeEnchereRadio"
							onchange="actualiserFormulaire(this)" /> <label
							class="form-check-label" for="typeEnchereAchatRadio">Vendre</label>
					</div>

					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="typeEnchereVenteCheckboxOuverte"
							name="typeEnchereVenteCheckbox" value="getOuverteVendre" disabled />
						<label class="form-check-label"
							for="typeEnchereVenteCheckboxOuverte">Enchère Ouverte</label>
					</div>


					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="typeEnchereVenteCheckboxEnCours"
							name="typeEnchereVenteCheckbox" value="getEnCoursVendre" disabled />
						<label class="form-check-label"
							for="typeEnchereVenteCheckboxEnCours">Enchère en cours</label>
					</div>

					<div class="form-check">
						<input class="form-check-input" type="checkbox"
							id="typeEnchereVenteCheckboxTerminee"
							name="typeEnchereVenteCheckbox" value="getTermineeVendre"
							disabled /> <label class="form-check-label"
							for="typeEnchereVenteCheckboxTerminee">Enchère Terminée</label>
					</div>
				</div>
			</div>
		</c:if>

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
	<div>
		<!-- if listeEncheres est vide alors on affiche qu'il n'y a pas de résultats pour la recherche -->

		<c:if test="${empty listeEncheres}">
			<br>
			<p>La liste des enchères pour est vide.</p>
		</c:if>

		<div class="row">
			<c:forEach items="${listeEncheres}" var="enchere">
				<div class="col-md-4">
					<div class="card">
						<!-- Header de la card -->
						<div class="card-header">
							<a
								href="${pageContext.request.contextPath}/eni/encheres/encheres?idArticle=${enchere.article.noArticle}">
								${ enchere.article.nomArticle } </a>
						</div>

						<!-- Body de la card -->
						<div class="card-body">
							Prix :
							<!-- Si l'article n'a pas de prix de vente -->
							<c:if test="${ enchere.article.prixVente == 0 }">
								<!-- Si aucune enchère n'a été effectuée, on affiche le prix de vente initial -->
								<c:if test="${ enchere.montant_enchere == 0 }">
									${enchere.article.prixInitial}
								</c:if>
								<!-- Sinon on affiche le prix de l'enchere -->
								<c:if test="${ enchere.montant_enchere !=0 }">
									${enchere.montant_enchere}
								</c:if>
							</c:if>

							<!-- Si l'article est vendu, on affiche le prix de vente -->
							<c:if test="${ enchere.article.prixVente != 0 }">
								${enchere.article.prixVente}
							</c:if>
							<br> Fin de l'enchère : ${ enchere.article.dateFinEncheres }
							<br> Vendeur : <a
								href="${pageContext.request.contextPath}/eni/encheres/detailProfil?idUtilisateur=${ enchere.article.utilisateur.noUtilisateur }">
								${ enchere.article.utilisateur.pseudo } </a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>


</body>

<script type="text/javascript">
	function actualiserFormulaire(element) {

		if (element.id == "typeEnchereAchatRadio") {
			for (let i = 0; i < document
					.getElementsByName("typeEnchereVenteCheckbox").length; i++) {
				document.getElementsByName("typeEnchereVenteCheckbox")[i].checked = false;
				document.getElementsByName("typeEnchereVenteCheckbox")[i].disabled = true;
				document.getElementsByName("typeEnchereAchatCheckbox")[i].disabled = false;
			}

		} else {
			for (let i = 0; i < document
					.getElementsByName("typeEnchereAchatCheckbox").length; i++) {
				document.getElementsByName("typeEnchereAchatCheckbox")[i].checked = false;
				document.getElementsByName("typeEnchereAchatCheckbox")[i].disabled = true;
				document.getElementsByName("typeEnchereVenteCheckbox")[i].disabled = false;
			}
		}

	}
</script>


</html>

