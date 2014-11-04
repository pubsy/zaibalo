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
			<%@ include file="edit_post_dialog.jsp"%>
			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<div class="profile_user_name"><c:out value="${user.displayName}" /></div>
					<div class="profile_user_avatar"><img src="image/${user.bigImgPath}" /></div>
			
					<div class="profile_info_entry">
						<span class="profile_info_field"><zmt:message key="who_where"/></span> <span class="profile_info_value"><c:out value="${user.about}"/></span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><zmt:message key="registration_date"/></span> <span class="profile_info_value"><fmt:formatDate type="date" dateStyle="MEDIUM" value="${user.date}" /></span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><zmt:message key="posts_count"/></span> <span class="profile_info_value">${post_count}</span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><zmt:message key="comments_count"/></span> <span class="profile_info_value">${comment_count}</span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><zmt:message key="posts_rate_sum"/></span> <span class="profile_info_value">${post_rating.sum}/${post_rating.count}</span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><zmt:message key="comments_rate_sum"/></span> <span class="profile_info_value">${comment_rating.sum}/${comment_rating.count}</span>
					</div>
					
					<c:if test="${sessionScope.user != null}">
						<a href="secure/dialog?to=${user.displayName}"><zmt:message key="write_a_message_to_user" /></a>
					</c:if>
					<br><br>
					<div id="profile_entries">
						<%@ include file="profile_entries_block.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>