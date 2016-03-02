package ua.com.zaibalo.business;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PageFilterBusinessLogic {

	@Autowired
	private ServletHelperService servletHelperService;
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private CommentsDAO commentsDAO;
	
	public void prePage(HttpServletRequest request, User user) {
		
		ServletContext servletContext = request.getSession().getServletContext();
		if(servletContext.getAttribute("categories") == null) {
			List<Category> catList = categoriesDAO.getMostPopularCategoriesList(10);
			servletContext.setAttribute("categories", catList);
		}
		
		List<Comment> commentsList = commentsDAO.getRecentComments(15);
		request.setAttribute("recentComments", commentsList);

		if(request.getAttribute("pageTitle") == null){
			request.setAttribute("pageTitle", getPageTitle(null));
		}
		
		request.setAttribute("visitorIP", ServletHelperService.getClientIpAddr(request));
		request.setAttribute("successRedirectURL", request.getRequestURI());
		
		servletHelperService.updateUnreadMessagesStatus(request);
	}
	
	private String getPageTitle(String title) {
		String pageTitle =  StringHelper.getLocalString("zaibalo_blog");
		if(title != null){
			pageTitle += " " + title;
		}
		
		return pageTitle;
	}
}
