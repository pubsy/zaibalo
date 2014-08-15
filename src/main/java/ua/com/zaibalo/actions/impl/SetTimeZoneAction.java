package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;

@Component
public class SetTimeZoneAction implements Action {

	@Override
	public AjaxResponse run(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        String strFromJavaScript = request.getParameter("timeZone");
        double timeZone = Double.parseDouble(strFromJavaScript);
        if (timeZone >= 0) {
            strFromJavaScript = "+" + timeZone;
        }
        request.getSession().setAttribute("timeZone", "GMT" + strFromJavaScript);
        return new SuccessResponse(true);
	}

}
