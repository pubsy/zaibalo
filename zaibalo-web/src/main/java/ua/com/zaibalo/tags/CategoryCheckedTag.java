package ua.com.zaibalo.tags;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CategoryCheckedTag extends SimpleTagSupport{
	private int id;
	private String queryString;
	private String url;
	
	
	public void doTag() throws JspException, IOException{
		final ServletRequest servletRequest = ((PageContext) getJspContext()).getRequest();
		String catIds = servletRequest.getParameter("categoryId");
		
		if(catIds == null){
			catIds = (String) servletRequest.getAttribute("param_categories");
		}
		
		if(catIds == null){
			return;
		}
		
		String[] ids = catIds.split(",");
		if(Arrays.asList(ids).contains("" + id)){
			getJspContext().getOut().print("checked");
		}
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public String getQueryString() {
		return queryString;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUrl() {
		return url;
	}
}
