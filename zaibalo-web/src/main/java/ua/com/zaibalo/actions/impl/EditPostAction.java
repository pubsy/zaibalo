package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

public class EditPostAction implements Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		
		String title = request.getParameter("post_title");
		String text = request.getParameter("post_text");
		String categoriesStr = request.getParameter("categories");
		String postIdStr = request.getParameter("post_id");
		
		if(title == null || "".equals(title.trim())){
			out.write("ERROR:" + StringHelper.getLocalString("title_cant_be_blank"));
			out.close();
			return;
		}
		
		if(text == null || "".equals(text.trim())){
			out.write("ERROR:" + StringHelper.getLocalString("content_cant_be_blank"));
			out.close();
			return;
		}
		
		if(categoriesStr == null || "".equals(categoriesStr.trim())){
			out.write("ERROR:" + StringHelper.getLocalString("you_have_to_choose_category"));
			out.close();
			return;
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);

		int postId = Integer.parseInt(postIdStr);
		DataAccessFactory factory = new DataAccessFactory(request);
		Post post = factory.getPostsAccessInstance().getObjectById(postId);
		
		if(user.getRole() >= 2 && post.getAuthorId() != user.getId()){
			out.write("ERROR:" + StringHelper.getLocalString("you_cant_edit_other_users_post"));
			out.close();
			return;
		}
		
		if(user.getRole() >= 2 && post.getComments().size() > 0){
			out.write("ERROR:" + StringHelper.getLocalString("you_cant_edit_your_post_after_commented"));
			out.close();
			return;
		}
		
		
		List<Category> postCategoriesAndTags = new ArrayList<Category>();
		
		for (String catName : categoriesStr.split(",")){
			Category category = factory.getCategorysAccessInstance().getCategoryByName(Category.CategoryType.BOTH, catName.trim());
			if(category != null){
				postCategoriesAndTags.add(category);
			}else{
				Category newTag = new Category(catName.trim(), Category.CategoryType.TAG);
				int catId = factory.getCategorysAccessInstance().insert(newTag);
				newTag.setId(catId);
				postCategoriesAndTags.add(newTag);
			}
		}
		
		post.setCategories(postCategoriesAndTags);
		post.setTitle(title);
		post.setContent(text);
		
		//factory.getCategorysAccessInstance().deleteAllPostCaegories(post.getId());
		factory.getPostsAccessInstance().update(post);
		
		request.setAttribute("post", post);
		
		RequestDispatcher view = request.getRequestDispatcher("/jsp/post_wrapper.jsp");
		view.forward(request, response);
		
	}

}
