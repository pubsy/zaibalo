
<c:set var="comments_count" value="${post.getComments().size()}" />
<c:set var="comments" value="${post.comments}" />

<div id='post_${post.id}' class="post">
	<input type="hidden" name="post_id" value="${post.id}" class="post_id_hidden"/>
	<div class="post_header">
		<div class="post_header_title">
			<a href='<c:url value="/post/${post.id}" />'><c:out value="${post.title}"/></a>
			<security:check>
				<c:if test="${sessionScope.user.id == post.author.id}">
					<a href="javascript:editPostShow(${post.id});"><span class="glyphicon glyphicon-wrench edit-post-icon"></span></a>
				</c:if>
			</security:check>
		</div>
		<div class="post_header_date">
			<fmt:formatDate type="date" dateStyle="SHORT" value="${post.date}" />
		</div>
	</div>
	<div style="clear: both;"></div>

	<div class="post_content">
		<div class="post_content_text"><xe:escape text="${post.content}" /></div>
		<div class="post_cat_rat">
			<div class="post_rating">
				<security:check>
					<img src="img/icons/rating_1_off.gif" id="ratingDown_${post.id}" class="rating-button" onclick="javascript:ratePost(${post.id}, 'down');">
				</security:check>
				<span class="rating-text">
					<zmt:message key="rating_colon"/> <span id="rating_sum_${post.id}" class="rating_sum">${post.ratingSum}</span> 
					(<span id="rating_count_${post.id}">${post.ratingCount}</span>)
				</span>
				<security:check>
					<img src="img/icons/rating_2_off.gif" id="ratingUp_${post.id}" class="rating-button" onclick="javascript:ratePost(${post.id}, 'up');">
				</security:check>
			</div>
			<div>
				<zmt:message key="categories_colon"/>
				<span class="post_category">
					<c:forEach items="${post.categories}" var="category">
					 	<c:set var="cat_name" value="${category.name}"/>
							<a href='<c:url value="/category/${category.id}" />'><c:out value="${cat_name}"/></a>
					</c:forEach>
				</span>
			</div>
			<security:check>
				<span id="remove_post_${post.id}"><a href="javascript:removePost(${post.id});"><zmt:message key="delete"/></a></span>
			</security:check>
		</div>

		<div class="post_author_info">
			<div>
				<img src="image/${post.author.smallImgPath}" alt="${post.author.displayName}">
			</div>
			<c:if test="${post.author.id != 2}">
				<div>
					<a href='<c:url value="/user/${post.author.id}" />'><c:out value="${post.author.displayName}" /></a>
				</div>
			</c:if>
			<c:if test="${post.author.id == 2}">
				<div><c:out value="${post.author.displayName}" /></div>
			</c:if>
		</div>

		<div style="clear: both;"></div>
		<div class="comments_block">

			<c:if test="${comments_count <= 2}">
				<div id="comment_count_text_${post.id}" class="invisible_comment_count_text"></div>
			</c:if>
			<c:if test="${comments_count > 2 && hideComments == true}">
				<div class="comment_style">
					<a href="javascript:toggleComments(${post.id});">
						<zmt:message key="show_all_comments">
							<zmt:param value="<span id='comment_count_${post.id}'>${comments_count}</span>"/>
						</zmt:message>
					</a>
				</div>
			</c:if>

			<div id="comments_${post.id}">
				<c:set var="i" value="0" />

				<c:forEach var="comment" items="${comments}">
					<c:if test="${comments_count > 2 && i == 0 && hideComments == true}">
						<div id="hidden_comments_${post.id}" style="display: none;">
					</c:if>
						<%@ include file="comment.jsp" %>

					<c:if test="${comments_count > 2 && i == comments_count -3 && hideComments == true}">
						</div>
					</c:if>
					<c:set var="i" value="${i + 1}" />
				</c:forEach>
			</div>
		<div style="clear: both;"></div>
		<div id="add_comment_block_${post.id}">
			<%@ include file="add_comment_block.jsp"%>
		</div>
	</div>
</div>
</div>
