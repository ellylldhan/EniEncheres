<%--
  Created by IntelliJ IDEA.
  User: guill
  Date: 10/04/2020
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <a class="title-link" href="${pageContext.request.contextPath}/eni/encheres/accueil">
        <h1>ENI-Encheres</h1>
    </a>
    <div class="topnav">
        <nav class="navbar navbar-expand-sm navbar-light bg-light mb-3">
            <c:choose>
                <c:when test="${!empty sessionScope.idUtilisateur}">
                    <a href="${pageContext.request.contextPath}/eni/encheres/detailProfil?idUtilisateur=${sessionScope.idUtilisateur}" class="nav-link">Mon profil</a>
                    <a href="${pageContext.request.contextPath}/eni/encheres/nouvelleVente" class="nav-link">Vendre un article</a>
                    <a href="${pageContext.request.contextPath}/eni/encheres/encheres" class="nav-link">Encheres</a>
                    <a href="${pageContext.request.contextPath}/eni/encheres/DeconnexionUtilisateur" class="nav-link">Se deconnecter</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/eni/encheres/GestionConnexionUtilisateur" class="nav-link">Se connecter</a>
                    <a href="${pageContext.request.contextPath}/eni/encheres/creationProfil" class="nav-link">Inscription</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</div>
