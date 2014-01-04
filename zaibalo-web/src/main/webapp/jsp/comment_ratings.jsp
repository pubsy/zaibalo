<%@page contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<div class="ratings-list">
	<c:forEach var="commentRating" items="${commentRatings}">
		<div class="rating">${commentRating.value} by ${commentRating.user.displayName}</div>
	</c:forEach>
</div>