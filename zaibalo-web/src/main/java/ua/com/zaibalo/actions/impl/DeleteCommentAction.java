package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

public class DeleteCommentAction implements Action{

	private static final long serialVersionUID = 1L;

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		String commentIdParamValue = request.getParameter("commentId");
		int commentId = Integer.parseInt(commentIdParamValue);
		DataAccessFactory factory = new DataAccessFactory(request);
		
		Comment comment = factory.getCommentsAccessInstance().getObjectById(commentId);
		User user = (User) request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(user.getId() != comment.getAuthorId() && user.getRole() >=2){
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("you.are.not.powerfull.enough") + "\"}");
			ServletHelperService.logException(new Exception("Rights violation!"), request);
			return;
		}

		factory.getCommentsAccessInstance().delete(commentId);
		out.write("{\"status\":\"success\"}");
	}
}
