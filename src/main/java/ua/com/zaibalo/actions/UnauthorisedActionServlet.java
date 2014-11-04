package ua.com.zaibalo.actions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.impl.RegisterAction;
import ua.com.zaibalo.actions.impl.RemindPasswordAction;
import ua.com.zaibalo.actions.impl.SetTimeZoneAction;
import ua.com.zaibalo.actions.impl.ShowRatingAction;

@Component
public class UnauthorisedActionServlet extends AbstractAction {

	@Autowired
	private RegisterAction registerAction;
	@Autowired
	private RemindPasswordAction remindPasswordAction;
	@Autowired
	private ShowRatingAction showRatingAction;
	@Autowired
	private SetTimeZoneAction setTimeZoneAction;
	
	private Map<String, Action> actionsMap = new HashMap<String, Action>();

	public void initMap() {
		actionsMap.put("register", registerAction);
		actionsMap.put("remind_password", remindPasswordAction);
		actionsMap.put("show_rating", showRatingAction);
		actionsMap.put("set_time_zone", setTimeZoneAction);
	}

	@Override
	protected Action getActionByName(String actionName) {
		if(actionsMap.isEmpty()){
			initMap();
		}
		return actionsMap.get(actionName);
	}
}
