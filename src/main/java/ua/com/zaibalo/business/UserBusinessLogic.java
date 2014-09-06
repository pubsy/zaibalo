package ua.com.zaibalo.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class UserBusinessLogic {
	
	@Autowired
	private UsersDAO usersDAO;
	
	public User createUser(String loginName, String email, String password,
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
	
	public User addUser(User user){
		return createUser(user.getLoginName(), user.getEmail(), user.getPassword(), user.getDisplayName(), user.getSmallImgPath(), user.getBigImgPath());
	}
	
	public List<String> getAllUserNamesList(){
		return usersDAO.getAllUserNamesList();
	}
	
	public User getUserById(int userId){
		return usersDAO.getUserById(userId);
	}
}
