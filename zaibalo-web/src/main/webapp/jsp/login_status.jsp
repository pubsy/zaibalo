				<div class="sidebar_block_title"><zmt:message key="welcome" /></div>
				<div id="login_block">
					<div id="login_status" class="sidebar_block_body">
						<div class="login_avatar">
							<img src="/image/${sessionScope.user.bigImgPath}">
						</div>
						<div class="login_text">
							<a href='<c:url value="/user?id=${sessionScope.user.id}" />'><c:out value="${sessionScope.user.displayName}" /></a><br>
							<a href="/secure/inbox"><zmt:message key="messages"/>${sessionScope.unreadMailCount}</a><br>
							<a href="/secure/profileSettings.do"><zmt:message key="profile_settings"/></a><br>
							<a href="/logout.do"><zmt:message key="logout"/></a>
						</div>
						<div style="clear:both;"></div>
					</div>
				</div>