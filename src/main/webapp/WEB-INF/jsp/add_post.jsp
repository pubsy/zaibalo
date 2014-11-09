
<security:check>
	<div id="add_post_div">
		<div id="add_post_form">
			<textarea id="post_text" rows="2" placeholder='<zmt:message key="post_text"/>'></textarea>
			<div id="add_post_button">
				<div id="apButton" >
					<input type="button" id="addPostButton" value='<zmt:message key="send"/>' onclick="add_post();" />
					<span id="loadingScreen"><img src="img/icons/loading.gif" /></span>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
</security:check>