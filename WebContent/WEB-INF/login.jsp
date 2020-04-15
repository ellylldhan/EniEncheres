<%--
  Created by IntelliJ IDEA.
  User: guill
  Date: 09/04/2020
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>
    <body id="login-page">
        <jsp:include page="/WEB-INF/fragments/header.jsp"></jsp:include>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-4">
                    <form action="${pageContext.request.contextPath}/eni/encheres/GestionConnexionUtilisateur" method="post">
                        <div class="form-group">
                            <label for="identifiant">Identifiant</label>
                            <input type="text" class="form-control" id="identifiant" name="identifiant">
                        </div>
                        <div class="form-group">
                            <label for="motDePasse">Password</label>
                            <input type="password" class="form-control" id="motDePasse" name="motDePasse">
                        </div>
                        <div class="from-group justify-content-center">
                            <button type="submit" class="btn btn-primary">Connexion</button>
                            <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Créer un compte</a>
                        </div>
                        <div class="form-group">
                            <a href="#" class="btn btn-secondary">Mot de passe oublié</a>
                        </div>
                        <%--<div class="form-group form-check">
                            <input type="checkbox" class="form-check-input" id="memoriser">
                            <label class="form-check-label" for="memoriser">Se souvenir de moi</label>
                        </div>--%>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
