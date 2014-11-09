package ua.com.zaibalo.validation;

import java.util.Set;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Comment;


public class Validator extends AbstractValidator{

	public boolean validatePost(String content, String title, Set<Category> categories) {

		validateNotEmptyString(content, StringHelper.getLocalString("content_cant_be_blank"));
		
		if(validationErrors.isEmpty()){
			return true;
		}
		return false;
	}
	
	public boolean validateComment(Comment comment) {

		validateNotEmptyString(comment.getContent(), StringHelper.getLocalString("comment_content_blank"));

		if(validationErrors.isEmpty()){
			return true;
		}
		return false;
	}

}
