package ua.com.zaibalo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

@Controller
@RequestMapping("/rest/posts") 
public class Posts {
	
	@Autowired
	private PostsDAO postsFacade;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<Post> getLatestPosts() {
		return postsFacade.getPostsList(null, null, PostOrder.ID, -1, 10);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getTest(@PathVariable Integer id, ModelMap model) {
		
		Post post = postsFacade.getObjectById(id);
		model.addAttribute("post", post);
		
		return "RestPost";
	}
}
