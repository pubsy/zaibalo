package ua.com.zaibalo.actions;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAction {
	
	private final static Logger LOGGER = Logger.getLogger(AbstractAction.class);

	private static final String ACTION_PARAMETER = "action"; 
	
	protected abstract Action getActionByName(String actionName);

	@Transactional(propagation=Propagation.REQUIRED)
	public AjaxResponse doPost(HttpServletRequest request, HttpServletResponse response) {
		String actionName = request.getParameter(ACTION_PARAMETER);
		
		AjaxResponse ajaxResponse;
		if(StringUtils.isBlank(actionName)){
			String errorMessage = "ERROR: 'action' parameter wasn't found.";
			LOGGER.error(errorMessage);
			ajaxResponse = new FailResponse(errorMessage);
		}
		
		Action action = getActionByName(actionName);

		ajaxResponse = action.run(request, response);
		
		return ajaxResponse;
	}
	
}
