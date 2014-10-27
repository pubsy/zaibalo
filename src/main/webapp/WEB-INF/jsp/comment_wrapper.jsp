<%@page contentType="text/html; charset=UTF-8"%>

<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="xe" uri="XmlEscape"  %>

<c:if test="${sessionScope.timeZone != null}">
	<fmt:setTimeZone value="${sessionScope.timeZone}" />
</c:if>

<%@ include file="comment.jsp"%>