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
import ua.com.zaibalo.model.User.Role;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class UsersBusinessLogic {
	
	@Autowired
	private UsersDAO usersDAO;
	
	public User createUser(String loginName, String email, String password,
								String displayName, String bigImagePath){
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
		user.setRole(Role.ROLE_USER);
		user.setToken(StringHelper.generateString(32));
		if(bigImagePath != null){
			user.setBigImgPath(bigImagePath);
		}

		return usersDAO.insert(user);
	}
	
	public List<String> getAllUserNamesList(){
		return usersDAO.getAllUserNamesList();
	}
	
	public User getUserById(int userId){
		return usersDAO.getUserById(userId);
	}

    public User getUserByLoginName(String loginName) {
        return usersDAO.getUserByLoginName(loginName);
    }
}
