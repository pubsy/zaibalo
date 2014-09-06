					<div class="sidebar_block_title"><zmt:message key="login_join" /></div>
					<div id="login_block">
						
						<div id="login_form" class="sidebar_block_body">
							<a href="javascript:showRegister()"><zmt:message key="register"/></a><br>
							<a href="javascript:showForgotPassword()"><zmt:message key="forgot_password_qm"/></a><br>
							<form action="javascript:login();">
								<zmt:message key="login_colon"/><br><input type="text" name="name" id="name" class="form_input"/><br>
								<zmt:message key="password_colon"/><br><input type="password" name="password" id="password" class="form_input"/><br>
								<input type="checkbox" name="remember" id="remember" checked><zmt:message key="remember_me"/><br>
								<input type="submit" id="submit_login_btn" value='<zmt:message key="login"/>'>
								<div style="clear:both;"></div>
							 </form>
							<!-- a href="https://loginza.ru/api/widget?token_url=http://scum.com.ua&providers_set=vkontakte,facebook" class="loginza">Sign in with OpenID</a-->
						</div>
						
						<div id="forgot_password" style="display:none;" class="sidebar_block_body">
							<zmt:message key="user_name_colon"/><br><input type="text" name="userName" id="userName" class="form_input"/><br>
							<input type="submit" value='<zmt:message key="send"/>' onclick="javascript:sendForgotPasswordRequest()">
						</div>
						
						<div id="register" style="display:none;" class="sidebar_block_body">
							<zmt:message key="login_colon"/><br><input type="text" name="register_login" id="register_login" class="form_input"/><br>
							<zmt:message key="email_colon"/><br><input type="text" name="register_email" id="register_email" class="form_input"/><br>
							<zmt:message key="password_will_be"/>
							<input type="submit" value='<zmt:message key="register"/>' onclick="javascript:register()">
						</div>
					</div>
					