<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>
<%@page session="true"%>

<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
					<form class="form-inline" role="form" action="<c:url value='j_spring_security_check' />" method="POST">
			            <c:if test="${not empty param.err}">
			                <div><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></div>
			            </c:if>
			            <c:if test="${not empty param.out}">
			                <div>You've logged out successfully.</div>
			            </c:if>
			            <c:if test="${not empty param.time}">
			                <div>You've been logged out due to inactivity.</div>
			            </c:if>
			            <div class="input-group input-group-sm">
							<label class="sr-only" for="name"><zmt:message key="login_colon"/></label> 
							<input type="text" class="form-control" name="username" placeholder="<zmt:message key='login_colon'/>">
						</div>
						<div class="input-group input-group-sm">
							<label class="sr-only" for="password"><zmt:message key="password_colon"/></label>
							<input type="password" class="form-control"	name="password" placeholder='<zmt:message key="password_colon"/>'>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="_spring_security_remember_me" ><zmt:message key="remember_me"/></label>
						</div>
						<button type="submit" class="btn btn-default login-form-element" id="submit_login_btn"><zmt:message key="login"/></button>
			        </form>
				</div>
			</div>
		</div>
	</body>
</html>