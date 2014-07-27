package ua.com.zaibalo.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.com.zaibalo.helper.StringHelper;

@SuppressWarnings("serial")
public class FmtMessageTag extends TagSupport{
	
	private String key;
	private List<String> params = new ArrayList<String>();
	
	public int doStartTag() {
		params.clear();
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			String result = StringHelper.getLocalString(key, params.toArray());
			out.print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getParams() {
		return params;
	}

	public void addParam(String param) {
		this.params.add(param);
	}
	
	
}
