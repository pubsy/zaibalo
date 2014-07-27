package ua.com.zaibalo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;

public abstract class AbstractAction {

	private static final String ACTION_PARAMETER = "action"; 
	
	protected abstract Action getActionByName(String actionName);

	public AjaxResponse doPost(HttpServletRequest request, HttpServletResponse response) {
		String actionName = request.getParameter(ACTION_PARAMETER);
		
		AjaxResponse ajaxResponse;
		if(StringUtils.isBlank(actionName)){
			String errorMessage = "ERROR: 'action' parameter wasn't found.";
			ServletHelperService.logException(new Exception(errorMessage), request);
			ajaxResponse = new FailResponse(errorMessage);
		}
		
		Action action = getActionByName(actionName);

		try {
			ajaxResponse = action.run(request, response);
		} catch (Exception e) {
			ServletHelperService.logException(e, request);
			ajaxResponse = new FailResponse(e.getMessage());
		}
		
		return ajaxResponse;
	}
	
}
