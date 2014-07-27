package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

@Component
public class DeleteCommentAction implements Action{

	@Autowired
	private CommentsDAO commentsDAO; 
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String commentIdParamValue = request.getParameter("commentId");
		int commentId = Integer.parseInt(commentIdParamValue);
		
		Comment comment = commentsDAO.getObjectById(commentId);
		User user = (User) request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(user.getId() != comment.getAuthorId() && user.getRole() >=2){
			ServletHelperService.logException(new Exception("Rights violation!"), request);
			return new FailResponse(StringHelper.getLocalString("you.are.not.powerfull.enough"));
		}

		commentsDAO.delete(commentId);
		return new SuccessResponse();
	}
}
