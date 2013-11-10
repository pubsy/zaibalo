				<div class="sidebar_block_title"><fmt:message key="welcome" /></div>
				<div id="login_block">
					<div id="login_status" class="sidebar_block_body">
						<div class="login_avatar">
							<img src='/image/<ue:url value="${sessionScope.user.bigImgPath}"/>'>
						</div>
						<div class="login_text">
							<a href='<c:url value="/user?id=${sessionScope.user.id}" />'><c:out value="${sessionScope.user.displayName}" /></a><br>
							<a href="/secure/inbox.do"><fmt:message key="messages"/>${sessionScope.unreadMailCount}</a><br>
							<a href="/secure/profileSettings.do"><fmt:message key="profile_settings"/></a><br>
							<a href="/logout.do"><fmt:message key="logout"/></a>
						</div>
						<div style="clear:both;"></div>
					</div>
				</div>