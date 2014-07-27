package ua.com.zaibalo.filters;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Comment;

@Transactional
public class PageFilter implements HandlerInterceptor{

	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private CommentsDAO commentsDAO; 
	@Autowired
	private ServletHelperService servletHelperService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		
		servletHelperService.updateUserAuthenication(request, response);
		
		ServletContext servletContext = request.getServletContext();
		if(servletContext.getAttribute("categories") == null){
			List<Category> catList = categoriesDAO.getCategoriesList(Category.CategoryType.CATEGORY);
			request.getServletContext().setAttribute("categories", catList);
		}
		
		List<Comment> commentsList = commentsDAO.getRecentComments(15);
		request.setAttribute("recentComments", commentsList);

		if(request.getAttribute("pageTitle") == null){
			request.setAttribute("pageTitle", getPageTitle(null));
		}
		
		request.setAttribute("visitorIP", ServletHelperService.getClientIpAddr(request));
		
		servletHelperService.updateUnreadMessagesStatus(request);
		
		return true;
	}
	
	public String getPageTitle(String title) {
		String pageTitle =  StringHelper.getLocalString("zaibalo_blog");
		if(title != null){
			pageTitle += " " + title;
		}
		
		return pageTitle;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
}
