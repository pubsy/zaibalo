
	<div id="posts">
		<c:set var="profileAuthorStyle" value="false" />
		<c:forEach items="${posts}" var="post">
			<c:set var="hideComments" value="true" />
			<%@ include file="post.jsp"%>
		</c:forEach>
	</div>