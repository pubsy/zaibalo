			<jsp:useBean id="now" class="java.util.Date"/>
		
			<div id="message"></div>
			<div id="edit_post_dialog" style="display:none;">
				<input type="hidden" id="edit_post_id" value="">
				<input type="text" style="margin: 0px 0px 5px 0px; width: 100%;" id="edit_post_title">
				<textarea rows=6 style="width: 100%; overflow-y: scroll;" id="edit_post_content"></textarea>
				<br><br>
				<div class="edit_post_cat_title">
					<fmt:message key="categories_colon"/>&nbsp;<span id="edit_post_category"></span>
				</div>
				<div>
					<select id="edit_category_select">
						<c:forEach var="category" items="${applicationScope.categories}">
							<option><c:out value="${category.name}"/></option>
						</c:forEach>
					</select>
					<input type="button" value="Додати" onclick="javascript:addSelectedCategoryToEditPost();">
					<input type="text" id="edit_post_tags_input" placeholder='<fmt:message key="write_tags_separated"/>' />
					<input type="button" value="Додати" onclick="javascript:addTagToEditPost();">
				</div>
				<div id="edit_post_validation"></div>
			</div>
		
			<div id="left_body">
				<div class="sidebar_block">
					<div class="sidebar_block_title"><fmt:message key="search" /></div>
					<div class="sidebar_block_body">
						<form action="http://www.google.com.ua" id="cse-search-box">
						  <div>
						    <input type="hidden" name="cx" value="partner-pub-2053586890395148:2934386286" />
						    <input type="hidden" name="ie" value="UTF-8" />
						    <input type="text" name="q" size="55" />
						    <input type="submit" name="sa" value="Search" />
						  </div>
						</form>
						
						<script type="text/javascript" src="http://www.google.com.ua/coop/cse/brand?form=cse-search-box&amp;lang=uk"></script>

					</div>
				</div>
				
				<div class="sidebar_block">
					<c:if test="${sessionScope.user == null}">
						<%@include file="login_block.jsp" %>
					</c:if>
					<c:if test="${sessionScope.user != null}">
						<%@include file="login_status.jsp" %>
					</c:if>
				</div>

				<div class="sidebar_block">
					<div class="sidebar_block_title"><fmt:message key="categories_colon" /></div>
					<div class="sidebar_block_body">
						<c:forEach var="category" items="${applicationScope.categories}">
							<input type="checkbox" class="category_checkbox" value="${category.id}" <cat:checker id="${category.id}" queryString="${pageContext.request.queryString}" url="${pageContext.request.servletPath}"/> >
							<a href='<c:url value="/category?categoryId=${category.id}" />'><c:out value="${category.name}"/></a>
							<br>
						</c:forEach>
					</div>
				</div>
				
				<div class="sidebar_block">
					<div class="sidebar_block_title"><fmt:message key="recent_comments" /></div>
					<div class="sidebar_block_body">
						<c:forEach var="comment" items="${recentComments}">
							<div class="recent_comment">
								<a href='<c:url value="/user?id=${comment.authorId}" />'><t:trimer text="${comment.author.displayName}" maxWords="12"/></a>
								<fmt:message key="on" />
								<a href="<c:url value="/post.do?id=${comment.postId}" />"><c:out value="${comment.postTitle}"/></a>
							</div>
						</c:forEach>
					</div>
				</div>				
			</div>