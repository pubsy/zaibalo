package ua.com.zaibalo.validation;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;


public class Validator extends AbstractValidator{

	public boolean validatePost(String content, String title, String[] categories) {

		validateNotEmptyString(content, StringHelper.getLocalString("content_cant_be_blank"));
		
		validateNotEmptyString(title, StringHelper.getLocalString("title_cant_be_blank"));
		
		validateNotEmptyArray(categories, StringHelper.getLocalString("you_have_to_choose_category"));
		
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
