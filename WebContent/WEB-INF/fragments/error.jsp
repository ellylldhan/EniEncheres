
<%@ page import="fr.eni.encheres.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${!empty listeCodesErreur}">
	<div class="container">
		<div class="alert alert-danger" role="alert">
			<c:forEach items="${listeCodesErreur}" var="codeErreur">
				<p>	${LecteurMessage.getMessageErreur(codeErreur)}</p>
			</c:forEach>
		</div>
	</div>
</c:if>