
<c:if test="${sessionScope.user != null and sessionScope.user.role <= 2}">
	<div id="add_post_div">
		<div id="add_post_form">
			<input id="post_title" type="text" placeholder='<zmt:message key="post_title"/>'
				style="margin: 0px 0px 5px 0px; width: 100%;" />
			<textarea id="post_text" rows="2" placeholder='<zmt:message key="post_text"/>'></textarea>
			<div class="category_block">
				<select id="category_select" multiple="multiple" style="display: none;">
					<c:forEach var="category" items="${applicationScope.categories}">
						<option><c:out value="${category.name}"/></option>
					</c:forEach>
				</select>
			</div>
		
			<div id="add_post_button">
				<input type="text" id="tags_input" placeholder='<zmt:message key="write_tags_separated"/>' />
				<div id="apButton" >
					<input type="button" id="addPostButton" value='<zmt:message key="send"/>' onclick="add_post();" />
					<span id="loadingScreen"><img src="/img/icons/loading.gif" /></span>
				</div>
			</div>
			
			<div style="clear: both;"></div>
		</div>
	</div>
</c:if>