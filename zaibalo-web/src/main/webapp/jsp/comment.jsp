
<div class="comment_style">
	<div id="comment_${comment.id}">
		<div class="comment_avatar">
			<img src='/image/${comment.author.smallImgPath}' width="32" alt="${comment.author.displayName}">
		</div>
		
		<div class="comment_content">
			<c:if test="${comment.author.id != 2}">
				<div class="comment_author" onclick="location.href='<c:url value="/user?id=${comment.author.id}"/>';"><c:out value="${comment.author.displayName}"/></div>
			</c:if>
			<c:if test="${comment.author.id == 2}">
				<div class="comment_author"><c:out value="${comment.author.displayName}"/></div>
			</c:if>
			
			<c:if test="${profileAuthorStyle}">
				<zmt:message key="commented_on" /><a href='<c:url value="/post.do?id=${comment.postId}"/>'>"<c:out value="${comment.postTitle}"/>"</a>
			</c:if>
			
			<c:if test="${sessionScope.user.id == comment.author.id || sessionScope.user.role < 2}">
				<div class="delete_link"><a href="javascript:deleteComment(${comment.id})"><img src="/img/icons/x.png"></a></div>
			</c:if>
			
			<div class="comment_context"><xe:escape text="${comment.content}" /></div>
			
			<div style="clear:both;"></div>
			
			<div class="comment_rating">
				<c:if test="${sessionScope.user != null}">
					<span class="rating_thumbs">	
						<a href="javascript:rateComment(${comment.id}, '-1');"> 
							<span class="comment_rating_down comment_rating_img" id="commentRatingDown_${comment.id}"></span>
						</a>
						<a href="javascript:rateComment(${comment.id}, '1');">
							<span class="comment_rating_up comment_rating_img" id="commentRatingUp_${comment.id}"></span>
						</a>
					</span>
				</c:if>
				<zmt:message key="rating_colon"/>
				<span id="comment_rating_sum_${comment.id}" class="rating_text">${comment.ratingSum}</span>
				<zmt:message key="votes_colon"/>
				<span id="comment_rating_count_${comment.id}" class="rating_text">${comment.ratingCount}</span>
			</div>
			<div class="comment_date">
				<c:choose>
	 				<c:when test="${comment.date.time gt now.time - 86400000}">
						<fmt:formatDate type="time" timeStyle="SHORT" value="${comment.date}" timeZone="EET"/>
					</c:when> 
					<c:otherwise>
						<fmt:formatDate type="date" dateStyle="SHORT" value="${comment.date}" timeZone="EET"/>
					</c:otherwise>
				</c:choose>
			</div>
			<div style="clear:both;"></div>
		</div>
		<div style="clear:both;"></div>
	</div>
</div>