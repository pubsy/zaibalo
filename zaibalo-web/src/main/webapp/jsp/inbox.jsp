<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="cat" uri="CategoryChecker"  %>
<%@taglib prefix="t" uri="Trimer"  %>
<%@taglib prefix="xe" uri="XmlEscape"  %>
<%@taglib prefix="ue" uri="UrlEncode" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<%@include file="head.jsp"%>
	</head>
	<body>
		<div id="body">
			<%@ include file="left_menu.jsp"%>
			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<c:choose>
						<c:when test="${empty discussions}">
							<div class="inbox_try_writing">
							<fmt:message key="no_messages_yet_try_writing">
								<fmt:param>/secure/dialog.do</fmt:param>
							</fmt:message>
							</div>
						</c:when>
						<c:otherwise>
							
							<div class="new_message_write_a_message"><a href="/secure/dialog.do"><fmt:message key="write_a_message" /></a></div>
						
							<table id="mail">
							  	<col width="5%">
					  			<col width="20%">
					  			<col width="60%">
					  			<col width="15%">
								<c:forEach var="discussion" items="${discussions}">
								<tr <c:if test="${discussion.hasUnreadMessages}">class="unread"</c:if>>
									<td>
										<c:choose>
											<c:when test="${discussion.author.id eq sessionScope.user.id}">
												<a href='<c:url value="/user?id=${discussion.recipient.id}"/>'>
													<img src='/image/<ue:url value="${discussion.recipient.smallImgPath}" />' alt='<c:out value="${discussion.recipient.displayName}"/>' width="24px" height="24px">
												</a>
											</c:when>
											<c:otherwise>
												<a href='<c:url value="/user?id=${discussion.author.id}"/>'>
													<img src='/image/<ue:url value="${discussion.author.smallImgPath}" />' alt='<c:out value="${discussion.author.displayName}"/>' width="24px" height="24px">
												</a>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${discussion.author.id eq sessionScope.user.id}">
												<a href='<c:url value="/user?id=${discussion.recipient.id}"/>'>
													<c:out value="${discussion.recipient.displayName}"/>
												</a>
											</c:when>
											<c:otherwise>
												<a href='<c:url value="/user?id=${discussion.author.id}"/>'>
													<c:out value="${discussion.author.displayName}"/>
												</a>
											</c:otherwise>
										</c:choose>	
									</td>
									<td>
										<a href="/secure/dialog.do?discussion_id=${discussion.id}"><xe:escape text="${discussion.extract}" /></a>
									</td>
									<td>
										<c:choose>
							 				<c:when test="${discussion.latestMessageDate.time gt now.time - 86400000}">
												<fmt:formatDate type="time" timeStyle="SHORT" value="${discussion.latestMessageDate}" timeZone="EET"/>
											</c:when> 
											<c:otherwise>
												<fmt:formatDate type="date" dateStyle="SHORT" value="${discussion.latestMessageDate}" timeZone="EET"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:forEach>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</body>
</html>