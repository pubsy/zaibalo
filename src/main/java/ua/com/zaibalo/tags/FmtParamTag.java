package ua.com.zaibalo.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class FmtParamTag extends BodyTagSupport{
	
	private String value;
	
	@Override
	public int doEndTag() throws JspException {
		FmtMessageTag parentTag = (FmtMessageTag)findAncestorWithClass(this, FmtMessageTag.class);
		parentTag.addParam(value);
		return EVAL_PAGE;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
