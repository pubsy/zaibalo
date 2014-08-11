package ua.com.zaibalo.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.controllers.Pages;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.SendEmailServiceStub;
import ua.com.zaibalo.email.templates.AbstractMessage;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class RegisterActionTest {

	@Autowired
	private Pages pages;

	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private UsersDAO usersDAO;

	@Test
	@Transactional
	public void testUserRegistration() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setParameter("email", "test@email.com");
		request.setParameter("register_login", "testLoginName");
		request.setParameter("action", "register");

		AjaxResponse run = pages.action(request, response);
		assertEquals("success", run.getStatus());

		User user = usersDAO.getUserByEmail("test@email.com");
		assertNotNull(user);
		assertEquals("testLoginName", user.getLoginName());

		AbstractMessage message = SendEmailServiceStub.pollMessage();
		String content = message.getMimeMessage().getContent().toString();
		assertTrue(content.contains("testLoginName"));
	}

	@Test
	@Transactional
	public void testEmailExists() throws Exception {
		User user = new User();
		user.setEmail("some@email.com");
		user.setLoginName("someLoginName");
		user.setDisplayName("CoolName");
		usersDAO.insert(user);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setParameter("email", "some@email.com");
		request.setParameter("register_login", "otheLoginName");
		request.setParameter("action", "register");

		AjaxResponse result = pages.action(request, response);
		assertTrue(result instanceof FailResponse);
		assertEquals("fail", result.getStatus());
		assertEquals("E-mail вже зареєстровано. Спробуйте відновити пароль.",
				((FailResponse) result).getMessage());
	}
	
	@Test
	@Transactional
	public void testLoginNameExists() throws Exception {
		User user = new User();
		user.setEmail("some@email.com");
		user.setLoginName("someLoginName");
		user.setDisplayName("CoolName");
		usersDAO.insert(user);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setParameter("email", "other@email.com");
		request.setParameter("register_login", "someLoginName");
		request.setParameter("action", "register");

		AjaxResponse result = pages.action(request, response);
		assertTrue(result instanceof FailResponse);
		assertEquals("fail", result.getStatus());
		assertEquals("Цей логін вже зайнято. Будь ласка оберіть інший.",
				((FailResponse) result).getMessage());
	}
	
	@Test
	@Transactional
	public void testDisplayNameExists() throws Exception {
		User user = new User();
		user.setEmail("some@email.com");
		user.setLoginName("someLoginName");
		user.setDisplayName("CoolName");
		usersDAO.insert(user);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setParameter("email", "other@email.com");
		request.setParameter("register_login", "CoolName");
		request.setParameter("action", "register");

		AjaxResponse result = pages.action(request, response);
		assertTrue(result instanceof FailResponse);
		assertEquals("fail", result.getStatus());
		assertEquals("Цей логін вже зайнято. Будь ласка оберіть інший.",
				((FailResponse) result).getMessage());
	}

}
