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

@Entity
@Table(name="post_rating")
public class PostRating {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName="id")
	private Post post;
	
	@Column(name="value")
	private int value;

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Post getPost() {
		return post;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}