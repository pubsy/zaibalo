							<li class="dropdown login-dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									<img src="${sessionScope.user.bigImgPath}?size=small" class="login_avatar" width="24px" >
									<span class="hide-on-mobile"><c:out value="${sessionScope.user.displayName}" /></span><span class="caret"></span>
								</a>
								<ul class="dropdown-menu login-menu">
									<li>
										<a href='<c:url value="/user/${sessionScope.user.id}" />'><zmt:message key="profile"/></a><br>
									</li>
									<li>	
										<a href="secure/inbox"><zmt:message key="messages"/>${sessionScope.unreadMailCount}</a><br>
									</li>
									<li>	
										<a href="secure/settings"><zmt:message key="profile_settings"/></a><br>
									</li>
									<li>	
										<a href="logout"><zmt:message key="logout"/></a>
									</li>
								</ul>
							</li>