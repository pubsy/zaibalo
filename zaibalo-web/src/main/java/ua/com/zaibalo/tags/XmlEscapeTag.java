package ua.com.zaibalo.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ua.com.zaibalo.helper.StringHelper;

public class XmlEscapeTag extends SimpleTagSupport{
	
	private String text;
	
	public void doTag() throws JspException, IOException{
		text = StringHelper.escapeXML(text);
		getJspContext().getOut().print(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
