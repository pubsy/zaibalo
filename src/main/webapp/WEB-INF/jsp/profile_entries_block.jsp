			<c:forEach items="${entrys}" var="entry">
					<c:set var="post" value="${entry}" />
					<%@ include file="post.jsp"%>
			</c:forEach>