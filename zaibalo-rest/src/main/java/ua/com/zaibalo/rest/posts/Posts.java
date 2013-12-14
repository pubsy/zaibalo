package ua.com.zaibalo.rest.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.zaibalo.db.api.PostsAccessInterface;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

@Controller
@Transactional
@RequestMapping("/posts") 
public class Posts {
	
	@Autowired
	private PostsAccessInterface postsFacade;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Post getPostById(@PathVariable Integer id) {
		return postsFacade.getObjectById(id);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<Post> getLatestPosts() {
		return postsFacade.getPostsList(null, null, PostOrder.ID, -1, 10);
	}
}
