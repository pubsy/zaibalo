
			<div id="left_body">
				<div class="sidebar_block">
					<div class="sidebar_block_title"><zmt:message key="search" /></div>
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
					<div class="sidebar_block_title"><zmt:message key="categories_colon" /></div>
					<div class="sidebar_block_body">
						<c:forEach var="category" items="${applicationScope.categories}">
							<input type="checkbox" class="category_checkbox" value="${category.id}" <cat:checker id="${category.id}" queryString="${pageContext.request.queryString}" url="${pageContext.request.servletPath}"/> >
							<a href='<c:url value="/category/${category.id}" />'><c:out value="${category.name}"/></a>
							<br>
						</c:forEach>
					</div>
				</div>
				
				<div class="sidebar_block">
					<div class="sidebar_block_title"><zmt:message key="recent_comments" /></div>
					<div class="sidebar_block_body">
						<c:forEach var="comment" items="${recentComments}">
							<div class="recent_comment">
								<a href='<c:url value="/user/${comment.author.id}" />'>
								<img src="image/${comment.author.smallImgPath}" >
								<t:trimer text="${comment.author.displayName}" maxWords="12"/></a>
								<zmt:message key="on" />
								<a href="<c:url value="/post/${comment.post.id}" />"><t:trimer text="${comment.post.title}" maxWords="19"/></a>
							</div>
						</c:forEach>
					</div>
				</div>				
			</div>