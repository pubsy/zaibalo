package ua.com.zaibalo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.helper.ajax.AjaxResponse;

public interface Action {
	
	public abstract AjaxResponse run(HttpServletRequest request, HttpServletResponse response);
}
