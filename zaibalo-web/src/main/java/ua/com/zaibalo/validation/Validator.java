package ua.com.zaibalo.validation;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;


public class Validator extends AbstractValidator{

	public boolean validatePost(Post post) {

		validateNotEmptyString(post.getContent(), StringHelper.getLocalString("content_cant_be_blank"));
		
		validateNotEmptyString(post.getTitle(), StringHelper.getLocalString("title_cant_be_blank"));
		
		validateNotEmptyCollection(post.getCategories(), StringHelper.getLocalString("you_have_to_choose_category"));
		
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
