package ua.com.zaibalo.servlets.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SinglePostServlet {
	
	@Autowired
	private PostsDAO postsDAO;
	
	public ModelAndView getPost(int postId) throws Exception {
		
		Post post = postsDAO.getObjectById(postId);
		
		if(post == null){
			throw new RuntimeException(StringHelper.getLocalString("post_not_found_colon", Integer.toString(postId)));
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("post", post);
		mav.setViewName("single_post");
		
		return mav;
	}

}
