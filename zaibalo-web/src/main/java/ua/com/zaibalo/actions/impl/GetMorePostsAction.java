package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.helper.ServletHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

public class GetMorePostsAction implements Action{

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws Exception {
		
		String fromParam = request.getParameter("from");
		String countParam = request.getParameter("count");
		String categoryIds = request.getParameter("categoryIds");
		
		int from = Integer.parseInt(fromParam);
		int count = Integer.parseInt(countParam);
		
		List<Post> list = null;

		if(StringHelper.isBlank(categoryIds)){
			 //list = DataAccessFactory.getPostsAccessInstance().getOrderedList(from, count, Post.PostOrder.ID);
		}else if(StringHelper.isArrayDecimal(categoryIds, ",")){
			//list = DataAccessFactory.getPostsAccessInstance().getMoreCategoryPosts(categoryIds, from, count);
		}else{
			out.print("Error! Bad request parameters. Please notify administration.");
			out.close();
			ServletHelper.logMessage("Error! While getting more posts. Bad request parameters.", request);
			return;
		}

		request.setAttribute("posts", list);
		
		RequestDispatcher view = request.getRequestDispatcher("/jsp/postsBlock.jsp");
		view.forward(request, response);
	}

}
