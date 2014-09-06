package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

@Component
public class EditCommentAction implements Action {

	@Autowired
	private CommentsDAO commentsDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String text = request.getParameter("comment_text");
		String commentIdStr = request.getParameter("comment_id");
		
		if(text == null || "".equals(text.trim())){
			return new FailResponse(StringHelper.getLocalString("content_cant_be_blank"));
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);

		if(user.getRole() > 2){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		int commentId = Integer.parseInt(commentIdStr);
		Comment comment = commentsDAO.getObjectById(commentId);
		
		if(user.getRole() == 2 && comment.getAuthor().getId() != user.getId()){
			return new FailResponse(StringHelper.getLocalString("you_cant_edit_other_users_post"));
		}

		comment.setContent(text);
		
		commentsDAO.update(comment);
		
		request.setAttribute("comment", comment);
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    request.getRequestDispatcher("/WEB-INF/jsp/comment_wrapper.jsp").forward(request, customResponse);
	    Object commentHTML = customResponse.getOutput();
		
	    return new SuccessResponse(commentHTML);
	}

}
