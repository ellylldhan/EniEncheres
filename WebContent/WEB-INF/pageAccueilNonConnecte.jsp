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

<body>

<% listeEncheres = (List<Enchere>) request.getAttribute("listeEncheres"); %>
<% listeCategories = (List<Categorie>) request.getAttribute("listeCategories"); %>


<div class="container-fluid">
    <h1 class="text-center"> Liste des enchères </h1>
    <div class="row">
        <div class="col-sm-6">
            <h3> Filtres </h3>
            <div class="col-sm-8">
                <label for="categories">Catégories</label>
                <select class="form-control custom-select" id="categories" name="categories">
                    <option value="" disabled selected>Toutes</option>
                    <c:forEach var="c" items="${ listeCategories }">
                        <option value="${ c.noCategorie }">${ c.libelle }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <br>


        <div class="form-group">
            <label for="categorie_article">Catégorie : </label>
            <select class="form-control" id="categorie_article" name="categorie_article">
                <c:forEach var="c" items="${ listeCategories }">
                    <option value="${ c.noCategorie }">${ c.libelle }</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-sm-4">
            <input class="form-control" name="txtRechercher" value="Rechercher"/>
        </div>
    </div>
    <br>
    <div>
        <c:forEach items="${lesEncheres}" var="enchere">
            <!-- Card Narrower -->
            <div class="card card-cascade narrower" style="width: 50%;">

                <!-- Card image -->
                <div class="view view-cascade overlay">
                    <img class="card-img-top" src="../img/articles/pc_gamer.jpg"
                         alt="Card image cap" style="width: 25%">
                    <a>
                        <div class="mask rgba-white-slight"></div>
                    </a>
                </div>

                <!-- Card content -->
                <div class="card-body card-body-cascade">

                    <!-- Label -->
                    <h5 class="pink-text pb-2 pt-1">${enchere.getUnArticleVendu().getNomArticle()}</h5>
                    <!-- Title -->
                    <h4 class="font-weight-bold card-title">Prix : ${enchere.getUnArticleVendu().getMiseAPrix()}</h4>
                    <!-- Text -->
                    <p class="card-text">${enchere.getUnArticleVendu().getDescription()}</p>
                    <!-- Button -->
                    <a class="btn btn-unique">Button</a>

                </div>

            </div>
            <!-- Card Narrower -->
        </c:forEach>
    </div>
</div>


</body>
</html>