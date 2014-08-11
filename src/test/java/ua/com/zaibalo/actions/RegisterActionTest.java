package ua.com.zaibalo.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.actions.impl.RegisterAction;
import ua.com.zaibalo.helper.ajax.AjaxResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:mvc-dispatcher-servlet.xml")
@Transactional
public class RegisterActionTest {

	@Autowired
	private RegisterAction registerAction;
	
	@Test
	public void testDisplayNameExists() throws Exception{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("email", "a@a.com");
		request.setParameter("register_login", "loginName");
		
		
		AjaxResponse run = registerAction.run(request, response);
		
		assertEquals("", run.getStatus());
	}
}
