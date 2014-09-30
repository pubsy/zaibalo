package ua.com.zaibalo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.business.PostsBusinessLogic;
import ua.com.zaibalo.model.Post;

@Controller
public class RssController {
	
	@Autowired
	private PostsBusinessLogic postsBusinessLogic;

	@RequestMapping(value = { "/feed" }, method = RequestMethod.GET)
	public ModelAndView feed() {
		List<Post> items = postsBusinessLogic.getOrderedList(-1, 10,
				Post.PostOrder.ID);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("feedContent", items);

		return mav;
	}
}
