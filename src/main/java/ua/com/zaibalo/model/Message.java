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

import ua.com.zaibalo.helper.gson.ExcludeFromJson;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Type(type = "text")
	private String text;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private User author;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipient_id", referencedColumnName = "id")
	private User recipient;

	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@ExcludeFromJson
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "discussion_id", referencedColumnName = "id", nullable = false)
	private Discussion discussion;

	@Column(name = "is_read")
	private boolean read;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

}
