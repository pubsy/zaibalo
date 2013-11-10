package ua.com.zaibalo.tags;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class UrlEncodeTag  extends SimpleTagSupport{
	
	private String value;
	
	public void doTag() throws JspException, IOException{
		String response = URLEncoder.encode(value, "UTF-8");
		getJspContext().getOut().print(response);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

