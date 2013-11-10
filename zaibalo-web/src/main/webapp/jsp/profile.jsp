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
					<div class="profile_user_name"><c:out value="${user.displayName}" /></div>
					<div class="profile_user_avatar"><img src='/image/<ue:url value="${user.bigImgPath}"/>' /></div>
			
					<div class="profile_info_entry">
						<span class="profile_info_field"><fmt:message key="who_where"/></span> <span class="profile_info_value"><c:out value="${user.about}"/></span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><fmt:message key="registration_date"/></span> <span class="profile_info_value"><fmt:formatDate type="date" dateStyle="MEDIUM" value="${user.date}" timeZone="EET"/></span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><fmt:message key="posts_count"/></span> <span class="profile_info_value">${post_count}</span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><fmt:message key="comments_count"/></span> <span class="profile_info_value">${comment_count}</span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><fmt:message key="posts_rate_sum"/></span> <span class="profile_info_value">${post_rating.sum}/${post_rating.count}</span>
					</div>
					<div class="profile_info_entry">
						<span class="profile_info_field"><fmt:message key="comments_rate_sum"/></span> <span class="profile_info_value">${comment_rating.sum}/${comment_rating.count}</span>
					</div>
					
					<c:if test="${sessionScope.user != null}">
						<a href="/secure/dialog.do?to=${user.displayName}"><fmt:message key="write_a_message_to_user" /></a>
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