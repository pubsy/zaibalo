<c:set var="comments_count" value="${post.getComments().size()}" />
<c:set var="comments" value="${post.comments}" />

<div id='post_${post.id}' class="post">
	<input type="hidden" name="post_id" value="${post.id}" class="post_id_hidden" />
	<div class="post_header">
				<div class="dropdown post-actions-dropdown">
					<span class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
						<span class="caret"></span>
					</span>
					<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
						<security:check>
							<c:if test="${sessionScope.user.id == post.author.id}">
								<li role="presentation">
									<button role="menuitem" tabindex="-1" class="delete-post-action"><zmt:message key="delete.post" /></button>
								</li>
							</c:if>
						</security:check>
						<li role="presentation">
							<button role="menuitem" tabindex="-1" class="post-link"><zmt:message key="direct.link" /></button>
						</li>
					</ul>
				</div>
		<img src="image/${post.author.smallImgPath}" alt="${post.author.displayName}"> 
		<a href='<c:url value="/user/${post.author.id}" />'><c:out value="${post.author.displayName}" /></a>
	</div>
	<div class="post_content">
		<div class="post_content_text"><te:escape post="${post}" /></div>
		<div class="post_rating">
			<security:check>
				<img src="img/icons/rating_1_off.gif" id="ratingDown_${post.id}"
					class="rating-button"
					onclick="javascript:ratePost(${post.id}, 'down');">
			</security:check>
			<span class="rating-text"> <zmt:message key="rating_colon" />
				<span id="rating_sum_${post.id}" class="rating_sum">${post.ratingSum}</span>
				(<span id="rating_count_${post.id}">${post.ratingCount}</span>)
			</span>
			<security:check>
				<img src="img/icons/rating_2_off.gif" id="ratingUp_${post.id}"
					class="rating-button"
					onclick="javascript:ratePost(${post.id}, 'up');">
			</security:check>
		</div>
		<div class="comments_block">
			<div id="comments_${post.id}">
				<c:forEach var="comment" items="${comments}">
					<%@ include file="comment.jsp"%>
				</c:forEach>
			</div>
			<div id="add_comment_block_${post.id}">
				<%@ include file="add_comment_block.jsp"%>
			</div>
		</div>
	</div>
</div>