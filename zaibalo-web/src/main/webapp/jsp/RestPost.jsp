<%@ page import="java.util.*, ua.com.zaibalo.model.*" contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="zmt" uri="ZMT" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="cat" uri="CategoryChecker"  %>
<%@taglib prefix="t" uri="Trimer"  %>
<%@taglib prefix="xe" uri="XmlEscape"  %>

<c:set var="comments_count" value="${post.getComments().size()}" />
<c:set var="comments" value="${post.comments}" />

<div id='post_${post.id}' class="post">
	<input type="hidden" name="post_id" value="${post.id}" class="post_id_hidden"/>
	<div class="post_header">
		<div class="post_header_title">
			<a href='<c:url value="/post?id=${post.id}" />'><c:out value="${post.title}"/></a>
		</div>
		<div class="post_header_date">
			<fmt:formatDate type="both" dateStyle="MEDIUM" timeStyle="SHORT" value="${post.date}" timeZone="EET"/>
		</div>
	</div>
	<div style="clear: both;"></div>

	<div class="post_content">
		<div class="post_content_text"><xe:escape text="${post.content}" /></div>
		<div class="post_cat_rat">
			<div class="post_rating">
				<br/> 
				<c:if test="${sessionScope.user != null}">
					<div id="ratingDown_${post.id}" class="ratingDown" onclick="javascript:ratePost(${post.id}, 'down');"></div>
					<div id="ratingUp_${post.id}" class="ratingUp" onclick="javascript:ratePost(${post.id}, 'up');"></div>
				</c:if>
				<zmt:message key="rating_colon"/> <span id="rating_sum_${post.id}">${post.ratingSum}</span> 
				<zmt:message key="votes_colon"/> <span id="rating_count_${post.id}">${post.ratingCount}</span>
				<div style="clear: both;"></div>
			</div>
			<div>
				<zmt:message key="categories_colon"/>
				<span class="post_category">
					<c:forEach items="${post.categories}" var="category">
					 	<c:set var="cat_name" value="${category.name}"/>
							<a href='<c:url value="/category?categoryId=${category.id}" />'><c:out value="${cat_name}"/></a>
					</c:forEach>
				</span>
			</div>
			<c:if test="${sessionScope.user.id == post.authorId || sessionScope.user.role < 2}">
				<span id="remove_post_${post.id}"><a href="javascript:removePost(${post.id});"><zmt:message key="delete"/></a></span>
			</c:if>
			<br>
			<c:if test="${(sessionScope.user.id == post.authorId && (empty post.comments)) || sessionScope.user.role < 2}">
				<a href="javascript:editPostShow(${post.id});"><zmt:message key="edit.post"/></a>
			</c:if>
		</div>

		<div class="post_author_info">
			<div>
				<img src='/image/${post.author.smallImgPath}' alt="${post.author.displayName}">
			</div>
			<c:if test="${post.author.id != 2}">
				<div>
					<a href='<c:url value="/user?id=${post.authorId}" />'><c:out value="${post.author.displayName}" /></a>
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
						<div style="clear: both;"></div>

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
