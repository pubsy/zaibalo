package ua.com.zaibalo.tags;

import java.io.IOException;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.markdown4j.Markdown4jProcessor;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.spring.SpringPropertiesUtil;

public class PostTextTag extends SimpleTagSupport{
	
	private Post post;
	
	public void doTag() throws JspException, IOException {
		String out = cleanHTML(convertMarkdownToHTML(findAndReplaceTags(post.getCategories(), post.getContent())));
		getJspContext().getOut().print(out);
	}

	private String convertMarkdownToHTML(String input) throws IOException {
		return new Markdown4jProcessor().process(input);
	}
	
	private String cleanHTML(String input) {
		String baseUrl = "http://" + SpringPropertiesUtil.getProperty("app.server.domain");
		return Jsoup.clean(input, baseUrl, Whitelist.relaxed());
	}

	private String findAndReplaceTags(Set<Category> categories, String input) {
		return StringHelper.replaceTagsWithLinks(categories, input);
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
