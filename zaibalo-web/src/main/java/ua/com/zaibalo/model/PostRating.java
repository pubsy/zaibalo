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

@Entity
@Table(name="post_rating")
public class PostRating {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	private Date date;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", referencedColumnName="id", insertable = false, updatable = false)
	private User user;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName="id", insertable = false, updatable = false)
	private Post post;
	
	@Column(name="value")
	private int value;
	
	@Column(name="user_display_name")
	private String userDisplayName;
	
	@Column(name="post_title")
	private String postTitle;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="post_id")
	private int postId;

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostTitle() {
		return postTitle;
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