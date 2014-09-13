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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "discussions")
public class Discussion {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Type(type = "text")
	private String extract;

	@Column(name = "latest_message_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date latestMessageDate;

	@Column(name = "has_unread_messages")
	private boolean hasUnreadMessages;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private User author;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "recipient_id", referencedColumnName = "id")
	private User recipient;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExtract() {
		return extract;
	}

	public void setExtract(String extract) {
		this.extract = extract;
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
