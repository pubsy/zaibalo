<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="vote">
	<div class="vote_icon">
		<c:if test="${comment_rating.value == -1}">
			<img src="/img/icons/comment_down_hover.png">
		</c:if>
		<c:if test="${comment_rating.value == 1}">
			<img src="/img/icons/comment_up_hover.png">
		</c:if>
	</div>
	<div class="vote_text">
		<fmt:message key="voted_on_comment">
			<fmt:param value="${comment_rating.userDisplayName}"/>
			<fmt:param value="${comment_rating.commentAuthorName}"/>
		</fmt:message>
		<a href='<c:url value="/post.do?id=${comment_rating.postId}#comment_${comment_rating.commentId}" />'>"${comment_rating.postTitle}"</a>
	</div>
	<div style="clear:both;"></div>
</div>