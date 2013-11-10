
<c:if test="${sessionScope.user != null}">
	<div id="add_post_div">
		<div id="add_post_form">
			<input id="post_title" type="text" placeholder='<fmt:message key="post_title"/>'
				style="margin: 0px 0px 5px 0px; width: 100%;" />
			<textarea id="post_text" rows="2" placeholder='<fmt:message key="post_text"/>'></textarea>
		
			<div id="add_post_button">
				<div id="apButton" ><input type="button" id="addPostButton" value='<fmt:message key="send"/>' onclick="add_post();" /></div>
				<div id="loadingScreen"><img src="/img/icons/loading.gif" /></div>
				<div style="clear: both;"></div>
			</div>
		
			<div class="add_post_tags">
				<input type="text" id="tags_input"
					placeholder='<fmt:message key="write_tags_separated"/>' />
			</div>
		
			<div style="position: relative;">
				<div class="category_block">
					<select id="category_select" multiple="multiple" style="display: none;">
						<c:forEach var="category" items="${applicationScope.categories}">
							<option><c:out value="${category.name}"/></option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
</c:if>