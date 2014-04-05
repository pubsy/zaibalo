<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="xe" uri="XmlEscape"  %>

	<c:forEach var="message" items="${messages}">
		<div class="comment_style">
			<div>
				<div class="comment_avatar">
					<img src="/image/${message.author.smallImgPath}" width="32" alt='<c:out value="${message.author.displayName}"/>'>
				</div>

				<div class="comment_content">
					<a href='<c:url value="/user/${message.author.id}"/>'>
						<c:out value="${message.author.displayName}"/>
					</a>

					<div><xe:escape text="${message.text}" /></div>

					<div class="comment_date">
						<c:choose>
			 				<c:when test="${message.date.time gt now.time - 86400000}">
								<fmt:formatDate type="time" timeStyle="SHORT" value="${message.date}" timeZone="EET"/>
							</c:when> 
							<c:otherwise>
								<fmt:formatDate type="date" dateStyle="SHORT" value="${message.date}" timeZone="EET"/>
							</c:otherwise>
						</c:choose>
					</div>
					<div style="clear: both;"></div>

				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</c:forEach>