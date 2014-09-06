package ua.com.zaibalo.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.model.User;

//@Controller
//@Transactional
//@RequestMapping("/api/users") 
public class UsersRestController {
	
	@Autowired
	private UsersDAO usersDAO;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public User getUser(@PathVariable Integer userId) {
		return usersDAO.getUserById(userId);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public User createUser(@RequestBody User user) {
		return usersDAO.insert(user);
	}
	
}
