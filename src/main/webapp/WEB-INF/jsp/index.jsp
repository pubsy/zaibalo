<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="cat" uri="CategoryChecker" %>
<%@taglib prefix="t" uri="Trimer" %>
<%@taglib prefix="xe" uri="XmlEscape" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
			<%@ include file="edit_post_dialog.jsp"%>
			<%@ include file="edit_comment_dialog.jsp"%>
			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<%@ include file="add_post.jsp"%>
					<%@ include file="posts_sorting.jsp"%>
					<%@ include file="postsBlock.jsp"%>
					<%@ include file="paging.jsp"%>
				</div>
			</div>
		</div>
	</body>
</html>