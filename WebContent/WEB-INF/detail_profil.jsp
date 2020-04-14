<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
	<div class="card card_style">
		<div class="card-header">
			<h3 class="my_title">Détail du profil</h3>
		</div>
		
		<div class="card-body">
			<div class="div_detail_profil_style">Pseudo : ${ utilisateur.pseudo }</div>
			<div class="div_detail_profil_style">Nom : ${ utilisateur.nom }</div>
			<div class="div_detail_profil_style">Prénom : ${ utilisateur.prenom }</div>
			<div class="div_detail_profil_style">Email : ${ utilisateur.email }</div>
			<div class="div_detail_profil_style">Téléphone : ${ utilisateur.telephone }</div>
			
			<c:if test="${ isMyAccount }">
				<div class="div_detail_profil_style">Rue : ${ utilisateur.rue }</div>
				<div class="div_detail_profil_style">Code postal : ${ utilisateur.codePostal }</div>
				<div class="div_detail_profil_style">Ville : ${ utilisateur.ville }</div>
				
				<div class="text-center">
					<a class="btn btn-outline-dark btn_style" type="button" href="${pageContext.request.contextPath}/eni/encheres/ServletCreationProfil">Modifier</a>
				</div>
			</c:if>
		</div>
	</div>	
</body>
</html>