<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>
<%@page session="true"%>

<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="security" uri="Security" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="cat" uri="CategoryChecker" %>
<%@taglib prefix="t" uri="Trimer" %>
<%@taglib prefix="xe" uri="XmlEscape" %>
<%@taglib prefix="te" uri="PostText" %>

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
			<%@ include file="edit_post_dialog.jsp"%>
			<%@ include file="edit_comment_dialog.jsp"%>
			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<div class="centered login-form">
						<form role="form" action="<c:url value='authenticate' />" method="POST">
							<input type="hidden" value="${successRedirectURL}" name="successRedirectURL"/>
				            <div class="form-group">
								<label class="sr-only" for="username"><zmt:message key="login_colon"/></label> 
								<input type="text" class="form-control" name="username" id="username" placeholder="<zmt:message key='login_colon'/>">
							</div>
							<div class="form-group">
								<label class="sr-only" for="password"><zmt:message key="password_colon"/></label>
								<input type="password" class="form-control"	name="password" id="password"placeholder='<zmt:message key="password_colon"/>'>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="remember_me" ><zmt:message key="remember_me"/></label>
							</div>
							<button type="submit" class="btn btn-custom btn-lg btn-block" id="submit-btn"><zmt:message key="login"/></button>
							<c:if test="${not empty authenticationError}">
							      <div class="login-error">
							        <zmt:message key="login_unsuccessful_due_to"/>
							        <c:out value="${authenticationError}"/>
							      </div>
							</c:if>
				        </form>
				        <div class="login-form-element register-forgot-block">
							<a href="javascript:remindPassDialogShow()"><zmt:message key="forgot_password_qm"/></a>
							<a href="javascript:registerDialogShow();"><zmt:message key="register"/></a>
						</div>
			        </div>
				</div>
			</div>
		</div>
	</body>
</html>