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
			<%@ include file="left_menu.jsp"%>
			<div id="right_body">
				<%@ include file="banner.jsp"%>
				<div class="content">
					<div class="dialog_messaging_with" <c:if test="${names != null}">style="display:none;"</c:if> >
						<zmt:message key="messaging_with" />
						<span class="dialog_messaging_with_name">${other_user_name}...</span>
					</div>
					
					<c:if test="${names == null}">
						<input type="hidden" id="cb_identifier" value='<c:out value="${other_user_name}"/>'>
					</c:if>
					
					<c:if test="${names != null}">
						<div class="combobox">
							<input type="text" placeholder="<zmt:message key="recipient_name"/>" id="cb_identifier" value='<c:out value="${param.to}" />' autocomplete="off"> 
							<span class="new_message_arrow">&#9660</span>
							<div class="dropdownlist">
							<c:forEach var="name" items="${names}">
								<a><c:out value="${name}"/></a>
							</c:forEach>
							</div>
						</div>
						<script type="text/javascript">
							var no = new ComboBox("cb_identifier");
						</script>
					</c:if>
					<br>
					<div class="comment_style">
						<div class="comment_avatar">
							<img src='/image/${sessionScope.user.smallImgPath}' width="32">
						</div>
						<div class="comment_content">
								<textarea placeholder="<zmt:message key="message_text_three_dots"/>" rows="1" id="text"></textarea>
								<input type="button" onclick="javascript:sendMessage(); return;" value="<zmt:message key='send' />" style="float: right;">
								<div id="loading_gif_${param.post_id}" class="add_comment_loading" style="display: none;">
									<img src="/img/icons/loading.gif" />
								</div>
						</div>
						<div style="clear: both;"></div>
					</div>
				
					<div id="dialog_messages">
						<%@include file="/jsp/messagesBlock.jsp" %>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>