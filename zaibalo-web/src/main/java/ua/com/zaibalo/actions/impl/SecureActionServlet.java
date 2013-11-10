package ua.com.zaibalo.actions.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.actions.GenericActionServlet;

@WebServlet(name="SecureAction", urlPatterns={"/secure_action/action.do"}, description="Secure servlet handling AJAX requests")
public class SecureActionServlet extends GenericActionServlet{

	private static final long serialVersionUID = 1L;
	
	private static Map<String, Action> secureActionsMap = new HashMap<String, Action>();
	
	static{
		secureActionsMap.put("delete_comment", new DeleteCommentAction());
		secureActionsMap.put("delete_post", new DeletePostAction());
		secureActionsMap.put("rate_post", new RatePostAction());
		secureActionsMap.put("save_comment", new SaveCommentAction());
		secureActionsMap.put("save_post", new SavePostAction());
		secureActionsMap.put("update_post", new EditPostAction());
		secureActionsMap.put("rate_comment", new RateCommentAction());
		secureActionsMap.put("update_profile", new UpdatProfileAction());
		secureActionsMap.put("send_message", new SendMessageAction());
	}

	@Override
	protected Action getActionByName(String actionName) {
		return secureActionsMap.get(actionName);
	}


}
