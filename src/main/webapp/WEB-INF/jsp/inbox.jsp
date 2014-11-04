<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="security" uri="Security" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="cat" uri="CategoryChecker" %>
<%@taglib prefix="t" uri="Trimer" %>
<%@taglib prefix="xe" uri="XmlEscape" %>

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url_base" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />

<c:if test="${sessionScope.timeZone != null}">
	<fmt:setTimeZone value="${sessionScope.timeZone}" />
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<%@include file="head.jsp"%>
	</head>
	<body>
		<div id="body">
			<jsp:useBean id="now" class="java.util.Date"/>

			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<c:choose>
						<c:when test="${empty discussions}">
							<div class="inbox_try_writing">
							<zmt:message key="no_messages_yet_try_writing">
								<zmt:param value="/secure/dialog" />
							</zmt:message>
							</div>
						</c:when>
						<c:otherwise>
							
							<div class="new_message_write_a_message"><a href="secure/dialog"><span class="glyphicon glyphicon-pencil"></span><zmt:message key="write_a_message" /></a></div>
						
							<table id="mail">
							  	<col width="5%">
					  			<col width="20%">
					  			<col width="60%">
					  			<col width="15%">
								<c:forEach var="discussion" items="${discussions}">
										<div class="comment_style dialog-row" onclick="javascript:location.href='/secure/dialog/${discussion.id}'">
												<div class="comment_avatar">
													<c:choose>
														<c:when test="${discussion.author.id eq sessionScope.user.id}">
																<img src='image/<c:out value="${discussion.recipient.smallImgPath}" />' alt='<c:out value="${discussion.recipient.displayName}"/>' width="24px" height="24px">
														</c:when>
														<c:otherwise>
																<img src='image/<c:out value="${discussion.author.smallImgPath}" />' alt='<c:out value="${discussion.author.displayName}"/>' width="24px" height="24px">
														</c:otherwise>
													</c:choose>
												</div>
								
												<div class="comment_content">
													<div class="comment_author">
														<c:choose>
															<c:when test="${discussion.author.id eq sessionScope.user.id}">
																	<c:out value="${discussion.recipient.displayName}"/>
															</c:when>
															<c:otherwise>
																	<c:out value="${discussion.author.displayName}"/>
															</c:otherwise>
														</c:choose>
													</div>
								
													<xe:escape text="${discussion.extract}" />
								
													<div class="comment_date">
														<c:choose>
											 				<c:when test="${discussion.latestMessageDate.time gt now.time - 86400000}">
																<fmt:formatDate type="time" timeStyle="SHORT" value="${discussion.latestMessageDate}" />
															</c:when> 
															<c:otherwise>
																<fmt:formatDate type="date" dateStyle="SHORT" value="${discussion.latestMessageDate}" />
															</c:otherwise>
														</c:choose>
													</div>
													<div style="clear: both;"></div>
								
												</div>
												<div style="clear: both;"></div>
										</div>
								</c:forEach>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</body>
</html>