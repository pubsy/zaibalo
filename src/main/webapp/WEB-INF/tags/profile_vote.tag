<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="rating" type="ua.com.zaibalo.model.PostRating" %>
<%@ tag body-content="empty" %>

<div class="vote">
	<div class="vote_icon">
		<c:if test="${rating.value == -1}">
			<img src="img/icons/rating_1_off.gif">
		</c:if>
		<c:if test="${rating.value == 1}">
			<img src="img/icons/rating_2_off.gif">
		</c:if>
	</div>
	<div class="vote_text">
		<c:out value="${rating.userDisplayName}" /><fmt:message key="voted_on" /><a href='<c:url value="/post/${rating.postId}" />'>"<c:out value="${rating.postTitle}" />"</a>
	</div>
	<div style="clear:both;"></div>
</div>
