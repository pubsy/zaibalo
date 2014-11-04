package ua.com.zaibalo.actions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.controllers.PagesController;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml" , "classpath:mvc-dispatcher-servlet.xml"})
public class SetTimeZoneActionTest {

	@Autowired
	private PagesController pages;

	@Autowired
	private UsersDAO usersDAO;

	@Test
	@Transactional
	public void testSetTimeZoneSuccess() throws IOException, MessagingException {
		User author = new User();
		author.setEmail("author@email.com");
		author.setLoginName("SomeLoginName");
		author.setDisplayName("CoolGuy");
		usersDAO.insert(author);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.getSession().setAttribute("user", author);
		request.setParameter("timeZone", "2.5");
		request.setParameter("action", "set_time_zone");

		AjaxResponse result = pages.action(request, response);

		// Check for success response
		assertEquals("success", result.getStatus());
		String sessionValue = (String)request.getSession().getAttribute("timeZone");
		assertEquals("GMT+2.5", sessionValue);
	}
	
	@Test
	@Transactional
	public void testSetTimeZoneWithoutDecimal() throws IOException, MessagingException {
		User author = new User();
		author.setEmail("author@email.com");
		author.setLoginName("SomeLoginName");
		author.setDisplayName("CoolGuy");
		usersDAO.insert(author);

		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.getSession().setAttribute("user", author);
		request.setParameter("timeZone", "2");
		request.setParameter("action", "set_time_zone");

		AjaxResponse result = pages.action(request, response);

		// Check for success response
		assertEquals("success", result.getStatus());
		String sessionValue = (String)request.getSession().getAttribute("timeZone");
		assertEquals("GMT+2", sessionValue);
	}
}
