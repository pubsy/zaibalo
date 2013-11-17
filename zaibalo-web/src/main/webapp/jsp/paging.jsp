
<c:if test="${results_size != 0}">
<div class="paging_pages">

	<c:set var="url_base" value="/?" />

	<c:forEach var='parameter' items='${paramValues}' varStatus="status"> 
		<c:if test="${parameter.key ne 'page' and parameter.key ne 'url_base'}">
			<c:set var="url_base" value="${url_base}${parameter.key}=${parameter.value[0]}&" />
		</c:if>
	</c:forEach>

	<c:set var="pages" value="start / results_size + 1" />
	<c:set var="window" value="10" />
	<c:set var="page_size" value="${param.count eq null ? 10 : param.count}" />
	<c:set var="page" value="${param.page eq null ? 1 : param.page}"/>
	<c:set var="start" value="${(page - 1) * page_size}" />
	
	<c:set var="pages" value="${results_size / page_size}" />
	<fmt:formatNumber var="pages" value="${pages + 0.5}" maxFractionDigits="0" />

	<c:set var="current_page" value="${(start / page_size ) + 1}"/>
	<fmt:formatNumber var="current_page" value="${current_page}" maxFractionDigits="0" />
	
    <c:choose>
    	<c:when test="${(current_page - 1) > (window / 2)}">
			<c:set var="left_link_count" value="${window / 2 - 1}" />
    	</c:when>
    	<c:otherwise>
    		<c:set var="left_link_count" value="${current_page - 1}" />
    	</c:otherwise>
    </c:choose>
    
    <c:set var="pageNo" value="${current_page - left_link_count}" />
    
	<c:if test="${pageNo > 1}">
		<a href="${url_base}page=1"><c:out value="1.. <<"/></a>
	</c:if>
	
	<fmt:formatNumber var="pageNo" value="${pageNo}" maxFractionDigits="0" />

	<c:forEach var="i" begin="0" end="${window-1}">
		<c:if test="${pageNo <= pages}">
			<c:choose>
				<c:when test="${pageNo == current_page}">
					<c:out value=" ${pageNo} "/>
				</c:when>
				<c:otherwise>
					<a href="${url_base}page=${pageNo}"><c:out value="${pageNo}"/></a>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:set var="pageNo" value="${pageNo + 1}"/>
	</c:forEach>
	
	<c:if test="${(pageNo - 1) < pages}">
		<a href="${url_base}page=${pages}"><c:out value=">> ..${pages}"/></a>
	</c:if>
</div>
<div class="paging_total">
	<c:choose>
		<c:when test="${start  > results_size}">
			<c:out value=" ${results_size + 1} "/>
		</c:when>
		<c:otherwise>
			<c:out value="${start + 1}"/>
		</c:otherwise>
	</c:choose>
	
	<c:out value=" - "></c:out>
	
	<c:choose>
		<c:when test="${(start + page_size) > results_size}">
			<c:out value=" ${results_size} "/>
		</c:when>
		<c:otherwise>
			<c:out value="${start + page_size}"/>
		</c:otherwise>
	</c:choose>
	
	<zmt:message key="of_total_colon" /> ${results_size}
</div>
</c:if>