package ua.com.zaibalo.rest.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;

import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.User.Role;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:test-context.xml")
public class PostsRestControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private UsersBusinessLogic userBusinessLogic;

	//@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.xmlConfigSetup(
				"classpath:test-context.xml").build();
	}

	//@Test
	public void testUserCreation() throws IOException, Exception {
		User user = new User();
		user.setEmail("aaa@aaa.com");
		user.setDisplayName("DisplayName");
		user.setLoginName("LoginName");

		mockMvc.perform(
				post("/api/users").contentType(APPLICATION_JSON_UTF8).body(
						convertObjectToJsonBytes(user))).andExpect(
				status().isOk());
		
		User savedUser = userBusinessLogic.getUserById(1);
		assertNotNull(savedUser);
		assertEquals("aaa@aaa.com", savedUser.getEmail());
		assertEquals("DisplayName", savedUser.getDisplayName());
		assertEquals("LoginName", savedUser.getLoginName());
		
	}

	//@Test
	public void testGetUserById() throws IOException, Exception {
		User user = new User();
		user.setEmail("aaa@aaa.com");
		user.setDisplayName("DisplayName");
		user.setLoginName("LoginName");
		user.setRole(Role.USER);
		user.setPassword("PASSWORD");
		user.setAbout("AboutMe");
		user.setDate(new Date(1408793863671L));
		user.setToken("SecretToken");
		user.setNotifyOnPM(true);
		int userId = userBusinessLogic.addUser(user).getId();

		mockMvc.perform(get("/api/users/" + userId).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(8)))
				.andExpect(jsonPath("id", is(1)))
				.andExpect(jsonPath("$.date").exists())
				.andExpect(jsonPath("loginName", is("LoginName")))
				.andExpect(jsonPath("email", is("aaa@aaa.com")))
				.andExpect(jsonPath("displayName", is("DisplayName")))
				.andExpect(jsonPath("smallImgPath", is("default.thumbnail.jpg")))
				.andExpect(jsonPath("bigImgPath", is("default.jpg")))
				.andExpect(jsonPath("notifyOnPM", is(false)));
	}

	public byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
