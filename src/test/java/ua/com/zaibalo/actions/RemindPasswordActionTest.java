package ua.com.zaibalo.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.controllers.PagesController;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.email.stub.SendEmailServiceStub;
import ua.com.zaibalo.email.templates.AbstractMessage;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.model.User;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml" , "classpath:mvc-dispatcher-servlet.xml", "classpath:email-service-stub.xml"})
public class RemindPasswordActionTest {
    
    @Autowired
    private PagesController pages;
    
    @Autowired
    private UsersDAO usersDAO;
    
    @Test
    @Transactional
    public void testRemindPassword() throws IOException, MessagingException {
        String oldPassword = "OldPassword";
 
        User user = new User();
        user.setEmail("some@email.com");
        user.setLoginName("SomeLoginName");
        user.setDisplayName("CoolGuy");
        user.setPassword(oldPassword);
        user.setToken("bla");
        usersDAO.insert(user);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("userName", "SomeLoginName");
        request.setParameter("action", "remind_password");

        AjaxResponse run = pages.action(request, response);
        
        //Check for success response
        assertEquals("success", run.getStatus());

        //Check users password got updated
        User afterReminderUser = usersDAO.getUserByEmail("some@email.com");
        assertNotEquals(MD5Helper.getMD5Of(oldPassword), afterReminderUser.getPassword());
        
        //Check email is sent out
        AbstractMessage message = SendEmailServiceStub.pollMessage();
        assertNotNull(message);
    }
    
    @Test
    @Transactional
    public void testWrongUserName() throws IOException, MessagingException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("userName", "WRON_USER_NAME");
        request.setParameter("action", "remind_password");

        AjaxResponse result = pages.action(request, response);
        assertEquals("fail", result.getStatus());
        
        assertTrue(result instanceof FailResponse);
        assertEquals("Користувача з таким іменем не існує.", ((FailResponse)result).getMessage());

        AbstractMessage message = SendEmailServiceStub.pollMessage();
        assertNull(message);
    }

}
