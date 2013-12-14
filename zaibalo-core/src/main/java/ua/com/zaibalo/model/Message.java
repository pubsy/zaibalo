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
@Table(name="messages")
public class Message {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Type(type="text")
	private String text;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="author_id", referencedColumnName="id", insertable = false, updatable = false)
	private User author;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recipient_id", referencedColumnName="id", insertable = false, updatable = false)
	private User recipient;
	
	@Column(name="creation_date")
	private Date date;
	
	@Column(name="discussion_id")
	private int discussionId;
	
	@Column(name="is_read")
	private boolean read;
	
	@Column(name="author_id")
	private int authorId;
	
	@Column(name="recipient_id")
	private int recipientId;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public boolean isRead() {
		return read;
	}

	public void setDiscussionId(int discussionId) {
		this.discussionId = discussionId;
	}
	public int getDiscussionId() {
		return discussionId;
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
