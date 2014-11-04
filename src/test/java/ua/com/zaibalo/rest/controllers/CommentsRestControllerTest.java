package ua.com.zaibalo.rest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

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
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.zaibalo.business.CommentsBusinessLogic;
import ua.com.zaibalo.business.PostsBusinessLogic;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.User.Role;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml" , "classpath:mvc-dispatcher-servlet.xml"})
public class CommentsRestControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private PostsBusinessLogic postsBusinessLogic;
	@Autowired
	private CommentsBusinessLogic commentsBusinessLogic;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac)
            .build();
    }
    
    @Test
	public void testCommentCreationSecured() throws IOException, Exception{
    	mockMvc.perform(post("/api/comments")
				.contentType(APPLICATION_JSON_UTF8)
				.body(convertObjectToJsonBytes(new CommentsServiceRequest())))
				.andExpect(status().isUnauthorized());
    }
    
    @Test
	public void testCommentUpdateSecured() throws IOException, Exception{
    	mockMvc.perform(put("/api/comments/" + 1)
				.contentType(APPLICATION_JSON_UTF8)
				.body(convertObjectToJsonBytes(new CommentsServiceRequest())))
				.andExpect(status().isUnauthorized());
    }
    
    @Test
	public void testCommentServiceWithWrongPassword() throws IOException, Exception{
		User author = new User();
		author.setPassword("1234");
		author.setToken("81dc9bdb52d04dc20036dbd8313ed055");
		author.setEmail("aaa@aaa.com");
		author.setRole(Role.ROLE_USER);
		author.setDisplayName("DisplayName");
		author.setLoginName("LoginName");
		author = usersDAO.insert(author);
		
		
    	mockMvc.perform(post("/api/comments")
				.contentType(APPLICATION_JSON_UTF8)
				.body(convertObjectToJsonBytes(new CommentsServiceRequest())))
				.andExpect(status().isUnauthorized());
    }
    
	@Test
	public void testCommentCreation() throws IOException, Exception {

		User author = new User();
		author.setPassword("1234");
		author.setToken("81dc9bdb52d04dc20036dbd8313ed055");
		author.setEmail("aaa@aaa.com");
		author.setRole(Role.ROLE_USER);
		author.setDisplayName("DisplayName");
		author.setLoginName("LoginName");
		author = usersDAO.insert(author);

		Post post = postsBusinessLogic.createPost("title", "content", author, new String[]{"category"});

		CommentsServiceRequest request = new CommentsServiceRequest();
		request.setPostId(post.getId());
		request.setContent("comment text");
		
		
		mockMvc.perform(post("/api/comments")
				.contentType(APPLICATION_JSON_UTF8)
				.body(convertObjectToJsonBytes(request)))
				.andExpect(content().string(""))
				.andExpect(status().isOk());

		Comment savedComment = commentsBusinessLogic.getCommentById(1);
		assertNotNull(savedComment);
		assertEquals("comment text", savedComment.getContent());
		assertEquals(author, savedComment.getAuthor());
	}
	
	@Test
	public void testCommentUpdate() throws IOException, Exception {

		User author = new User();
		author.setPassword("1234");
		author.setToken("81dc9bdb52d04dc20036dbd8313ed055");
		author.setEmail("aaa@aaa.com");
		author.setRole(Role.ROLE_USER);
		author.setDisplayName("DisplayName");
		author.setLoginName("LoginName");
		author = usersDAO.insert(author);

		Post post = postsBusinessLogic.createPost("title", "content", author, new String[]{"category"});

		Comment saveComment = commentsBusinessLogic.saveComment(author, post, "uxuxux");
		
		CommentsServiceRequest request = new CommentsServiceRequest();
		request.setContent("comment text test 4563x");
		
//		auth("LoginName", "1234", "ROLE_USER");
		
		mockMvc.perform(put("/api/comments/" + saveComment.getId())
				.contentType(APPLICATION_JSON_UTF8)
				.body(convertObjectToJsonBytes(request)))
				.andExpect(status().isOk());

		Comment savedComment = commentsBusinessLogic.getCommentById(saveComment.getId());
		assertNotNull(savedComment);
		assertEquals("comment text test 4563x", savedComment.getContent());
		assertEquals(author, savedComment.getAuthor());
	}

	public byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}
}