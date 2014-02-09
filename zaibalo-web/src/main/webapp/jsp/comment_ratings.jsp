<%@page contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="zmt" uri="ZMT" %>

<div class="ratings-list">
	<c:choose>
	  <c:when test="${not empty commentRatings}">
		<c:forEach var="commentRating" items="${commentRatings}">
			<div class="rating">${commentRating.value} by ${commentRating.user.displayName}</div>
		</c:forEach>
	  </c:when>
	  <c:otherwise>
	    <zmt:message key="no_rates_yet"/>
	  </c:otherwise>
	</c:choose>
</div>