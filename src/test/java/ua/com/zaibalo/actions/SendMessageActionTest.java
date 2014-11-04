package ua.com.zaibalo.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.controllers.PagesController;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.email.stub.SendEmailServiceStub;
import ua.com.zaibalo.email.templates.AbstractMessage;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml" , "classpath:mvc-dispatcher-servlet.xml"})
public class SendMessageActionTest {
	
    @Autowired
    private PagesController pages;
    
    @Autowired
    private UsersDAO usersDAO;
    
    @Test
    @Transactional
    public void testRemindPassword() throws IOException, MessagingException {
        User author = new User();
        author.setEmail("author@email.com");
        author.setLoginName("SomeLoginName");
        author.setDisplayName("CoolGuy");
        usersDAO.insert(author);
        
        User recipient = new User();
        recipient.setEmail("recipient@email.com");
        recipient.setLoginName("SomeOtherLoginName");
        recipient.setDisplayName("OtherGuy");
        recipient.setNotifyOnPM(true);
        usersDAO.insert(recipient);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.getSession().setAttribute("user", author);
        request.setParameter("recipient_name", "OtherGuy");
        request.setParameter("text", "SomeTextInAMessage");
        request.setParameter("action", "send_message");

        AjaxResponse result = pages.secureAction(request, response);
        
        //Check for success response
        assertEquals("success", result.getStatus());
        assertEquals("", ((SuccessResponse)result).getObject());

        //Check email is sent out
        AbstractMessage message = SendEmailServiceStub.pollMessage();
        assertNotNull(message);
        assertTrue(message.getMimeMessage().getContent().toString().contains("SomeTextInAMessage"));
    }
}
