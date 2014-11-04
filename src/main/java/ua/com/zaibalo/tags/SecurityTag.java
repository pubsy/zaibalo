package ua.com.zaibalo.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import ua.com.zaibalo.constants.ZaibaloConstants;

public class SecurityTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	private String access;
	
	@Override
	public int doStartTag() throws JspException {
		boolean evalBodyInclude = false;
		
		if("isAnonymous()".equals(access)){
			evalBodyInclude = !isAuthenticated();
		} else {
			evalBodyInclude = isAuthenticated();
		}
		
		if(evalBodyInclude){
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	private boolean isAuthenticated() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		return request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME) != null;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	
}
