<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="mine" tagdir="/WEB-INF/tags" %>	
				
			<c:set var="hideComments" value="true" />
			<c:forEach items="${entrys}" var="entry">
					<c:set var="post" value="${entry}" />
					<%@ include file="post.jsp"%>
			</c:forEach>