package ua.com.zaibalo.servlets.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

@Service
public class SinglePostServlet {
	
	@Autowired
	private PostsDAO postsDAO;
	
	public String getPost(String postIdParam, HttpServletRequest request, HttpServletResponse response) {
		
		if(StringHelper.isBlank(postIdParam) || !StringHelper.isDecimal(postIdParam)){
			ServletHelperService.redirectHome(response);
			ServletHelperService.logMessage("Post Id is not passed as a parameter.", request);
			return null;
		}
		
		int postId = Integer.parseInt(postIdParam);
		
		Post post = postsDAO.getObjectById(postId);

		if(post == null){
			ServletHelperService.redirectHome(response);
			ServletHelperService.logShortErrorMessage("Post not found: " + postIdParam);
			return null;
		}
		
		request.setAttribute("post", post);
		
		return "single_post";
	}

}
