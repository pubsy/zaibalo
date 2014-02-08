package ua.com.zaibalo.actions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.impl.DeleteCommentAction;
import ua.com.zaibalo.actions.impl.DeletePostAction;
import ua.com.zaibalo.actions.impl.EditPostAction;
import ua.com.zaibalo.actions.impl.RateCommentAction;
import ua.com.zaibalo.actions.impl.RatePostAction;
import ua.com.zaibalo.actions.impl.SaveCommentAction;
import ua.com.zaibalo.actions.impl.SavePostAction;
import ua.com.zaibalo.actions.impl.SendMessageAction;

@Component
public class AuthorisedActionServlet extends AbstractAction{

	private static Map<String, Action> secureActionsMap = new HashMap<String, Action>();
	
	@Autowired
	private DeleteCommentAction deleteCommentAction;
	@Autowired
	private DeletePostAction deletePostAction;
	@Autowired
	private RatePostAction ratePostAction; 
	@Autowired
	private SaveCommentAction saveCommentAction;
	@Autowired
	private SavePostAction savePostAction;
	@Autowired
	private EditPostAction editPostAction;
	@Autowired
	private RateCommentAction rateCommentAction;
	@Autowired
	private SendMessageAction sendMessageAction;
	
	public void initMap() {
		secureActionsMap.put("delete_comment", deleteCommentAction);
		secureActionsMap.put("delete_post", deletePostAction);
		secureActionsMap.put("rate_post", ratePostAction);
		secureActionsMap.put("save_comment", saveCommentAction);
		secureActionsMap.put("save_post", savePostAction);
		secureActionsMap.put("update_post", editPostAction);
		secureActionsMap.put("rate_comment", rateCommentAction);
		secureActionsMap.put("send_message", sendMessageAction);
	}

	@Override
	protected Action getActionByName(String actionName) {
		if(secureActionsMap.isEmpty()){
			initMap();
		}
		return secureActionsMap.get(actionName);
	}

}
