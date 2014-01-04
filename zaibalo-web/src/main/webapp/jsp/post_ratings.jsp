<%@page contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<div class="ratings-list">
	<c:forEach var="postRating" items="${postRatings}">
		<div class="rating">${postRating.value} by ${postRating.user.displayName}</div>
	</c:forEach>
</div>