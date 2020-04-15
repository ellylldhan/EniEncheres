<%--
  Created by IntelliJ IDEA.
  User: guill
  Date: 10/04/2020
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container header">
    <h1>ENI-Encheres</h1>
    <div>
        <c:if test="${!empty sessionScope.idUtilisateur}">
            <a href="${pageContext.request.contextPath}/eni/encheres/DeconnexionUtilisateur" class="btn btn-primary">Se deconnecter</a>
        </c:if>
        <c:if test="${empty sessionScope.idUtilisateur}">
            <a href="${pageContext.request.contextPath}/eni/encheres/GestionConnexionUtilisateur" class="btn btn-primary">Se connecter</a>
            <a href="${pageContext.request.contextPath}/eni/encheres/creationProfil" class="btn btn-primary">Inscription</a>
        </c:if>
    </div>
</div>
