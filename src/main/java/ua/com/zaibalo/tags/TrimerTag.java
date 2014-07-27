package ua.com.zaibalo.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ua.com.zaibalo.helper.StringHelper;

public class TrimerTag extends SimpleTagSupport{
	
	private String text;
	private String postFix;
	private int maxWords;
	
	public void doTag() throws JspException, IOException{
		text = StringHelper.escapeXML(text);
		if(postFix == null){
			text = StringHelper.extract(text, maxWords);
		}else{
			text = StringHelper.extract(text, maxWords, postFix);			
		}
		getJspContext().getOut().print(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPostFix() {
		return postFix;
	}

	public void setPostFix(String postFix) {
		this.postFix = postFix;
	}

	public int getMaxWords() {
		return maxWords;
	}

	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}

}
