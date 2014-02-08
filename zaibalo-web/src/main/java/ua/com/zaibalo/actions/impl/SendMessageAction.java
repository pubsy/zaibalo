package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.SendEmailHelper;
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


	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		User recipient = usersDAO.getUserByDisplayName(recipientName);
		
		if(recipient == null){
			return new FailResponse(StringHelper.getLocalString("user_does_not_exist", recipientName));
		}
		
		
		Message message = new Message();
		message.setAuthorId(user.getId());
		message.setAuthor(user);
		message.setDate(new Date());
		message.setRecipientId(recipient.getId());
		message.setText(text);
		message.setRead(false);
		
		int discussionId = discussionsDAO.getDisscussionIdForUsers(message.getAuthorId(), message.getRecipientId());
		if(discussionId == -1){
			Discussion discussion = new Discussion();
			discussion.setExtract(message.getText());
			discussion.setAuthorId(message.getAuthorId());
			discussion.setRecipientId(message.getRecipientId());
			discussion.setHasUnreadMessages(true);
			discussion.setLatestMessageDate(message.getDate());
			
			discussionId = discussionsDAO.insert(discussion);
		}else{
			discussionsDAO.updateExistingDiscussion(discussionId, message);
		}
		
		message.setDiscussionId(discussionId);
		
		int messageId = messagesDAO.insert(message);
		discussionId = messagesDAO.getMessageById(messageId).getDiscussionId();

		List<Message> messages = messagesDAO.getAllUserDiscussionMessages(discussionId, user.getId());
		request.setAttribute("messages", messages);
		
		if(recipient.isNotifyOnPM()){
			sendNotification(request, text, recipient.getEmail(), user.getDisplayName());
		}

		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    request.getRequestDispatcher("/jsp/messagesBlock.jsp").forward(request, customResponse);
	    
	    return new SuccessResponse(customResponse.getOutput());
	}

	private void sendNotification(HttpServletRequest request, String text, String recipientEmail, String senderName)
			throws IOException, AddressException, MessagingException, Exception {
		String[] params = { 
				StringHelper.getLocalString("you_got_new_message", senderName),
				StringHelper.getLocalString("message_text", text) 
			};
		
		InputStream is = request.getServletContext().getResourceAsStream("html/pm_notification.html");
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");

		String pattern = writer.toString();
		
		String body = new MessageFormat(pattern).format(params, new StringBuffer(), null).toString();

		final SendEmailHelper helper = new SendEmailHelper();
		final MimeMessage email = helper.createMessage(
				recipientEmail,
				StringHelper.getLocalString("zaibalo_new_pm"), body
			);
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					helper.send(email);
				} catch (MessagingException ex) {
					System.out.println("ERROR: Sending pm message email notification failed.");
				}					
			}		
		}).start();
	}

}
