package ua.com.zaibalo.rest.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

@Controller
@RequestMapping("/rest/posts") 
public class Posts {
	
	@Autowired
	private PostsDAO postsDao;
	
	private static final int DEFAULT_COUNT = 10;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<Post> getLatestPosts() {
		return postsDao.getPostsList(null, null, PostOrder.ID, -1, DEFAULT_COUNT);
	}
	
	@RequestMapping(value = { "/page/{page}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Post> getLatestPostsPage(
			@PathVariable("page") String page) {
		int from = -1;
		if (StringHelper.isDecimal(page)) {
			from = (Integer.parseInt(page) - 1) * DEFAULT_COUNT;
		}
		return postsDao.getPostsList(null, null, PostOrder.ID, from, DEFAULT_COUNT);
	}
	
	@RequestMapping(value = { "/category/{category}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Post> getLatestPostsCategory(
			@PathVariable("category") String category) {
		
		List<Integer> catIdsList = null;
		if (StringHelper.isNotBlank(category)
				&& StringHelper.isArrayDecimal(category, ",")) {
			catIdsList = new ArrayList<Integer>();
			for (String num : category.split(",")) {
				catIdsList.add(Integer.parseInt(num));
			}
		}
		
		return postsDao.getPostsList(catIdsList, null, PostOrder.ID, -1, DEFAULT_COUNT);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Post getPost(@PathVariable Integer id, ModelMap model) {
		
		Post post = postsDao.getObjectById(id);
		model.addAttribute("post", post);
		
		return post;
	}
	//{"id":6105,"title":"ttt","authorName":"aaa","ratingSum":1,"ratingCount":3,"commentCount":11,"authorAvatarUrl":"jpg","content":"ccc",date: "2013-11-05 13:18:40.0"}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Post saveUpdatePost(@RequestBody Post post, ModelMap model) {
		if(post.getId() == -1){
			//postsDao.insert(post);
		} else {
			//postsDao.update(post);
		}
		model.addAttribute("post", post);
		
		return post;
	}
	
	
}
