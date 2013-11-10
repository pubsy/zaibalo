package ua.com.zaibalo.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import ua.com.zaibalo.actions.impl.AutentificationAction;
import ua.com.zaibalo.actions.impl.GetMorePostsAction;
import ua.com.zaibalo.actions.impl.RegisterAction;
import ua.com.zaibalo.actions.impl.RemindPasswordAction;
import ua.com.zaibalo.actions.impl.UserValidationLinkAction;

@WebServlet(name="Action", urlPatterns={"/action.do"}, description="Servlet handling AJAX requests")
public class ActionServlet extends GenericActionServlet {

	private static final long serialVersionUID = 1L;
	
	private static Map<String, Action> actionsMap = new HashMap<String, Action>();
	
	static{
		actionsMap.put("authenticate", new AutentificationAction());
		actionsMap.put("validate_email", new UserValidationLinkAction());
		actionsMap.put("more_posts", new GetMorePostsAction());
		actionsMap.put("register", new RegisterAction());
		actionsMap.put("remind_password", new RemindPasswordAction());
	}

	@Override
	protected Action getActionByName(String actionName) {
		return actionsMap.get(actionName);
	}

}
