
			<div id="edit_post_dialog" style="display:none;">
				<input type="hidden" id="edit_post_id" value="">
				<input type="text" style="margin: 0px 0px 5px 0px; width: 100%;" id="edit_post_title">
				<textarea rows=6 style="width: 100%; overflow-y: scroll;  resize: none;" id="edit_post_content"></textarea>
				<br><br>
				<div class="edit_post_cat_title">
					<zmt:message key="categories_colon"/>&nbsp;<span id="edit_post_category"></span>
				</div>
				<div>
					<select id="edit_category_select">
						<c:forEach var="category" items="${applicationScope.categories}">
							<option><c:out value="${category.name}"/></option>
						</c:forEach>
					</select>
					<input type="button" value='<zmt:message key="add"/>' onclick="javascript:addSelectedCategoryToEditPost();">
					<input type="text" id="edit_post_tags_input" placeholder='<zmt:message key="write_tags_separated"/>' />
					<input type="button" value='<zmt:message key="add"/>' onclick="javascript:addTagToEditPost();">
				</div>
				<div id="edit_post_validation"></div>
			</div>
		