package ua.com.zaibalo.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

@Component
public class UserBusinessLogic {
	
	@Autowired
	private UsersDAO usersDAO;
	
	public int addUser(String loginName, String email, String password,
								String displayName, String smallImagePath, String bigImagePath){
		User user = new User();
		user.setLoginName(loginName);
		user.setEmail(email);
		if(password != null){
			user.setPassword(password);
		}else{
			user.setPassword("");
		}
		user.setDate(new Date());
		if(displayName != null){
			user.setDisplayName(displayName);
		}else{
			user.setDisplayName(loginName);
		}
		user.setRole(2);
		user.setToken(StringHelper.generateString(32));
		if(bigImagePath != null){
			user.setBigImgPath(bigImagePath);
		}else{
			user.setBigImgPath(null);
		}
		if(smallImagePath != null){
			user.setSmallImgPath(smallImagePath);
		}else{
			user.setSmallImgPath(null);
		}

		return usersDAO.insert(user);
	}
}
