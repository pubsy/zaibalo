							<li class="dropdown login-dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="glyphicon glyphicon-log-in"></span><span class="hide-on-mobile"><zmt:message key="login_join"/></span><span class="caret"></span>
								</a>
								<ul class="dropdown-menu login-menu">
									<li>
										<form class="form-inline" role="form" action="<c:url value='j_spring_security_check'/>" method="POST">
											<div class="input-group input-group-sm login-form-element">
											  <label class="sr-only" for="name"><zmt:message key="login_colon"/></label> 
											  <input type="text" class="form-control" name="username" placeholder="<zmt:message key='login_colon'/>">
											</div>
											<div class="input-group input-group-sm login-form-element">
												<label class="sr-only" for="password"><zmt:message key="password_colon"/></label>
												<input type="password" class="form-control"	name="password" placeholder='<zmt:message key="password_colon"/>'>
											</div>
											<div class="checkbox login-form-element">
												<label> <input type="checkbox" name="_spring_security_remember_me" ><zmt:message key="remember_me"/></label>
											</div>
											<button type="submit" class="btn btn-default login-form-element" id="submit_login_btn"><zmt:message key="login"/></button>
										</form>
										<div class="login-form-element register-forgot-block">
											<a href="javascript:remindPassDialogShow()"><zmt:message key="forgot_password_qm"/></a>
											<a href="javascript:registerDialogShow();"><zmt:message key="register"/></a>
										</div>
									</li>
								</ul>
							</li>