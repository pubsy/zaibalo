<%@page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="zmt" uri="ZMT" %>
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
			<jsp:useBean id="now" class="java.util.Date"/>
			<%@ include file="edit_post_dialog.jsp"%>
			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<c:if test="${update_status != null}">
						<div class="update_status">${update_status}</div>
					</c:if>
					
					<form action="/secure_action/action.do?action=update_profile" method="post" 
					   			enctype="multipart/form-data" 
					   			name="profileSettingsForm" id="profileSettingsForm">	
					   			
						<div class="profile_settings_section_title"><zmt:message key="change_picure"/></div>
						<img src="/image/${sessionScope.user.bigImgPath}"/>
						<div class="profile_settings_attributes">
							<zmt:message key="upload_new_avatar"/>
							<div class="setting_detail"><zmt:message key="upload_new_avatar_restrictions"/></div>
							<input type="file" name="userphoto_image_file">
						</div>
						
						<div class="profile_settings_section_title"><zmt:message key="profile"/></div>
					
						<div class="profile_settings_attributes">
							<label for="user_display_name"><zmt:message key="user_display_name"/></label>
							<input type="text" value="<c:out value="${sessionScope.user.displayName}" />" name="user_display_name">
						</div>
						
						<div class="profile_settings_attributes">
							<label for="about_user"><zmt:message key="about_user"/></label>
							<input type="text" value="<c:out value="${sessionScope.user.about}" />" name="about_user">
						</div>
					
						<div class="profile_settings_attributes">
							<label for="login_name"><zmt:message key="profile_user_name_colon"/></label>
							<input type="text" value="<c:out value="${sessionScope.user.loginName}" />" readonly="readonly" name="login_name">
						</div>
						
						<div class="profile_settings_attributes">
							<label for="email"><zmt:message key="user_email"/></label>
							<input type="text" value="${sessionScope.user.email}" readonly="readonly" name="email">
						</div>
						
						<div class="profile_settings_attributes">
							<label for="notify_on_pm"><zmt:message key="notify_on_new_pm"/></label>
							<input type="checkbox" name="notify_on_pm" <c:if test="${sessionScope.user.notifyOnPM}">checked</c:if>  >
						</div>
				
						<div class="profile_settings_section_title"><zmt:message key="change_password"/></div>
						
						<div class="profile_settings_attributes">
							<label for="new_password"><zmt:message key="new_password_colon"/></label>
							<input type="password" name="new_password"/>
						</div>
					
						<div class="profile_settings_attributes">
							<label for="repeat_password"><zmt:message key="repeat_new_password_colon"/></label>
							<input type="password" name="repeat_password"/>
						</div>
						
						<input type="submit" value='<zmt:message key="save"/>'>
					</form>	
				</div>
			</div>
		</div>
	</body>
</html>
