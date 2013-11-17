<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="zmt" uri="ZMT" %>

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
		<zmt:message key="voted_on_comment">
			<zmt:param value="${comment_rating.userDisplayName}"/>
			<zmt:param value="${comment_rating.commentAuthorName}"/>
		</zmt:message>
		<a href='<c:url value="/post.do?id=${comment_rating.postId}#comment_${comment_rating.commentId}" />'>"${comment_rating.postTitle}"</a>
	</div>
	<div style="clear:both;"></div>
</div>