package ua.com.zaibalo.actions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.impl.AutentificationAction;
import ua.com.zaibalo.actions.impl.RegisterAction;
import ua.com.zaibalo.actions.impl.RemindPasswordAction;
import ua.com.zaibalo.actions.impl.ShowRatingAction;
import ua.com.zaibalo.actions.impl.UserValidationLinkAction;

@Component
public class UnauthorisedActionServlet extends AbstractAction {

	@Autowired
	private AutentificationAction autentificationAction;
	@Autowired
	private UserValidationLinkAction userValidationLinkAction;
	@Autowired
	private RegisterAction registerAction;
	@Autowired
	private RemindPasswordAction remindPasswordAction;
	@Autowired
	private ShowRatingAction showRatingAction;

	private Map<String, Action> actionsMap = new HashMap<String, Action>();

	public void initMap() {
		actionsMap.put("authenticate", autentificationAction);
		actionsMap.put("validate_email", userValidationLinkAction);
		actionsMap.put("register", registerAction);
		actionsMap.put("remind_password", remindPasswordAction);
		actionsMap.put("show_rating", showRatingAction);
	}

	@Override
	protected Action getActionByName(String actionName) {
		if(actionsMap.isEmpty()){
			initMap();
		}
		return actionsMap.get(actionName);
	}
}
