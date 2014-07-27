package ua.com.zaibalo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="discussions")
public class Discussion {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Type(type="text")
	private String extract;

	@Column(name="author_id")
	private int authorId;
	
	@Column(name="recipient_id")
	private int recipientId;

	@Column(name="latest_message_date")
	private Date latestMessageDate;
	
	@Column(name="has_unread_messages")
	private boolean hasUnreadMessages;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="author_id", referencedColumnName="id", insertable = false, updatable = false)
	private User author;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recipient_id", referencedColumnName="id", insertable = false, updatable = false)
	private User recipient;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExtract() {
		//return StringHelper.extract(StringHelper.escapeXML(this.extract), 55, "...");
		return extract;
	}
	public void setExtract(String extract) {
		this.extract = extract;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}
	public void setHasUnreadMessages(boolean hasUnreadMessages) {
		this.hasUnreadMessages = hasUnreadMessages;
	}
	public boolean isHasUnreadMessages() {
		return hasUnreadMessages;
	}
	public void setLatestMessageDate(Date latestMessageDate) {
		this.latestMessageDate = latestMessageDate;
	}
	public Date getLatestMessageDate() {
		return latestMessageDate;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getAuthor() {
		return author;
	}
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	public User getRecipient() {
		return recipient;
	}

	
}
