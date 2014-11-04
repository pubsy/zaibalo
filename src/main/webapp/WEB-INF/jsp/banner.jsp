				<div id="header">
					<div id="header_menu">
						<ul class="nav nav-pills">
						
							<li class="active"><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span><span class="hide-on-mobile"><zmt:message key="main" /></span></a></li>

							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="glyphicon glyphicon-comment"></span><span class="hide-on-mobile"><zmt:message key="recent_comments" /></span><span class="caret"></span>
								</a>
								<ul class="dropdown-menu recent-comments">
									<c:forEach var="comment" items="${recentComments}">
										<li class="recent_comment">
											<a href='<c:url value="/user/${comment.author.id}" />'>
											<img src="image/${comment.author.smallImgPath}" width="16px" >
											<t:trimer text="${comment.author.displayName}" maxWords="12"/></a>
											<zmt:message key="on" />
											<a href="<c:url value="/post/${comment.post.id}" />"><t:trimer text="${comment.post.title}" maxWords="19"/></a>
										</li>
									</c:forEach>
								</ul>
							</li>
							
							<li class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="glyphicon glyphicon-tags"></span><span class="hide-on-mobile"><zmt:message key="categories" /></span><span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<c:forEach var="category" items="${applicationScope.categories}">
										<li>
											<label>
												<input type="checkbox" class="category_checkbox" id="${category.id}" value="${category.id}" <cat:checker id="${category.id}" queryString="" url=""/> >
												<c:out value="${category.name}"/>
											</label>
										</li>
									</c:forEach>
								</ul>
							</li>
							
							<li><a href="feed"><span class="rss-icon"></span><span class="hide-on-mobile">RSS</span></a></li>
							<security:check>
								<%@include file="login-status-dropdown.jsp" %>
							</security:check>
							<security:check access="isAnonymous()">
								<li class="login-dropdown">
									<a href="login?redirect=${successRedirectURL}">
										<span class="glyphicon glyphicon-log-in"></span><span class="hide-on-mobile"><zmt:message key="login_join"/></span>
									</a>
								</li>
							</security:check>
						</ul>
					</div>
					<div id="header_banner">
						<div id="vk_icon"><a target="_blank" href="http://vk.com/club10793353"><img src="img/icons/vk.png" /></a></div>					
						<div id="header_banner_image"><img src="img/banners/banner.png"></div>
					</div>
				</div>	
						
				<div id="forgot_password" style="display:none;">
					<div class="form-group">
						<label for="userName" class="col-sm-2 control-label"><zmt:message key="user_name_colon"/></label>
						<div class="col-sm-10">
							<input type="text" name="userName" id="userName" class="form-control"/><br>
						</div>
					</div>
					<div id="forgot-pass-dialog-validation" class="dialog-validation"></div>
				</div>
						
				<div id="register" style="display:none;">
					<div class="form-group">
						<label for="register_login" class="col-sm-2 control-label"><zmt:message key="login_colon"/></label>
						<div class="col-sm-10">
							<input type="text" name="register_login" id="register_login" class="form-control"/><br>
						</div>
					</div>
					<div class="form-group">
						<label for="register_email" class="col-sm-2 control-label"><zmt:message key="email_colon"/></label>
						<div class="col-sm-10">
							<input type="email" name="register_email" id="register_email" class="form-control"/><br>
						</div>
					</div>
					<div id="register-dialog-validation" class="dialog-validation"></div>
					<zmt:message key="password_will_be"/>
				</div>
				
				<div id="message"></div>
				<div id="show_rating"></div>