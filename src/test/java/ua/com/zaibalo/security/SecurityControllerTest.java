package ua.com.zaibalo.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.ResultActions;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.User.Role;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class SecurityControllerTest {
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.TEXT_HTML.getType(),
        MediaType.TEXT_HTML.getSubtype(), Charset.forName("utf8"));
    
    @Autowired
    private UsersDAO usersDAO;
    
    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
        
        User author = new User();
        author.setPassword(MD5Helper.getMD5Of("1234"));
        author.setToken("blablabla");
        author.setEmail("aaa@aaa.com");
        author.setRole(Role.ROLE_USER);
        author.setDisplayName("DisplayName");
        author.setLoginName("LoginName");
        author = usersDAO.insert(author);
    }
    
    @Test
    public void testLoginSuccess() throws IOException, Exception{
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON_UTF8)
                .param("username", "LoginName")
                .param("password", "1234")
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
        
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        assertNotNull(session.getAttribute(ZaibaloConstants.USER_PARAM_NAME));
    }

    @Test
    public void testLoginWithouUsernamePassword() throws IOException, Exception{
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON_UTF8)
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
        
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        assertNull(session.getAttribute(ZaibaloConstants.USER_PARAM_NAME));
    }
    

    @Test
    public void testLoginFailWithWrongPassword() throws IOException, Exception{
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_JSON_UTF8)
                .param("username", "LoginName")
                .param("password", "WrongPasswod")
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        assertNull(session.getAttribute(ZaibaloConstants.USER_PARAM_NAME));
    }
}
