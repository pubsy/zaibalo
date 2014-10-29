package ua.com.zaibalo.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import ua.com.zaibalo.constants.ZaibaloConstants;

public class SecurityTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		if(request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME) != null){
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
}
