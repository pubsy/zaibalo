
		<c:if test="${sessionScope.user != null}">	
			<div class="comment_style">
				<a href="javascript:showAddCommentField('${post.id}');" id='add_comment_text_${post.id}'><zmt:message key="comment_this"/></a>
				<div id='add_comment_${post.id}' style='display: none;'>
					<div id="comment_avatar_${post.id}" class="comment_avatar">
						<img src="/image/${sessionScope.user.smallImgPath}" width="32">
					</div>
					<div class="comment_content">
						<textarea placeholder='<zmt:message key="write_a_comment"/>' rows="2" id="comment_textarea_${post.id}"></textarea>
						<div id="loading_gif_${post.id}" class="add_comment_loading" style="display:none;"><img src="/img/icons/loading.gif" /></div>
						<button onclick="addComment(${post.id})"><zmt:message key="send"/></button>
					</div>
					
					<div style="clear: both;"></div>
				</div>
			</div>
		</c:if>