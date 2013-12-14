package ua.com.zaibalo.servlets.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.helper.ZAppContext;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;

public abstract class ServletPage extends HttpServlet{
	
	public static final long serialVersionUID = 1L;
	
	public abstract String run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        PrintWriter out = response.getWriter();  
		
		List<Comment> commentsList = null;
		
		String page = null;
		
		try {
			commentsList = ZAppContext.getCommentsDAO().getRecentComments(20);
			request.setAttribute("recentComments", commentsList);

			if(request.getAttribute("pageTitle") == null){
				request.setAttribute("pageTitle", getPageTitle(null));
			}
			
			request.setAttribute("visitorIP", ServletHelperService.getClientIpAddr(request));
			
			ZAppContext.getServletHelperService().checkUserAuthorised(request, response);

			ZAppContext.getServletHelperService().updateUnreadMessagesStatus(request);
			
			page = this.run(request, response, out);
			if(page == null){
				return;
			}
			request.getRequestDispatcher(page).forward(request, response);

		} catch (Exception e) {
			ServletHelperService.logException(e, request);
			try {
				response.sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getPageTitle(String title) {
		String pageTitle =  StringHelper.getLocalString("zaibalo_blog");
		if(title != null){
			pageTitle += " " + title;
		}
		
		return pageTitle;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doPost(request, response);
	}

}
