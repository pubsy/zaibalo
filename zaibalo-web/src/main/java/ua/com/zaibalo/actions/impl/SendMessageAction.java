package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.classic.Session;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.SendEmailHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

public class SendMessageAction implements Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		String recipientName = request.getParameter("recipient_name");
		String text = request.getParameter("text");
		
		if(StringHelper.isBlank(recipientName)){
			out.write("ERROR:" + StringHelper.getLocalString("select_recipient"));
			out.close();
			return;
		}
		
		if(StringHelper.isBlank(text)){
			out.write("ERROR:" + StringHelper.getLocalString("message_text_cant_be_blank"));
			out.close();
			return;
		}
		
		if(text.length() > 500){
			out.write("ERROR:" + StringHelper.getLocalString("message_text_cant_be_longer_than", 500));
			out.close();
			return;
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		User recipient = factory.getUsersAccessInstance().getUserByDisplayName(recipientName);
		
		if(recipient == null){
			out.write("ERROR:" + StringHelper.getLocalString("user_does_not_exist", recipientName));
			out.close();
			return;
		}
		
		
		Message message = new Message();
		message.setAuthorId(user.getId());
		message.setAuthor(user);
		message.setDate(new Date());
		message.setRecipientId(recipient.getId());
		message.setText(text);
		message.setRead(false);
		
		int discussionId = factory.getDiscussionsAccessInstance().getDisscussionIdForUsers(message.getAuthorId(), message.getRecipientId());
		if(discussionId == -1){
			Discussion discussion = new Discussion();
			discussion.setExtract(message.getText());
			discussion.setAuthorId(message.getAuthorId());
			discussion.setRecipientId(message.getRecipientId());
			discussion.setHasUnreadMessages(true);
			discussion.setLatestMessageDate(message.getDate());
			
			discussionId = factory.getDiscussionsAccessInstance().insert(discussion);
		}else{
			factory.getDiscussionsAccessInstance().updateExistingDiscussion(discussionId, message);
		}
		
		message.setDiscussionId(discussionId);
		
		int messageId = factory.getMessageAccessInstance().insert(message);
		discussionId = factory.getMessageAccessInstance().getMessageById(messageId).getDiscussionId();

		List<Message> messages = factory.getMessageAccessInstance().getAllUserDiscussionMessages(discussionId, user.getId());
		request.setAttribute("messages", messages);
		
		if(recipient.isNotifyOnPM()){
			sendNotification(request, text, recipient.getEmail(), user.getDisplayName());
		}

		RequestDispatcher view = request.getRequestDispatcher("/jsp/messagesBlock.jsp");
		view.forward(request, response);
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
