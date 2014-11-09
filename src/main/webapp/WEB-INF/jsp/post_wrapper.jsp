<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="xe" uri="XmlEscape"  %>
<%@taglib prefix="security" uri="Security" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="cat" uri="CategoryChecker" %>
<%@taglib prefix="t" uri="Trimer" %>
<%@taglib prefix="te" uri="PostText" %>

<c:if test="${sessionScope.timeZone != null}">
	<fmt:setTimeZone value="${sessionScope.timeZone}" />
</c:if>

<%@ include file="post.jsp" %>