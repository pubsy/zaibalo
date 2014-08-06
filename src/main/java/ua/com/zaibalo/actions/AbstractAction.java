package ua.com.zaibalo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;

public abstract class AbstractAction {

	private static final String ACTION_PARAMETER = "action"; 
	
	protected abstract Action getActionByName(String actionName);

	@Transactional(propagation=Propagation.REQUIRED)
	public AjaxResponse doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String actionName = request.getParameter(ACTION_PARAMETER);
		
		AjaxResponse ajaxResponse;
		if(StringUtils.isBlank(actionName)){
			String errorMessage = "ERROR: 'action' parameter wasn't found.";
			ServletHelperService.logException(new Exception(errorMessage), request);
			ajaxResponse = new FailResponse(errorMessage);
		}
		
		Action action = getActionByName(actionName);

		ajaxResponse = action.run(request, response);
		
		return ajaxResponse;
	}
	
}
