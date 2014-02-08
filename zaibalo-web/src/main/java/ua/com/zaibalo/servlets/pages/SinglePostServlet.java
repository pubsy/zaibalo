package ua.com.zaibalo.servlets.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

@Controller
public class SinglePostServlet extends ServletPage {
	
	@Autowired
	private PostsDAO postsDAO;
	
	@Override
	public String runInternal(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		
		if(StringHelper.isBlank(id) || !StringHelper.isDecimal(id)){
			ServletHelperService.redirectHome(response);
			ServletHelperService.logMessage("Post Id is not passed as a parameter.", request);
			return null;
		}
		
		int postId = Integer.parseInt(id);
		
		Post post = postsDAO.getObjectById(postId);

		if(post == null){
			ServletHelperService.redirectHome(response);
			ServletHelperService.logShortErrorMessage("Post not found: " + id);
			return null;
		}
		
		request.setAttribute("post", post);
		request.setAttribute("pageTitle", getPageTitle(post.getTitle()));
		
		return "single_post";
	}

}
