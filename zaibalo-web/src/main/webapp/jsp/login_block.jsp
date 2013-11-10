					<div class="sidebar_block_title"><fmt:message key="login_join" /></div>
					<div id="login_block">
						
						<div id="login_form" class="sidebar_block_body">
							<a href="javascript:showRegister()"><fmt:message key="register"/></a><br>
							<a href="javascript:showForgotPassword()"><fmt:message key="forgot_password_qm"/></a><br>
							<form action="javascript:login();">
								<fmt:message key="login_colon"/><br><input type="text" name="name" id="name" class="form_input"/><br>
								<fmt:message key="password_colon"/><br><input type="password" name="password" id="password" class="form_input"/><br>
								<input type="checkbox" name="remember" id="remember" checked><fmt:message key="remember_me"/><br>
								<input type="submit" id="submit_login_btn" value='<fmt:message key="login"/>'>
								<div style="clear:both;"></div>
							 </form>
							<!-- a href="https://loginza.ru/api/widget?token_url=http://scum.com.ua&providers_set=vkontakte,facebook" class="loginza">Sign in with OpenID</a-->
						</div>
						
						<div id="forgot_password" style="display:none;" class="sidebar_block_body">
							<fmt:message key="user_name_colon"/><br><input type="text" name="userName" id="userName" class="form_input"/><br>
							<input type="submit" value='<fmt:message key="send"/>' onclick="javascript:sendForgotPasswordRequest()">
						</div>
						
						<div id="register" style="display:none;" class="sidebar_block_body">
							<fmt:message key="login_colon"/><br><input type="text" name="register_login" id="register_login" class="form_input"/><br>
							<fmt:message key="email_colon"/><br><input type="text" name="register_email" id="register_email" class="form_input"/><br>
							<fmt:message key="password_will_be"/>
							<input type="submit" value='<fmt:message key="register"/>' onclick="javascript:register()">
						</div>
					</div>
					