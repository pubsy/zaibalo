package ua.com.zaibalo.servlets.pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.ServletHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

@WebServlet(urlPatterns={"/post.do", "/post"}, name="PostPage")
public class PostServlet extends ServletPage {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws ServletException, IOException {

		String id = request.getParameter("id");
		
		if(StringHelper.isBlank(id) || !StringHelper.isDecimal(id)){
			response.sendRedirect("/");
			ServletHelper.logMessage("Post Id is not passed as a parameter.", request);
			return null;
		}
		
		int postId = Integer.parseInt(id);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		Post post = factory.getPostsAccessInstance().getObjectById(postId);

		if(post == null){
			response.sendRedirect("/");
			ServletHelper.logShortErrorMessage("Post not found: " + id);
			return null;
		}
		
		request.setAttribute("post", post);
		request.setAttribute("pageTitle", getPageTitle(post.getTitle()));
		
		return "/jsp/single_post.jsp";
	}

}
