<%@ page import="java.util.List" %>
<%@ page import="fr.eni.encheres.bo.Categorie" %>
<%@ page import="fr.eni.encheres.bo.Enchere" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%! private List<Categorie> listeCategories; %>
<%! private List<Enchere> listeEncheres; %>

<html>
<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

	
<% listeEncheres = (List<Enchere>) request.getAttribute("listeEncheres"); %>
<% listeCategories = (List<Categorie>) request.getAttribute("listeCategories"); %>

<body class="container">
    <h2 class="text-center"> Liste des enchères </h2>
    <div class="row">
        
        <div class="col-sm-6">
            <h3> Filtres </h3>
            <div class="col-sm-8">
                <label for="categories">Catégories</label>
                <select class="form-control custom-select" id="categories" name="categories">
                    <option value="" selected>Toutes</option>
                    <c:forEach var="c" items="${ listeCategories }">
                        <option value="${ c.noCategorie }">${ c.libelle }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <br>
    <div class="col-sm-4">
            <input class="form-control" name="txtRechercher" value="" type="search"/>
        </div>
    </div>
    <br>
    <div>
        <c:forEach items="${listeEncheres}" var="enchere">
            <!-- Card Narrower -->
            <div class="card card-cascade narrower" style="width: 50%;">

                <!-- Card image -->
		<div class="col-md-4">
			<img src="${pageContext.request.contextPath}/images/no_image.png" class="img-thumbnail">
		</div>

                <!-- Card content -->
                <div class="card-body card-body-cascade">

                    <!-- Label -->
                    <h4 class="font-weight-bold card-title">${enchere.getArticle.getNomArticle()}</h4>
                    <!-- Contenu -->
                    <p class="card-text">Prix : ${enchere.getMontant_Enchere()}</p>
                    <p class="card-text">Fin de l'enchère : ${enchere.getArticle.dateFinEncheres()}</p>
                    <br/>
                    <p class="card-text">Vendeur : ${enchere.getUtilisateur.getPseudo()}</p>
                </div>

            </div>
            <!-- Card Narrower -->
        </c:forEach>
    </div>
</div>


</body>
</html>