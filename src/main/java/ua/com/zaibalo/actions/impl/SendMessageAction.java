package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.templates.PrivateMessageNotificationMessage;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

@Component
public class SendMessageAction implements Action {

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private DiscussionsDAO discussionsDAO;
	@Autowired
	private MessagesDAO messagesDAO;
	@Autowired
	private SendEmailService sendEmailService;

	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sender = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(sender.getRole() > 2){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}

		String recipientName = request.getParameter("recipient_name");
		String text = request.getParameter("text");
		
		
		if(StringHelper.isBlank(recipientName)){
			return new FailResponse(StringHelper.getLocalString("select_recipient"));
		}
		
		if(StringHelper.isBlank(text)){
			return new FailResponse(StringHelper.getLocalString("message_text_cant_be_blank"));
		}
		
		if(text.length() > 500){
			return new FailResponse(StringHelper.getLocalString("message_text_cant_be_longer_than", 500));
		}

		User recipient = usersDAO.getUserByDisplayName(recipientName);
		
		if(recipient == null){
			return new FailResponse(StringHelper.getLocalString("user_does_not_exist", recipientName));
		}
		
		Message message = new Message();
		message.setAuthor(sender);
		message.setAuthor(sender);
		message.setDate(new Date());
		message.setRecipient(recipient);
		message.setText(text);
		message.setRead(false);
		
		Discussion discussion = discussionsDAO.getDisscussionIdForUsers(message.getAuthor(), message.getRecipient());
		if(discussion == null){
			discussion = new Discussion();
			discussion.setExtract(message.getText());
			discussion.setAuthor(message.getAuthor());
			discussion.setRecipient(message.getRecipient());
			discussion.setHasUnreadMessages(true);
			discussion.setLatestMessageDate(message.getDate());
			
			discussionsDAO.insert(discussion);
		}else{
			discussionsDAO.updateExistingDiscussion(discussion, message);
		}
		
		message.setDiscussion(discussion);
		
		messagesDAO.insert(message);
		
		List<Message> messages = messagesDAO.getAllUserDiscussionMessages(discussion, sender);
		request.setAttribute("messages", messages);
		
		if(recipient.isNotifyOnPM()){
			sendNotification(request, text, recipient.getEmail(), sender.getDisplayName());
		}

		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    request.getRequestDispatcher("/WEB-INF/jsp/messagesBlock.jsp").forward(request, customResponse);
	    
	    return new SuccessResponse(customResponse.getOutput());
	}

	private void sendNotification(HttpServletRequest request, String text, String recipientEmail, String senderName)
			throws IOException, AddressException, MessagingException, Exception {
		
		PrivateMessageNotificationMessage message = new PrivateMessageNotificationMessage(recipientEmail);
		message.setSenderName(senderName);
		message.setText(text);
		sendEmailService.sendEmail(message);
	
	}

}
