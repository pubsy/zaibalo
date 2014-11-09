package ua.com.zaibalo.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.com.zaibalo.business.PostsBusinessLogic;
import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.exceptions.ValidationException;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.User.Role;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:zaibalo-core-context.xml"})
public class CategoriesDaoTest {

    @Autowired
    private CategoriesDAO categoriesDAO;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private PostsBusinessLogic postsBusinessLogic;

    @Before
    public void before() throws ValidationException{
        User author = new User();
        author.setPassword("1234");
        author.setToken("81dc9bdb52d04dc20036dbd8313ed055");
        author.setEmail("aaa@aaa.com");
        author.setRole(Role.ROLE_USER);
        author.setDisplayName("DisplayName");
        author.setLoginName("LoginName");
        author = usersDAO.insert(author);
        
        categoriesDAO.insert(new Category("MorePopular"));
        categoriesDAO.insert(new Category("EvenLessPopular"));
        categoriesDAO.insert(new Category("LessPopular"));
        
        postsBusinessLogic.createPost("content2 #EvenLessPopular", author);
        postsBusinessLogic.createPost("content2 #MorePopular", author);
        postsBusinessLogic.createPost("#LessPopular content", author);
        postsBusinessLogic.createPost("#MorePopular content2", author);
    }
    
    @Test
    public void test(){
        List<Category> list = categoriesDAO.getMostPopularCategoriesList(3);
        assertEquals(3, list.size());
        assertEquals("MorePopular", list.get(0).getName());
        assertEquals("LessPopular", list.get(1).getName());
        assertEquals("EvenLessPopular", list.get(2).getName());
    }

}
