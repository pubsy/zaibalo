
<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="cat" uri="CategoryChecker"  %>
<%@taglib prefix="t" uri="Trimer"  %>
<%@taglib prefix="xe" uri="XmlEscape"  %>

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
					<div id="posts">
						<c:set var="hideComments" value="false" />
						<%@ include file="post.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>