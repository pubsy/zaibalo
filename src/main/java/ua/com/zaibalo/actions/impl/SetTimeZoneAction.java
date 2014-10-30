package ua.com.zaibalo.actions.impl;

import java.text.DecimalFormat;

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
			HttpServletResponse response) {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalSeparatorAlwaysShown(false);

		String strFromJavaScript = request.getParameter("timeZone");
		double timeZone = Double.parseDouble(strFromJavaScript);
		String timeZoneValue = timeZone >= 0 ? 
				"GMT+" + format.format(timeZone) :
				"GMT" + format.format(timeZone);
		request.getSession().setAttribute("timeZone", timeZoneValue);
		return new SuccessResponse(true);
	}

}
