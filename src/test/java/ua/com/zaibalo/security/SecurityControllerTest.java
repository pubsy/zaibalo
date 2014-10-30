package ua.com.zaibalo.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.codehaus.jackson.map.ObjectMapper;
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
    
    public static final MediaType APPLICATION_HTML_UTF8 = new MediaType(
        MediaType.TEXT_HTML.getType(),
        MediaType.TEXT_HTML.getSubtype(), Charset.forName("utf8"));

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    @Autowired
    private UsersDAO usersDAO;
    
    @Autowired
    private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    private User author;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
        
        author = new User();
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
                .contentType(APPLICATION_HTML_UTF8)
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
                .contentType(APPLICATION_HTML_UTF8)
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
        
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        assertNull(session.getAttribute(ZaibaloConstants.USER_PARAM_NAME));
    }
    

    @Test
    public void testLoginFailWithWrongPassword() throws IOException, Exception{
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(APPLICATION_HTML_UTF8)
                .param("username", "LoginName")
                .param("password", "WrongPasswod")
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
        HttpSession session = resultActions.andReturn().getRequest().getSession();
        assertNull(session.getAttribute(ZaibaloConstants.USER_PARAM_NAME));
    }
    
    @Test
    public void testSettingsPageRedirectsToLoginPageWhenNotAuthenticated() throws IOException, Exception{
        mockMvc.perform(get("/secure/settings")
                .contentType(APPLICATION_HTML_UTF8)
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
    }
    
    @Test
    public void testSettingsPageAccesedWhenAuthenticated() throws IOException, Exception{
        mockMvc.perform(get("/secure/settings")
        		.sessionAttr("user", author)
                .contentType(APPLICATION_HTML_UTF8)
                .body("".getBytes()))
                .andExpect(status().is(200))
                .andExpect(view().name("profile_settings"));
    }
    
    @Test
    public void testInboxPageRedirectsToLoginPageWhenNotAuthenticated() throws IOException, Exception{
        mockMvc.perform(get("/secure/inbox")
                .contentType(APPLICATION_HTML_UTF8)
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
    }
    
    @Test
    public void testDialogPageRedirectsToLoginPageWhenNotAuthenticated() throws IOException, Exception{
        mockMvc.perform(get("/secure/dialog/1")
                .contentType(APPLICATION_HTML_UTF8)
                .body("".getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/login"));
    }
    
    @Test
    public void testSecureActionSuccessWhenNotAuthenticated() throws IOException, Exception{
        mockMvc.perform(post("/secure/action.do")
                .contentType(APPLICATION_HTML_UTF8)
                .sessionAttr("user", author)
                .param("post_title", "ttt")
                .param("post_text", "ccc")
                .param("categories", "Test")
                .param("action", "save_post")
                .body("".getBytes()))
                .andExpect(content().mimeType(APPLICATION_JSON_UTF8))
                .andExpect(content().string("{\"object\":\"\",\"status\":\"success\"}"))
                .andExpect(status().is(200));
    }
    
    @Test
    public void testSecureActionFailWhenNotAuthenticated() throws IOException, Exception{
        mockMvc.perform(post("/secure/action.do")
                .contentType(APPLICATION_HTML_UTF8)
                .param("post_title", "ttt")
                .param("post_text", "ccc")
                .param("categories", "Test")
                .param("action", "save_post")
                .body("".getBytes()))
                .andExpect(content().string(""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCommentCreationSecured() throws IOException, Exception{
        mockMvc.perform(post("/secure/comment")
                .contentType(APPLICATION_JSON_UTF8)
                .param("postId", "1")
                .param("content", "Comment text")
                .body("".getBytes()))
                .andExpect(content().string(""))
                .andExpect(status().isUnauthorized());
    }

    public byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

}
