
	<div id="posts">
		<c:forEach items="${posts}" var="post">
			<c:set var="hideComments" value="true" />
			<%@ include file="post.jsp"%>
		</c:forEach>
	</div>