package ua.com.zaibalo.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;

public class PostTextTag extends SimpleTagSupport{
	
	private Post post;
	
	public void doTag() throws JspException, IOException{
		String text = StringHelper.escapeXML(post.getContent());
		text = StringHelper.replaceTagsWithLinks(post.getCategories(), text);
		getJspContext().getOut().print(text);
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
