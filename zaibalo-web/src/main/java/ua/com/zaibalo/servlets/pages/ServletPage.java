package ua.com.zaibalo.servlets.pages;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Comment;

public abstract class ServletPage {
	
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private CommentsDAO commentsDAO; 
	@Autowired
	private ServletHelperService servletHelperService;
	
	
	protected abstract String runInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	public String run(HttpServletRequest request, HttpServletResponse response){
		
		List<Comment> commentsList = null;
		
		String page = null;
		
		try {
			ServletContext servletContext = request.getServletContext();
			if(servletContext.getAttribute("categories") == null){
				List<Category> catList = categoriesDAO.getCategoriesList(Category.CategoryType.CATEGORY);
				request.getServletContext().setAttribute("categories", catList);
			}
			
			commentsList = commentsDAO.getRecentComments(15);
			request.setAttribute("recentComments", commentsList);

			if(request.getAttribute("pageTitle") == null){
				request.setAttribute("pageTitle", getPageTitle(null));
			}
			
			request.setAttribute("visitorIP", ServletHelperService.getClientIpAddr(request));
			
			servletHelperService.updateUnreadMessagesStatus(request);
			
			page = this.runInternal(request, response);
		} catch (Exception e) {
			ServletHelperService.logException(e, request);
			try {
				response.sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return page;
	}

	public String getPageTitle(String title) {
		String pageTitle =  StringHelper.getLocalString("zaibalo_blog");
		if(title != null){
			pageTitle += " " + title;
		}
		
		return pageTitle;
	}

}
